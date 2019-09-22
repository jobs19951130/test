package com.demo;

import io.vertx.core.*;
import io.vertx.ext.web.Router;

public class MyVerticle  extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFut) throws Exception {
        Router router = Router.router(vertx);
        router.route("/demo").blockingHandler(new DemoHandler(),false);
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(9000, result -> { // (1)
                    if (!result.succeeded()) { // (2)
                        System.out.println("failed to start server, msg = " + result.cause());
                        startFut.fail(result.cause()); // (3)
                    }
                });
    }
    public static void main(String[] args) {
        Verticle myVerticle = new MyVerticle();
        VertxOptions vo = new VertxOptions();
        vo.setEventLoopPoolSize(16);
        vo.setWorkerPoolSize(64);

        Vertx vertx = Vertx.vertx(vo);
        System.out.println("----------------------------------------------------------");
        DeploymentOptions options = new DeploymentOptions();
        vertx.deployVerticle(myVerticle,options,res -> {

                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("++++++++++++++++++:" + res.succeeded());
            System.out.println("++++++++++++++++++:" + res.failed());
            System.out.println("++++++++++++++++++:" + res.cause());
            System.out.println("++++++++++++++++++:" + res.result());

            });
    }
}
