package com.onixys.vertx.tdd.sample.health;

/**
 * Health Indicator
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface HealthIndicator {
    default Health health() {
        Health.Builder builder = new Health.Builder();
        this.healthCheck(builder);
        return builder.build();
    }

    void healthCheck(Health.Builder builder);
}
