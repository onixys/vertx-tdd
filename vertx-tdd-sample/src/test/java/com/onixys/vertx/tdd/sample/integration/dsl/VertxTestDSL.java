package com.onixys.vertx.tdd.sample.integration.dsl;

/**
 * Vertx Test DSL
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class VertxTestDSL {
    private static VertxTestDSL instance;
    public String HTTP_HOST = "localhost";
    public int HTTP_PORT = 4000;
    public String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";
    public String DATE_PATTERN = "\\d{4}-[01]\\d-[0-3]\\dT[0-2]\\d:[0-5]\\d:[0-5]\\d([+-][0-2]\\d:[0-5]\\d|Z)";

    public synchronized static VertxTestDSL instance() {
        if (instance == null) {
            instance = new VertxTestDSL();
        }

        return instance;
    }
}
