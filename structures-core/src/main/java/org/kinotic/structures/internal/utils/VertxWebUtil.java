package org.kinotic.structures.internal.utils;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Navíd Mitchell 🤪 on 6/1/23.
 */
public class VertxWebUtil {
    private static final Logger log = LoggerFactory.getLogger(VertxWebUtil.class);

    public static String validateAndReturnStructureId(RoutingContext ctx){
        String structureNamespace = ctx.pathParam("structureNamespace");
        String structureName = ctx.pathParam("structureName");

        Validate.notNull(structureNamespace, "structureNamespace must not be null");
        Validate.notNull(structureName, "structureName must not be null");

        return StructuresUtil.structureNameToId(structureNamespace, structureName);
    }

    public static String validateAndReturnPathParam(String pathParamName, RoutingContext ctx){
        String ret = ctx.pathParam(pathParamName);
        Validate.notNull(ret, pathParamName + " must not be null");
        return ret;
    }

    /**
     * Returns a {@link Pageable} from the request or a default {@link OffsetPageable} if none is present
     * @param ctx the routing context to get the pageable from
     * @return a {@link Pageable}
     */
    public static Pageable getPageableOrDefaultOffsetPageable(RoutingContext ctx){
       Pageable ret = getPageableIfExits(ctx ,true, OffsetPageable.class);
         if(ret == null){
              ret = Pageable.create(0, 25, null);
         }
         return ret;
    }

    /**
     * Returns a {@link Pageable} from the request or a default {@link CursorPageable} if none is present
     * @param ctx the routing context to get the pageable from
     * @return a {@link Pageable}
     */
    public static Pageable getPageableOrDefaultCursorPageable(RoutingContext ctx){
        Pageable ret = getPageableIfExits(ctx ,true, CursorPageable.class);
        if(ret == null){
            ret = Pageable.create(null, 25, null);
        }
        return ret;
    }

    /**
     * Returns a {@link Pageable} if either a cursor or page number is present in the request
     * @param ctx the routing context to get the pageable from
     * @param createIfOnlySortPresent if true and only a sort is present a {@link OffsetPageable} will be created with a page number of 0
     * @param defaultPageableClass the default {@link Pageable} type if only a sort is present
     * @return a {@link Pageable} or null if neither a cursor nor page number is present
     */
    public static Pageable getPageableIfExits(RoutingContext ctx,
                                              boolean createIfOnlySortPresent,
                                              Class<? extends Pageable> defaultPageableClass){
        Pageable ret = null;
        String sizeString = ctx.request().getParam("size");
        int size = (sizeString != null && !sizeString.isEmpty()) ? Integer.parseInt(sizeString) : 25;

        String pageString = ctx.request().getParam("page");
        Integer pageNumber = pageString != null ? Integer.parseInt(pageString) : null;

        String cursor = ctx.request().getParam("cursor");
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
        } else if(createIfOnlySortPresent && defaultPageableClass != null && !orders.isEmpty()){
            if(defaultPageableClass == OffsetPageable.class){
                ret = Pageable.create(0, size, Sort.by(orders));
            }else if(defaultPageableClass == CursorPageable.class){
                ret = Pageable.create(null, size, Sort.by(orders));
            }else{
                throw new IllegalArgumentException("Unsupported defaultPageableClass: " + defaultPageableClass);
            }
        }

        return ret;
    }

    public static Handler<RoutingContext> createExceptionConvertingFailureHandler(){
        return ctx -> {
            writeException(ctx, ctx.failure());
        };
    }

    public static void writeException(RoutingContext context, Throwable throwable){
        HttpServerResponse response = context.response();
        String errorMessage;
        int statusCode = context.statusCode();

        if(throwable != null){

            errorMessage = throwable.getMessage();

            if(statusCode == -1){
                if (throwable instanceof IllegalArgumentException) {
                    statusCode = 400;
                } else if (throwable instanceof NullPointerException) {
                    statusCode = 400;
                } else {
                    statusCode = 500;
                }
            }
            log.debug("Error processing web request. Status Code ({})", statusCode, throwable);
        }else{

            if(statusCode == -1){
                statusCode = 500;
            }
            errorMessage = "Server Error";

            log.warn("Unknown exception occurred processing web request. Status Code ({})", statusCode);
        }
        String jsonString = new JsonObject().put("error", errorMessage).encode();
        response.setStatusCode(statusCode);
        response.putHeader("Content-Type", "application/json");
        response.end(jsonString);
    }

}
