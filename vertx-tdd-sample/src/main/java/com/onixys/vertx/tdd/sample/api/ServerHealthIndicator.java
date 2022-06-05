package com.onixys.vertx.tdd.sample.api;

import com.onixys.vertx.tdd.sample.health.Health;
import com.onixys.vertx.tdd.sample.health.HealthIndicator;

/**
 * ServerHealthIndicator
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class ServerHealthIndicator implements HealthIndicator {
    @Override
    public void healthCheck(Health.Builder builder) {
        builder.up();
    }
}
