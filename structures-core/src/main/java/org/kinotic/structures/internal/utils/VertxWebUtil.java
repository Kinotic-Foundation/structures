package org.kinotic.structures.internal.utils;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
public class VertxWebUtil {

    public static String validateAndReturnStructureId(RoutingContext ctx){
        String structureNamespace = ctx.pathParam("structureNamespace");
        String structureName = ctx.pathParam("structureName");

        Validate.notNull(structureNamespace, "structureNamespace must not be null");
        Validate.notNull(structureName, "structureName must not be null");

        return StructuresUtil.structureNameToId(structureNamespace, structureName);
    }

    /**
     * Returns a {@link Pageable} from the request or a default {@link OffsetPageable} if none is present
     * @param ctx the routing context to get the pageable from
     * @return a {@link Pageable}
     */
    public static Pageable getPageableOrDefaultOffsetPageable(RoutingContext ctx){
       Pageable ret = getPageableIfExits(ctx ,true);
         if(ret == null){
              ret = Pageable.create(0, 25, null);
         }
         return ret;
    }

    /**
     * Returns a {@link Pageable} if either a cursor or page number is present in the request
     * @param ctx the routing context to get the pageable from
     * @param createIfOnlySortPresent if true and only a sort is present a {@link Pageable} will be created with a page number of 0
     * @return a {@link Pageable} or null if neither a cursor nor page number is present
     */
    public static Pageable getPageableIfExits(RoutingContext ctx,
                                              boolean createIfOnlySortPresent){
        Pageable ret = null;
        String sizeString = ctx.request().getParam("size");
        int size = (sizeString != null && !sizeString.isEmpty()) ? Integer.parseInt(sizeString) : 25;

        String pageString = ctx.request().getParam("page");
        Integer pageNumber = pageString != null ? Integer.parseInt(pageString) : null;

        String cursor = ctx.request().getParam("page");
        boolean cursorPresent = false;
        if(cursor != null){
            if(cursor.equals("null")){
                cursor = null;
            }
            cursorPresent = true;
        }

        if (cursorPresent && pageNumber != null) {
            throw new IllegalArgumentException("Pageable cannot have both a cursor and a pageNumber");
        }

        String sortString = ctx.request().getParam("sort");
        String[] sort = (sortString != null && !sortString.isEmpty()) ? sortString.split(",") : new String[0];
        List<Order> orders = new ArrayList<>();
        for(String fieldString : sort){
            if(fieldString.startsWith("-")) {
                orders.add(new Order(Direction.DESC, fieldString.substring(1)));
            }else {
                orders.add(new Order(Direction.ASC, fieldString));
            }
        }

        if(pageNumber != null){
            ret = Pageable.create(pageNumber, size, Sort.by(orders));
        } else if(cursorPresent){
            ret = Pageable.create(cursor, size, Sort.by(orders));
        } else if(createIfOnlySortPresent && !orders.isEmpty()){
            ret = Pageable.create(0, size, Sort.by(orders));
        }

        return ret;
    }

    public static Handler<RoutingContext> createExceptionConvertingFailureHandler(){
        return ctx -> {
            Throwable failure = ctx.failure();
            if(failure != null){
                writeException(ctx, failure);
            }
        };
    }

    public static void writeException(RoutingContext context, Throwable throwable){
        HttpServerResponse response = context.response();
        if(throwable instanceof IllegalArgumentException) {
            response.setStatusCode(400);
        }else if(throwable instanceof NullPointerException){
            response.setStatusCode(400);
        } else {
            response.setStatusCode(500);
        }
        response.putHeader("Content-Type", "application/json");
        response.end(new JsonObject().put("error", throwable.getMessage()).encode());
    }

}
