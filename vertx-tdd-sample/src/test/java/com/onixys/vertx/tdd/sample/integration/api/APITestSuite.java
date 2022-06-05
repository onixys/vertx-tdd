package com.onixys.vertx.tdd.sample.integration.api;

import com.onixys.vertx.tdd.sample.integration.AbstractIntegrationTest;
import com.onixys.vertx.tdd.sample.integration.dsl.VertxTestDSL;
import com.onixys.vertx.tdd.sample.verticle.MainVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * APITestSuite
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
@DisplayName("API Test Suite")
public class APITestSuite {

    @Nested
    @ExtendWith(VertxExtension.class)
    @Timeout(value = 5, timeUnit = TimeUnit.SECONDS)
    @DisplayName("Integration test - Service is healthy")
    class ServiceDeploymentTest extends AbstractIntegrationTest {
        @Override
        public Class<? extends Verticle> getClazz() {
            return MainVerticle.class;
        }

        @Test
        public void service_is_healthy_when_deployed(VertxTestContext context) {
            httpClient.request(
                    HttpMethod.GET,
                    VertxTestDSL.instance().HTTP_PORT,
                    VertxTestDSL.instance().HTTP_HOST,
                    "/health/readiness")
                .compose(req -> req.send().compose(HttpClientResponse::body))
                .onComplete(context.succeeding(buffer -> context.verify(() -> {
                    JsonObject responseJson = new JsonObject(buffer.toString());
                    assertEquals("UP", responseJson.getString("status"));
                    logger.info("service_is_healthy_when_deployed: " + Thread.currentThread().getName());
                    context.completeNow();
                })));
        }

        @Test
        public void service_is_healthy_10_checks(Vertx vertx, VertxTestContext context) {
            Checkpoint serverStarted = context.checkpoint();
            Checkpoint requestsServed = context.checkpoint(10);
            Checkpoint responsesReceived = context.checkpoint(10);

            vertx.createHttpServer()
                .requestHandler(req -> {
                    req.response().end("Ok");
                    requestsServed.flag();
                })
                .listen(8888)
                .onComplete(context.succeeding(httpServer -> {
                    serverStarted.flag();

                    HttpClient client = vertx.createHttpClient();
                    for (int i = 0; i < 10; i++) {
                        client.request(HttpMethod.GET, 8888, "localhost", "/")
                            .compose(req -> req.send().compose(HttpClientResponse::body))
                            .onComplete(context.succeeding(buffer -> context.verify(() -> {
                                assertEquals("Ok", buffer.toString());
                                responsesReceived.flag();
                            })));
                    }
                }));
        }
    }
}
