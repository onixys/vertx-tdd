package com.onixys.vertx.tdd.sample.integration;

import com.onixys.vertx.tdd.sample.config.VertxConfiguration;
import com.onixys.vertx.tdd.sample.verticle.MainVerticle;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.junit5.RunTestOnContext;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Abstract Integration Test
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(VertxExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {
    @RegisterExtension
    RunTestOnContext rtoc = new RunTestOnContext();
    protected Logger logger = LoggerFactory.getLogger(AbstractIntegrationTest.class);

    protected String deploymentId;
    protected HttpClient httpClient;

    @BeforeAll
    public void prepare(Vertx vertx, VertxTestContext context) {
        httpClient = vertx.createHttpClient();
        deployVerticle(vertx).compose(id -> {
            deploymentId = id;
            logger.info("Verticle is deployed with id: " + deploymentId);
            return Future.succeededFuture();
        });
        context.completeNow();
    }

    public abstract Class<? extends Verticle> getClazz();

    public Future<String> deployVerticle(Vertx vertx) {
        DeploymentOptions options = new DeploymentOptions();
        options.setConfig(VertxConfiguration.instance(vertx).load().result());
        return vertx.deployVerticle(getClazz().getName(), options);
    }

    @AfterAll
    public void cleanup(Vertx vertx, VertxTestContext context) {
        vertx.undeploy(deploymentId)
            .onComplete(r -> {
                logger.info("Verticle with id: " + deploymentId + " is removed.");
            });
        context.completeNow();
    }

}
