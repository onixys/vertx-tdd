package com.onixys.vertx.tdd.sample.health;

import com.onixys.vertx.tdd.parent.common.Status;
import com.onixys.vertx.tdd.sample.health.Health;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Health Test
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
@DisplayName("Health Test")
public class HealthTest {

    @Test
    public void health_initially_should_show_status_to_be_unknown() {
        Health health = new Health.Builder().build();
        assertThat(health.status()).isEqualTo(Status.UNKNOWN);
    }

    @Test
    public void health_up_should_change_status_to_up() {
        Health health = new Health.Builder().up().build();
        assertThat(health.status()).isEqualTo(Status.UP);
    }

    @Test
    public void health_down_should_change_status_to_down() {
        Health health = new Health.Builder().down().build();
        assertThat(health.status()).isEqualTo(Status.DOWN);
    }

    @Test
    public void health_unknown_should_change_status_to_unknown() {
        Health health = new Health.Builder().unknown().build();
        assertThat(health.status()).isEqualTo(Status.UNKNOWN);
    }

    @Test
    public void health_down_with_exception_should_throw_exception() {
        Health health = new Health.Builder().down(new RuntimeException("Something went wrong")).build();
        //assertThrows(RuntimeException.class, () -> {}, "Runtime Exception");
        assertEquals("RuntimeException: Something went wrong", health.details().get("exception message"));
        assertEquals("Something went wrong", health.ex().getMessage());
    }
}
