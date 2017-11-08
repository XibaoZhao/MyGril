package com.example.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.example.domain.Girl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	
	public RedisDao() {
	}
	
	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}

	private JedisPool jedisPool ;
	
	private RuntimeSchema<Girl> schema = RuntimeSchema.createFrom(Girl.class);
	
	

	public Girl getGirl(int id) {
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "girl:"+id;
				//序列化，存入redis
				byte[] bytes = jedis.get(key.getBytes());
				if(bytes!=null){
					Girl girl = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, girl, schema);
					//girl被反序列
					return girl;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public String putGirl2Redis(Girl girl){
		//序列化
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "girl:"+girl.getId();
				/*byte[] bytes = ProtostuffIOUtil.toByteArray(girl, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout = 60*60;
				String result = jedis.setex(key.getBytes(), timeout, bytes);
				return result;*/
				jedis.setex(key, 60*60, girl.toString());
			} finally{
				jedis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
