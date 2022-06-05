package com.onixys.vertx.tdd.sample.verticle;

import com.onixys.vertx.tdd.sample.config.VertxConfiguration;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Verticle
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainVerticle extends AbstractVerticle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void start() throws Exception {
        logger.info("Starting Main Verticle");
        Future<JsonObject> load = VertxConfiguration.instance(vertx).load();
        load.compose(this::configuration);
    }

    private Future<Void> configuration(JsonObject configuration) {
        DeploymentOptions deploymentOptions = new DeploymentOptions().setConfig(configuration);
        vertx.deployVerticle(new HttpVerticle(), deploymentOptions);
        return Future.succeededFuture();
    }

    public void stop() throws Exception {
    }
}
