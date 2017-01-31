package cn.shop.manage.service;

public interface RedisCallback<T, E> {

	T doInRedis(E e);
}
