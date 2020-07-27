package com.piggy.quincy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis操作接口
 *
 * @author IMNOTHD
 * @date 2020/5/24 4:17
 */
public interface RedisService {
    /**
     * 保存属性
     */
    void set(String key, Object value, long time);

    /**
     * 保存属性
     */
    void set(String key, Object value);

    /**
     * 获取属性
     */
    Object get(String key);

    /**
     * 删除属性
     */
    Boolean del(String key);

    /**
     * 批量删除属性
     */
    Long del(List<String> keys);

    /**
     * 设置过期时间
     */
    Boolean expire(String key, long time);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    /**
     * 判断是否有该属性
     */
    Boolean hasKey(String key);

    /**
     * 按delta递增
     */
    Long incr(String key, long delta);

    /**
     * 按delta递减
     */
    Long decr(String key, long delta);

    /**
     * 获取Hash结构中的属性
     */
    Object hashGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hashSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     */
    void hashSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hashGetAll(String key);

    /**
     * 直接设置整个Hash结构
     */
    Boolean hashSetAll(String key, Map<String, Object> map, long time);

    /**
     * 直接设置整个Hash结构
     */
    void hashSetAll(String key, Map<String, Object> map);

    /**
     * 删除Hash结构中的属性
     */
    void hashDel(String key, Object... hashKey);

    /**
     * 判断Hash结构中是否有该属性
     */
    Boolean hashHasKey(String key, String hashKey);

    /**
     * Hash结构中属性递增
     */
    Long hashIncr(String key, String hashKey, Long delta);

    /**
     * Hash结构中属性递减
     */
    Long hashDecr(String key, String hashKey, Long delta);

    /**
     * 获取Set结构
     */
    Set<Object> setMembers(String key);

    /**
     * 向Set结构中添加属性
     */
    Long setAdd(String key, Object... values);

    /**
     * 向Set结构中添加属性
     */
    Long setAddWithExpireTime(String key, long time, Object... values);

    /**
     * 是否为Set中的属性
     */
    Boolean setIsMember(String key, Object value);

    /**
     * 获取Set结构的长度
     */
    Long setSize(String key);

    /**
     * 删除Set结构中的属性
     */
    Long setRemove(String key, Object... values);

    /**
     * 获取List结构中的属性
     */
    List<Object> listRange(String key, long start, long end);

    /**
     * 获取List结构的长度
     */
    Long listSize(String key);

    /**
     * 根据索引获取List中的属性
     */
    Object listIndex(String key, long index);

    /**
     * 向List结构中添加属性
     */
    Long listRightPush(String key, Object value);

    /**
     * 向List结构中添加属性
     */
    Long listRightPush(String key, Object value, long time);

    /**
     * 向List结构中批量添加属性
     */
    Long listRightPushAll(String key, Object... values);

    /**
     * 向List结构中批量添加属性
     */
    Long listRightPushAll(String key, Long time, Object... values);

    /**
     * 弹出list右侧元素
     */
    Object listRightPop(String key);

    /**
     * 从List结构中移除属性
     */
    Long listRemove(String key, long count, Object value);
}
