package com.onixys.vertx.tdd.sample.config;

import com.onixys.vertx.tdd.parent.common.Environment;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

/**
 * Vertx Configuration
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class VertxConfiguration {
    private static VertxConfiguration instance;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Vertx vertx;
    private String configFileName;

    private VertxConfiguration(Vertx vertx) {
        this.vertx = vertx;
    }

    public static synchronized VertxConfiguration instance(Vertx vertx) {
        if (instance == null) {
            instance = new VertxConfiguration(vertx);
        }
        return instance;
    }

    public Future<JsonObject> load() {
        return environment(vertx)
            .compose(this::environmentVariables)
            .compose(this::load);
    }

    private Future<JsonObject> load(Void unused) {
        return configuration(vertx);
    }

    private Future<Void> environmentVariables(JsonObject variables) {
        logger.info("Environment: " + variables.getString("VERTXWEB_ENVIRONMENT"));
        String env = variables.getString("VERTXWEB_ENVIRONMENT");
        configFileName = "conf/" + env + "-config.conf";

        if (environment(env).equals(Environment.UNKNOWN)) {
            throw new RuntimeException("Please set the environment variable 'VERTXWEB_ENVIRONMENT' to prod, dev or test");
        }
        return Future.succeededFuture();
    }

    private Environment environment(String env) {
        switch (env) {
            case "dev":
                return Environment.DEV;
            case "prod":
                return Environment.PROD;
            case "test":
                return Environment.TEST;
            default:
                return Environment.UNKNOWN;
        }
    }

    private Future<JsonObject> environment(Vertx vertx) {
        ConfigStoreOptions storeOptions = new ConfigStoreOptions().setType("env");
        return retrieveConfig(vertx, storeOptions);
    }

    private Future<JsonObject> retrieveConfig(Vertx vertx, ConfigStoreOptions storeOptions) {
        ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(storeOptions);
        ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
        return retriever.getConfig();
    }

    private Future<JsonObject> configuration(Vertx vertx) {
        ConfigStoreOptions storeOptions = new ConfigStoreOptions()
            .setType("file")
            .setFormat("hocon")
            .setConfig(new JsonObject().put("path", configFileName));

        return retrieveConfig(vertx, storeOptions);
    }
}
