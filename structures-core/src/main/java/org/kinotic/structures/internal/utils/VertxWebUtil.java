package org.kinotic.structures.internal.utils;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.Validate;
import org.kinotic.continuum.core.api.crud.Direction;
import org.kinotic.continuum.core.api.crud.Order;
import org.kinotic.continuum.core.api.crud.Pageable;
import org.kinotic.continuum.core.api.crud.Sort;

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

    public static Pageable validateAndReturnPageable(RoutingContext ctx){
        String pageString = ctx.request().getParam("page");
        String sizeString = ctx.request().getParam("size");
        String sortString = ctx.request().getParam("sort");

        int page = (pageString != null && !pageString.isEmpty()) ? Integer.parseInt(pageString) : 0;
        int size = (sizeString != null && !sizeString.isEmpty()) ? Integer.parseInt(sizeString) : 25;

        String[] sort = (sortString != null && !sortString.isEmpty()) ? sortString.split(",") : new String[0];
        List<Order> orders = new ArrayList<>();
        for(String fieldString : sort){
            if(fieldString.startsWith("-")) {
                orders.add(new Order(Direction.DESC, fieldString.substring(1)));
            }else {
                orders.add(new Order(Direction.ASC, fieldString));
            }
        }

        return Pageable.create(page, size, Sort.by(orders));
    }

    public static Handler<RoutingContext> createExceptionConvertingFailureHandler(){
        return ctx -> {
            Throwable failure = ctx.failure();
            if(failure != null){
                writeException(ctx.response(), failure);
            }
        };
    }

    public static void writeException(HttpServerResponse response, Throwable throwable){
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
