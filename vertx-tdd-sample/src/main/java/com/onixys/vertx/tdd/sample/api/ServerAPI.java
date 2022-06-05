package com.onixys.vertx.tdd.sample.api;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server API
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class ServerAPI {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void health(RoutingContext ctx) {
        logger.info("getting health");
        ServerHealthIndicator healthIndicator = new ServerHealthIndicator();
        JsonObject response = new JsonObject().put("status", healthIndicator.health().status().name());
        ctx.response()
            .putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
            .end(response.encode());
    }
}
