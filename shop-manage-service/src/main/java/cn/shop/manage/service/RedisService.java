package cn.shop.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
	
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	public String get(String key, String value) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return shardedJedis.set(key, value);
		} finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}
		
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * 		秒
	 * @return
	 */
	public String set(final String key, final String value, final Integer seconds) {
		return execute(new RedisCallback<String, ShardedJedis>() {
			@Override
			public String doInRedis(ShardedJedis e) {
				return e.setex(key, seconds, value);
			}
		});
	}
	
	public Long delete(final String key) {
		return execute(new RedisCallback<Long, ShardedJedis>() {
			@Override
			public Long doInRedis(ShardedJedis e) {
				return e.del(key);
			}
		});
	}
	
	public String get(final String key) {
		return execute(new RedisCallback<String, ShardedJedis>() {
			@Override
			public String doInRedis(ShardedJedis e) {
				return e.get(key);
			}
		});
	}
//	public String get(String key) {
//		ShardedJedis shardedJedis = null;
//		try {
//			
//			shardedJedis = shardedJedisPool.getResource();
//			
//			return shardedJedis.get(key);
//		} finally {
//			if (shardedJedis != null) {
//				shardedJedis.close();
//			}
//		}
//		
//	}
	
	private <T> T execute(RedisCallback<T, ShardedJedis> redisCallback) {
		ShardedJedis shardedJedis = null;
		try {
			shardedJedis = shardedJedisPool.getResource();
			return redisCallback.doInRedis(shardedJedis);
		} finally {
			if (shardedJedis != null) {
				shardedJedis.close();
			}
		}
	}
 }
