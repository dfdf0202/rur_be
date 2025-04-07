package kr.co.rur.core.api.model.oauth.conf

import com.sun.jdi.IntegerValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.nio.charset.StandardCharsets
@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}")
    private var host: String,
    @Value("\${spring.data.redis.port}")
    private var port: String,
    @Value("\${spring.data.redis.database}")
    private var database: String
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        val config = RedisStandaloneConfiguration().apply {
            hostName = host
            port = port
            database = database
        }
        return LettuceConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        return RedisTemplate<String, Any>().apply {
            setConnectionFactory(redisConnectionFactory())
            setDefaultSerializer(StringRedisSerializer(StandardCharsets.UTF_8))
            setEnableDefaultSerializer(true)
        }
    }
}