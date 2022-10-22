//package com.tidb.hackathon.config;
//
///**
// * @author Derek Cheng
// * @date 2020/12/17 8:57 下午
// * 连接池注入配置信息
// * spring boot框架中已经集成了redis，
// * 在1.x.x的版本时默认使用的jedis客户端，
// * 现在是2.x.x版本默认使用的lettuce客户端，两种客户端的区别如下
// * Jedis和Lettuce都是Redis Client
// * Jedis 是直连模式，在多个线程间共享一个 Jedis 实例时是线程不安全的，
// * 如果想要在多线程环境下使用 Jedis，需要使用连接池，
// * 每个线程都去拿自己的 Jedis 实例，当连接数量增多时，物理连接成本就较高了。
// * Lettuce的连接是基于Netty的，连接实例可以在多个线程间共享，
// * 所以，一个多线程的应用可以使用同一个连接实例，而不用担心并发线程的数量。
// * 当然这个也是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
// * 通过异步的方式可以让我们更好的利用系统资源，而不用浪费线程等待网络或磁盘I/O。
// * Lettuce 是基于 netty 的，netty 是一个多线程、事件驱动的 I/O 框架，
// * 所以 Lettuce 可以帮助我们充分利用异步的优势。
// */
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@AutoConfigureAfter(RedisAutoConfiguration.class)
//public class RedisConfig {
//    @Bean
//    public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        redisTemplate.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
////        template.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setValueSerializer(stringRedisSerializer);
//        // hash的value序列化方式采用jackson
////        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setHashValueSerializer(stringRedisSerializer);
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}
