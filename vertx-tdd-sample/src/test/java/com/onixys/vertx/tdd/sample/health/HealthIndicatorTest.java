package com.onixys.vertx.tdd.sample.health;

import com.onixys.vertx.tdd.parent.common.Status;
import com.onixys.vertx.tdd.sample.health.Health;
import com.onixys.vertx.tdd.sample.health.HealthIndicator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Health Indicator Test
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
@DisplayName("Health Indicator Test")
public class HealthIndicatorTest {

    @Test
    public void health_check_call_to_up_method_should_change_health_status_to_up() {
        HealthIndicator indicator = Health.Builder::up;
        assertThat(indicator.health().status()).isEqualTo(Status.UP);
    }

    @Test
    public void health_check_call_to_down_method_should_change_health_status_to_down() {
        HealthIndicator indicator = Health.Builder::down;
        assertThat(indicator.health().status()).isEqualTo(Status.DOWN);
    }

    @Test
    public void health_check_call_to_unknown_method_should_change_health_status_to_unknown() {
        HealthIndicator indicator = Health.Builder::unknown;
        assertThat(indicator.health().status()).isEqualTo(Status.UNKNOWN);
    }
}
