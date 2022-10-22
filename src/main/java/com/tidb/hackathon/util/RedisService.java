package com.tidb.hackathon.util;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Derek Cheng
 * @date 2020/12/17 9:05 下午
 */
public interface RedisService {

    /**
     * 设置 String 类型 key-value
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 获取 String 类型 key-value
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置 String 类型 key-value 并添加过期时间 (毫秒单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,毫秒单位
     */
    void setForTimeMS(String key, String value, long time);

    /**
     * 设置 String 类型 key-value 并添加过期时间 (分钟单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,分钟单位
     */
    void setForTimeMIN(String key, String value, long time);

    /**
     * 设置 String 类型 key-value 并添加过期时间 (分钟单位)
     *
     * @param key
     * @param value
     * @param time  过期时间,分钟单位
     */
    void setForTimeCustom(String key, String value, long time, TimeUnit type);

    /**
     * 如果 key 存在则覆盖,并返回旧值. 如果不存在,返回null 并添加
     *
     * @param key
     * @param value
     * @return
     */
    String getAndSet(String key, String value);

    /**
     * 批量添加 key-value (重复的键会覆盖)
     *
     * @param keyAndValue
     */
    void batchSet(Map<String, String> keyAndValue);

    /**
     * 批量添加 key-value 只有在键不存在时,才添加 map 中只要有一个key存在,则全部不添加
     *
     * @param keyAndValue
     */
    void batchSetIfAbsent(Map<String, String> keyAndValue);

    /**
     * 对一个 key-value 的值进行加减操作, 如果该 key 不存在 将创建一个key 并赋值该 number 如果 key 存在,但
     * value 不是长整型 ,将报错
     *
     * @param key
     * @param number
     */
    Long increment(String key, long number);

    /**
     * 对一个 key-value 的值进行加减操作, 如果该 key 不存在 将创建一个key 并赋值该 number 如果 key 存在,但
     * value 不是 纯数字 ,将报错
     *
     * @param key
     * @param number
     */
    Double increment(String key, double number);

    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param key
     * @param time
     * @param type
     * @return
     */
    boolean expire(String key, long time, TimeUnit type);

    /**
     * 移除指定key 的过期时间
     *
     * @param key
     * @return
     */
    boolean persist(String key);

    /**
     * 获取指定key 的过期时间
     *
     * @param key
     * @return
     */
    Long getExpire(String key);

    /**
     * 修改 key
     *
     * @param key
     * @return
     */
    void rename(String key, String newKey);

    /**
     * 删除 key-value
     *
     * @param key
     * @return
     */
    boolean delete(String key);

    /* ============================hash操作================================= */

    /**
     * 添加 Hash 键值对
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void put(String key, String hashKey, String value);

    /**
     * 批量添加 hash 的 键值对 有则覆盖,没有则添加
     *
     * @param key
     * @param map
     */
    void putAll(String key, Map<String, String> map);

    /**
     * 添加 hash 键值对. 不存在的时候才添加
     *
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    boolean putIfAbsent(String key, String hashKey, String value);

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key
     * @param hashKeys
     * @return 删除成功的 数量
     */
    Long deleteHash(String key, String... hashKeys);

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key
     * @param hashKey
     * @param number
     * @return
     */
    Long increment(String key, String hashKey, long number);

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key
     * @param hashKey
     * @param number
     * @return
     */
    Double increment(String key, String hashKey, Double number);

    /**
     * 获取指定 key 下的 hashkey
     *
     * @param key
     * @param hashKey
     * @return
     */
    Object getHashKey(String key, String hashKey);

    /**
     * 获取 key 下的 所有 hashkey 和 value
     *
     * @param key
     * @return
     */
    Map<Object, Object> getHashEntries(String key);

    /**
     * 验证指定 key 下 有没有指定的 hashkey
     *
     * @param key
     * @param hashKey
     * @return
     */
    boolean hashKey(String key, String hashKey);

    /**
     * 获取 key 下的 所有 hashkey 字段名
     *
     * @param key
     * @return
     */
    Set<Object> hashKeys(String key);

    /**
     * 获取指定 hash 下面的 键值对 数量
     *
     * @param key
     * @return
     */
    Long hashSize(String key);

    /* ===============================List 操作============================= */

    /**
     * 指定 list 从左入栈
     *
     * @param key
     * @return 当前队列的长度
     */
    Long leftPush(String key, Object value);

    /**
     * 指定 list 从左出栈 如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     *
     * @param key
     * @return 出栈的值
     */
    Object leftPop(String key);

    /**
     * 从左边依次入栈 导入顺序按照 Collection 顺序 如: a b c => c b a
     *
     * @param key
     * @param values
     * @return
     */
    Long leftPushAll(String key, Collection<Object> values);

    /**
     * 指定 list 从右入栈
     *
     * @param key
     * @return 当前队列的长度
     */
    Long rightPush(String key, Object value);

    /**
     * 指定 list 从右出栈 如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     *
     * @param key
     * @return 出栈的值
     */
    Object rightPop(String key);

    /**
     * 从右边依次入栈 导入顺序按照 Collection 顺序 如: a b c => a b c
     *
     * @param key
     * @param values
     * @return
     */
    Long rightPushAll(String key, Collection<Object> values);

    /**
     * 根据下标获取值
     *
     * @param key
     * @param index
     * @return
     */
    Object popIndex(String key, long index);

    /**
     * 获取列表指定长度
     *
     * @param key
     * @return
     */
    Long listSize(String key);

    /**
     * 获取列表 指定范围内的所有值
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> listRange(String key, long start, long end);

    /**
     * 删除 key 中 值为 value 的 count 个数.
     *
     * @param key
     * @param count
     * @param value
     * @return 成功删除的个数
     */
    Long listRemove(String key, long count, Object value);

    /**
     * 删除 列表 [start,end] 以外的所有元素
     *
     * @param key
     * @param start
     * @param end
     */
    void listTrim(String key, long start, long end);

    /**
     * 将 key 右出栈,并左入栈到 key2
     *
     * @param key  右出栈的列表
     * @param key2 左入栈的列表
     * @return 操作的值
     */
    Object rightPopAndLeftPush(String key, String key2);

    /* =================set 操作 无序不重复集合================= */

    /**
     * 添加 set 元素
     *
     * @param key
     * @param values
     * @return
     */
    Long addSet(String key, String... values);

    /**
     * 获取两个集合的差集
     *
     * @param key
     * @return
     */
    Set<Object> difference(String key, String otherkey);

    /**
     * 获取 key 和 集合 collections 中的 key 集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    Set<Object> difference(String key, Collection<Object> otherKeys);

    /**
     * 将 key 与 otherkey 的差集 ,添加到新的 newKey 集合中
     *
     * @param key
     * @param otherkey
     * @param newKey
     * @return 返回差集的数量
     */
    Long differenceAndStore(String key, String otherkey, String newKey);

    /**
     * 将 key 和 集合 collections 中的 key 集合的差集 添加到 newkey 集合中
     *
     * @param key
     * @param otherKeys
     * @param newKey
     * @return 返回差集的数量
     */
    Long differenceAndStore(String key, Collection<Object> otherKeys, String newKey);

    /**
     * 删除一个或多个集合中的指定值
     *
     * @param key
     * @param values
     * @return 成功删除数量
     */
    Long remove(String key, Object... values);

    /**
     * 随机移除一个元素,并返回出来
     *
     * @param key
     * @return
     */
    Object randomSetPop(String key);

    /**
     * 随机获取一个元素
     *
     * @param key
     * @return
     */
    Object randomSet(String key);

    /**
     * 随机获取指定数量的元素,同一个元素可能会选中两次
     *
     * @param key
     * @param count
     * @return
     */
    List<Object> randomSet(String key, long count);

    /**
     * 随机获取指定数量的元素,去重(同一个元素只能选择两一次)
     *
     * @param key
     * @param count
     * @return
     */
    Set<Object> randomSetDistinct(String key, long count);

    /**
     * 将 key 中的 value 转入到 destKey 中
     *
     * @param key
     * @param value
     * @param destKey
     * @return 返回成功与否
     */
    boolean moveSet(String key, Object value, String destKey);

    /**
     * 无序集合的大小
     *
     * @param key
     * @return
     */
    Long setSize(String key);

    /**
     * 判断 set 集合中 是否有 value
     *
     * @param key
     * @param value
     * @return
     */
    boolean isMember(String key, Object value);

    /**
     * 返回 key 和 othere 的并集
     *
     * @param key
     * @param otherKey
     * @return
     */
    Set<Object> unionSet(String key, String otherKey);

    /**
     * 返回 key 和 otherKeys 的并集
     *
     * @param key
     * @param otherKeys key 的集合
     * @return
     */
    Set<Object> unionSet(String key, Collection<Object> otherKeys);

    /**
     * 将 key 与 otherKey 的并集,保存到 destKey 中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return destKey 数量
     */
    Long unionAndStoreSet(String key, String otherKey, String destKey);

    /**
     * 将 key 与 otherKey 的并集,保存到 destKey 中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return destKey 数量
     */
    Long unionAndStoreSet(String key, Collection<Object> otherKeys, String destKey);

    /**
     * 返回集合中所有元素
     *
     * @param key
     * @return
     */
    Set<Object> members(String key);

    // Zset 根据 socre 排序 不重复 每个元素附加一个 socre double类型的属性(double 可以重复)

    /**
     * 添加 ZSet 元素
     *
     * @param key
     * @param value
     * @param score
     */
    boolean addZset(String key, Object value, double score);

    /**
     * 批量添加 Zset <br>
     * Set<TypedTuple<Object>> tuples = new HashSet<>();<br>
     * TypedTuple<Object> objectTypedTuple1 = new
     * DefaultTypedTuple<Object>("zset-5",9.6);<br>
     * tuples.add(objectTypedTuple1);
     *
     * @param key
     * @param tuples
     * @return
     */
    Long batchAddZset(String key, Set<TypedTuple<Object>> tuples);

    /**
     * Zset 删除一个或多个元素
     *
     * @param key
     * @param values
     * @return
     */
    Long removeZset(String key, String... values);

    /**
     * 对指定的 zset 的 value 值 , socre 属性做增减操作
     *
     * @param key
     * @param value
     * @param score
     * @return
     */
    Double incrementScore(String key, Object value, double score);

    /**
     * 获取 key 中指定 value 的排名(从0开始,从小到大排序)
     *
     * @param key
     * @param value
     * @return
     */
    Long rank(String key, Object value);

    /**
     * 获取 key 中指定 value 的排名(从0开始,从大到小排序)
     *
     * @param key
     * @param value
     * @return
     */
    Long reverseRank(String key, Object value);

    /**
     * 获取索引区间内的排序结果集合(从0开始,从小到大,带上分数)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<TypedTuple<Object>> rangeWithScores(String key, long start, long end);

    /**
     * 获取索引区间内的排序结果集合(从0开始,从小到大,只有列名)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> range(String key, long start, long end);

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从小到大,只有列名)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> rangeByScore(String key, double min, double max);

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从小到大,集合带分数)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max);

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从小到大,不带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count  输出指定元素数量
     * @return
     */
    Set<Object> rangeByScore(String key, double min, double max, long offset, long count);

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从小到大,带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count  输出指定元素数量
     * @return
     */
    Set<TypedTuple<Object>> rangeByScoreWithScores(String key, double min, double max, long offset, long count);

    /**
     * 获取索引区间内的排序结果集合(从0开始,从大到小,只有列名)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<Object> reverseRange(String key, long start, long end);

    /**
     * 获取索引区间内的排序结果集合(从0开始,从大到小,带上分数)
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Set<TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end);

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从大到小,集合不带分数)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<Object> reverseRangeByScore(String key, double min, double max);

    /**
     * 获取分数范围内的 [min,max] 的排序结果集合 (从大到小,集合带分数)
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Set<TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max);

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从大到小,不带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count  输出指定元素数量
     * @return
     */
    Set<Object> reverseRangeByScore(String key, double min, double max, long offset, long count);

    /**
     * 返回 分数范围内 指定 count 数量的元素集合, 并且从 offset 下标开始(从大到小,带分数的集合)
     *
     * @param key
     * @param min
     * @param max
     * @param offset 从指定下标开始
     * @param count  输出指定元素数量
     * @return
     */
    Set<TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double min, double max, long offset, long count);

    /**
     * 返回指定分数区间 [min,max] 的元素个数
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    long countZSet(String key, double min, double max);

    /**
     * 返回 zset 集合数量
     *
     * @param key
     * @return
     */
    long sizeZset(String key);

    /**
     * 获取指定成员的 score 值
     *
     * @param key
     * @param value
     * @return
     */
    Double score(String key, Object value);

    /**
     * 删除指定索引位置的成员,其中成员分数按( 从小到大 )
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    Long removeRange(String key, long start, long end);

    /**
     * 删除指定 分数范围 内的成员 [main,max],其中成员分数按( 从小到大 )
     *
     * @param key
     * @param min
     * @param max
     * @return
     */
    Long removeRangeByScore(String key, double min, double max);

    /**
     * key 和 other 两个集合的并集,保存在 destKey 集合中, 列名相同的 score 相加
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    Long unionAndStoreZset(String key, String otherKey, String destKey);

    /**
     * key 和 otherKeys 多个集合的并集,保存在 destKey 集合中, 列名相同的 score 相加
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    Long unionAndStoreZset(String key, Collection<String> otherKeys, String destKey);

    /**
     * key 和 otherKey 两个集合的交集,保存在 destKey 集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    Long intersectAndStore(String key, String otherKey, String destKey);

    /**
     * key 和 otherKeys 多个集合的交集,保存在 destKey 集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    Long intersectAndStore(String key, Collection<String> otherKeys, String destKey);
}
