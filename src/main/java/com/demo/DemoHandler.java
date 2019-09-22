package com.demo;

import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class DemoHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext rct) {
        Future<String> future = Future.future();
        rct.vertx().executeBlocking(
                (Future<String> future1) -> {
                    double sum =0;
                    for (int j=0;j<1000;j++)
                        for (int i=0;i<100000;i++){
                            sum=sum+Math.random();

                        }
                    future1.complete("hello");
                },
                future.completer()
        );
        future.setHandler(res -> {

            if(!res.succeeded()){
                Throwable err = res.cause();
                rct.response().end("error");
                return;
            }
            rct.response().end(res.result());
        });
    }



}
