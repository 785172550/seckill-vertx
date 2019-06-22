package com.wh.start;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;

/*

系统设计： 
* 
* 详情页 (CDN 缓存) -> 系统时间 -> 倒计时js 
*   |
*   v
* 地址暴露接口 (后端优化)
*   |
*   V
* 执行秒杀操作 (后端优化)
*   |
*   V
* 返回结果
* 
* -------------
* 
*  秒杀操作 事务  网络延迟 GC
*  update 减库存
*  insert 购买明细
*  commit
*/

/**
 * @ClassName: ServerStarter
 * @author: hw83770
 * @desc this is a simple demo of seckill web application
 */
@Slf4j
public class ServerStarter {

    public static void main(String[] args) {
        log.info("Server starting ...");

        VertxOptions options = new VertxOptions();
        options.setEventLoopPoolSize(2);
        Vertx vertx = Vertx.vertx(options); // create vertx app

        DeploymentOptions depOps = new DeploymentOptions();
        depOps.setInstances(2);
        vertx.deployVerticle(SeckillVerticle.class, depOps, ar -> { // deploy a verticle(an event loop app)
            if (ar.succeeded()) {
                log.info("done deployment");
            } else {
                log.error("deploy error: ", ar.cause());
            }
        });

    }

}
