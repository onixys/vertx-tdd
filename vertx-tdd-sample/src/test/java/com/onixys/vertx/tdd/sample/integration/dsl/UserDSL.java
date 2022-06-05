package com.onixys.vertx.tdd.sample.integration.dsl;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class UserDSL {
    public static class ITUser {
        private final String id;
        private final String username;
        private final String about;
        private final String password;

        ITUser(String id, String username, String password, String about) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.about = about;
        }

        public String id() {
            return id;
        }

        public String username() {
            return username;
        }

        public String about() {
            return about;
        }

        public String password() {
            return password;
        }

        @Override
        public boolean equals(Object other) {
            return reflectionEquals(this, other);
        }

        @Override
        public String toString() {
            return reflectionToString(this, MULTI_LINE_STYLE);
        }
    }
}
