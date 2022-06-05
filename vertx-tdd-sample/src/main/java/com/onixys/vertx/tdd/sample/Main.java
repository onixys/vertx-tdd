package com.onixys.vertx.tdd.sample;

import com.onixys.vertx.tdd.sample.verticle.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.util.concurrent.TimeUnit;

/**
 * Main
 *
 * @author Hamid Kianzad
 * @version 1.0.0
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions()
            .setWarningExceptionTime(1)
            .setMaxEventLoopExecuteTime(500)
            .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
            .setBlockedThreadCheckInterval(500)
            .setBlockedThreadCheckIntervalUnit(TimeUnit.MILLISECONDS);

        Vertx vertx = Vertx.vertx(vertxOptions);
        vertx.deployVerticle(new MainVerticle());
    }
}
