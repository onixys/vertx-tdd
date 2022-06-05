package com.onixys.vertx.tdd.sample.health;

import com.onixys.vertx.tdd.parent.common.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * Health
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class Health {

    private final Status status;
    private final Map<String, Object> details;
    private final Throwable ex;

    private Health(Builder builder) {
        this.status = builder.status;
        this.details = builder.details;
        this.ex = builder.ex;
    }

    public Status status() {
        return status;
    }

    public Map<String, Object> details() {
        return details;
    }

    public Throwable ex() {
        return ex;
    }

    public static class Builder {
        private Status status = Status.UNKNOWN;
        private Map<String, Object> details =  new HashMap<>();
        private Throwable ex;

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder details(Map<String, Object> details) {
            this.details = details;
            return this;
        }

        public Builder up() {
            this.status(Status.UP);
            return this;
        }

        public Builder down() {
            this.status(Status.DOWN);
            return this;
        }

        public Builder down(Throwable ex) {
            this.down().exception(ex);
            return this;
        }

        public Builder unknown() {
            this.status(Status.UNKNOWN);
            return this;
        }

        public Builder exception(Throwable ex) {
            this.ex = ex;
            Map<String, Object> details = new HashMap<>();
            details.put("exception message", ex.getClass().getSimpleName() + ": " + ex.getMessage());
            details.put("exception cause", ex.getClass().getSimpleName() + ": " + ex.getCause());

            return this.details(details);
        }

        public Health build() {
            Health health = new Health(this);
            return health;
        }
    }
}
