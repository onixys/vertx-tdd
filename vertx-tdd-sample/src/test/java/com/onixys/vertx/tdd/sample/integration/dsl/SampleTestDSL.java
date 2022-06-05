package com.onixys.vertx.tdd.sample.integration.dsl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.onixys.vertx.tdd.sample.integration.dsl.UserDSL.ITUser;

public class SampleTestDSL {

    private static Logger logger = LoggerFactory.getLogger(SampleTestDSL.class);
    private static final String BASE_URL = "http://localhost:4000";

    public static ITUser register(ITUser user) {
        logger.info("Register user: " + user);
        return null;
    }
}
