package com.wh.dao;

import com.wh.domain.Phone;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: PhoneDao
 * @author: hw83770
 */

public class PhoneDao {

    private static int productNum = 2000;
    private static ConcurrentHashMap<Long, Phone> database = new ConcurrentHashMap<>(productNum);
    private static AtomicInteger count = new AtomicInteger(productNum);

    private static PhoneDao phoneDao = new PhoneDao();

    public static PhoneDao getInstance() {
        return phoneDao;
    }

    public int getCount() {
        return count.get();
    }

    public Phone getById(long id) {
        return database.get(id); // protostuff serialize
    }

    public int decrease() {
        return count.decrementAndGet();
    }

    public void addPhone(Phone phone) {
        database.put(phone.id, phone);
        /*
         * String key = "seckill:" + phone.id; byte[] bytes =
		 * ProtostuffIOUtil.toByteArray(phone, schema,
		 * LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE)); // 超时缓存 int timeout
		 * = 60*60; // 1 hour String res = jedis.setex(key.getBytes(), timeout, bytes);
		 * 
		 */

    }

}
