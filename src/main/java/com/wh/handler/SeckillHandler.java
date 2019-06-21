package com.wh.handler;

import java.util.Date;
import com.wh.dao.PhoneDao;
import com.wh.domain.Phone;
import io.vertx.ext.web.RoutingContext;

/**
 * 
 * @ClassName: SeckillHandler
 * @author: hw83770
 * 
 */

public class SeckillHandler {

	private static SeckillHandler intstance = new SeckillHandler();

	PhoneDao phoneDao = PhoneDao.getInstance();

	public static SeckillHandler getInstance() {
		return intstance;
	}

	public void seckill(RoutingContext ctx) {
		System.out.println("handler seckill ... ");
		Long killId = Long.parseLong(ctx.request().getParam("id"));

		if (phoneDao.decrease() >= 0) {
			phoneDao.addPhone(new Phone(killId, "iphone8", 998, new Date().getTime()));
		}

		ctx.response().end();
	}

	public void exportHandler(RoutingContext ctx) {
		long seckillId = Long.parseLong(ctx.request().getParam("id"));
		boolean isStarted = exportSeckillUrl(seckillId);

		ctx.response().end();
	}

	private boolean exportSeckillUrl(long seckillId) {

		Phone phone = phoneDao.getById(seckillId);
		if (phone == null) // phone not exist
			return false;

		Date nowTime = new Date();
		if (nowTime.getTime() >= phone.exportTime) {

			return true;
		}
		return false;
	}
}
