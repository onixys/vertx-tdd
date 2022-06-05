package com.onixys.vertx.tdd.sample.verticle;

import com.onixys.vertx.tdd.sample.api.ServerAPI;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.CookieSameSite;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static com.onixys.vertx.tdd.parent.common.TimeConstants.TIMEOUT;

/**
 * Http Verticle
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class HttpVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start() throws Exception {
        Router router = Router.router(vertx);

        setupCors(router);
        setupSession(router);
        setupDefaultRoutes(router);
        routes(router);

        logger.info("HTTP Server is starting ...");
        JsonObject http = config().getJsonObject("http");
        vertx
            .createHttpServer()
            .requestHandler(router)
            .listen(http.getInteger("port"))
            .compose(this::result);
    }

    private Future<Void> result(HttpServer httpServer) {
        logger.info("HTTP Server is started on port: " + httpServer.actualPort());
        return Future.succeededFuture();
    }

    public void routes(Router router) {
        router.get("/health/readiness").handler(handler -> new ServerAPI().health(handler));
        router.get("/health/liveness").handler(handler -> new ServerAPI().health(handler));
    }

    private void setupCors(Router router) {
        Set<String> allowedHeaders = new HashSet();
        allowedHeaders.add("Access-Control-Allow-Credentials");
        allowedHeaders.add("Access-Control-Allow-Origin");
        allowedHeaders.add("Access-Control-Expose-Headers");
        allowedHeaders.add("x-requested-with");
        allowedHeaders.add("origin");
        allowedHeaders.add("accept");
        allowedHeaders.add("credentials");
        allowedHeaders.add("Authorization");
        allowedHeaders.add("Set-Cookie");
        allowedHeaders.add("Content-Type");


        CorsHandler corsHandler = CorsHandler.create(config().getString("corsUrl"));
        corsHandler
            .allowedHeaders(allowedHeaders)
            .allowCredentials(true);

        router.route().handler(corsHandler);
    }

    private SessionHandler setupSession(Router router) {
        SessionHandler sessionHandler = SessionHandler.create(LocalSessionStore.create(vertx));
        sessionHandler
            .setSessionCookieName("chat-backend-cookie")
            // Javascript cannot access cookie
            .setCookieHttpOnlyFlag(true)
            .setCookieSameSite(CookieSameSite.NONE)
            .setSessionTimeout(TIMEOUT)
            .setCookieSecureFlag(config().getBoolean("secureCookie"));
        router.route().handler(sessionHandler);
        return sessionHandler;
    }

    private void setupDefaultRoutes(Router router) {
        router.route().handler(BodyHandler.create());
        router.route().handler(LoggerHandler.create());
    }

    public void stop() throws Exception {
    }
}
