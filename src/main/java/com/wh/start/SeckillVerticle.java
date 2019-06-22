package com.wh.start;

import com.wh.handler.SeckillHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: SeckillVerticle
 * @author: hw83770
 */

@Slf4j
public class SeckillVerticle extends AbstractVerticle {

    private final static int port = 9527;

    @Override
    public void start(Future<Void> startFut) throws Exception {

        Router router = Router.router(vertx);
        configRouter(router);

        // create http server
        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(port, result -> {
                    if (!result.succeeded()) {
                        log.error("failed to start server, msg = ", result.cause());
                        startFut.fail(result.cause());
                    } else {
                        log.info("Server listen ...", port);
                        startFut.complete();
                    }
                });

    }

    private static void configRouter(Router router) {
        SeckillHandler seckill = SeckillHandler.getInstance();
        // complicated version
        router.get("/getInfo").handler(new Handler<RoutingContext>() {

            @Override
            public void handle(RoutingContext event) {
                HttpServerRequest req = event.request();
                String accept = req.getHeader("Accept");
                String agent = req.getHeader("User-Agent");

                log.info("http header accept: " + accept);
                log.info("http header agent: " + agent);

                String name = req.getParam("name");
                event.response().end("haha: " + name);
            }
        });

        // simple version
        router.route("/seckill").handler(ctx -> {
            ctx.response().end("hello: " + ctx.request().getParam("name"));
        });
        router.route("/kill_phone").handler(seckill::seckill);
    }

}
