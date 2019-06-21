package com.wh.start;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * 
 * @ClassName: TestStart
 * @author: hw83770
 * 
 */
@RunWith(VertxUnitRunner.class)
public class TestStart {
	private Vertx vertx;

	@Before
	public void setUp(TestContext context) throws Exception {
		vertx = Vertx.vertx();
		vertx.deployVerticle(SeckillVerticle.class.getName(), context.asyncAssertSuccess());
	}

	@Test
	public void testMyApplication(TestContext context) {
		final Async async = context.async();

		vertx.createHttpClient().getNow(9527, "localhost", "/seckill", response -> {
			response.handler(body -> {
				context.assertTrue(body.toString().contains("hello"));
				async.complete();
			});
		});
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

}
