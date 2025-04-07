package kr.co.rur.core.api.support.handler

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component

@Component
class RedisHandler(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun set(key: String, value: String) {
        val valueOps: ValueOperations<String, Any> = redisTemplate.opsForValue()
        valueOps.set(key, value)
    }

    fun put(key: String, hashKey: String, value: Any) {
        redisTemplate.opsForHash<String, Any>().put(key, hashKey, value)
    }

    fun get(key: String, hashKey: String): String? {
        return redisTemplate.opsForHash<String, Any>().get(key, hashKey) as? String
    }

    fun get(key: String): String? {
        val value = redisTemplate.opsForValue().get(key)
        return value as? String
    }

    fun delete(key: String, hashKey: String) {
        redisTemplate.opsForHash<String, Any>().delete(key, hashKey)
    }

}