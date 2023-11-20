package com.blog.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redisの操作
 *
 **/
@SuppressWarnings("all")
public interface RedisService {
    /**
     * 保存するプロパティ
     *
     * @param key   キー
     * @param value バリュー
     * @param time  タイムスタンプ
     */
    void set(String key, Object value, long time);

    /**
     * プロパティを保存する
     *
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * プロパティを取得
     *
     * @param key
     * @return オブジェクト
     */
    Object get(String key);

    /**
     * プロパティを削除する
     *
     * @param key
     * @return 削除の結果（trueー成功）
     */
    Boolean del(String key);

    /**
     * プロパティをまとめて削除する
     *
     * @param keys キーのリスト
     * @return 削除した数
     */
    Long del(List<String> keys);

    /**
     * 時間制限の設置
     *
     * @param key
     * @param time タイムスタンプ
     * @return 期限切ったかどうかの結果（trueー期限切れ）
     */
    Boolean expire(String key, long time);

    /**
     * 切れた時のタイムスタンプを取得
     *
     * @param key
     * @return タイムスタンプ
     */
    Long getExpire(String key);

    /**
     * キーの存在を調べる
     *
     * @param key
     * @return trueー存在している
     */
    Boolean hasKey(String key);

    /**
     * デルタ値でインクリする
     *
     * @param key
     * @param delta delta值
     * @return インクリメント後の結果
     */
    Long incr(String key, long delta);

    /**
     * デルタ値でデクリする
     *
     * @param key
     * @param delta delta值
     * @return デクリメント後の結果
     */
    Long decr(String key, long delta);

    /**
     * ハッシュ内のプロパティを取得
     *
     * @param key     外部key值
     * @param hashKey 内部key值
     * @return 内部のkeyとvalue
     */
    Object hGet(String key, String hashKey);

    /**
     * ハッシュにプロパティを追加
     *
     * @param key     外部key
     * @param hashKey 内部key
     * @param value   内部keyのvalue
     * @param time    expire時間
     * @return trueー成功
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * ハッシュにプロパティを追加（void版）
     *
     * @param key     外部key
     * @param hashKey 内部key
     * @param value   内部keyのvalue
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * ハッシュオブジェクトを取得
     *
     * @param key 外部key值
     * @return hashMap
     */
    Map hGetAll(String key);

    /**
     * ハッシュのセット
     *
     * @param key  外部key
     * @param map  hashMap值
     * @param time expire時間
     * @return trueー成功
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * ハッシュのセット（void版）
     *
     * @param key 外部key
     * @param map hashMap值
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * ハッシュ内のプロパティを削除
     *
     * @param key     外部key值
     * @param hashKey 内部key值
     */
    void hDel(String key, Object... hashKey);

    /**
     * ハッシュ内にプロパティの存在を調べる
     *
     * @param key     外部key
     * @param hashKey 内部key
     * @return trueー存在
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * ハッシュ内のプロパティをインクリさせる
     *
     * @param key     外部key
     * @param hashKey 内部key
     * @param delta   インクリメントする条件
     * @return インクリメント後のデータ
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * ハッシュ内のプロパティをデクリさせる
     *
     * @param key     外部key
     * @param hashKey 内部key
     * @param delta   デクリメントの条件
     * @return デクリメント後のデータ
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * Setの内容を取得
     *
     * @param key key
     * @return set
     */
    Set<Object> sMembers(String key);

    /**
     * Set内にプロパティを追加
     *
     * @param key    key
     * @param values valueの集合
     * @return 入れたプロパティの数
     */
    Long sAdd(String key, Object... values);

    /**
     * Set内にプロパティを追加（タイムスタンプ付き版）
     *
     * @param key    key
     * @param time   expire時間
     * @param values valueの集合
     * @return 入れたプロパティの数
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * ハッシュ内にプロパティの存在を調べる
     *
     * @param key   key
     * @param value value
     * @return trueー存在
     */
    Boolean sIsMember(String key, Object value);

    /**
     * Setのサイズ（要素数）を取得
     *
     * @param key key
     * @return 要素数
     */
    Long sSize(String key);

    /**
     * Set内のプロパティを削除する
     *
     * @param key    key
     * @param values valueの集合
     * @return 削除したプロパティの数
     */
    Long sRemove(String key, Object... values);

    /**
     * List内のプロパティを取得
     *
     * @param key   key
     * @param start 始まりのインデックス
     * @param end   終わりのインデックス
     * @return オブジェクトのlist
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * Listのサイズを取得
     *
     * @param key key
     * @return サイズ
     */
    Long lSize(String key);

    /**
     * インデックスでプロパティを取得
     *
     * @param key   key
     * @param index
     * @return オブジェクト
     */
    Object lIndex(String key, long index);

    /**
     * Listにプロパティを追加
     *
     * @param key   key
     * @param value value
     * @return 追加後のサイズ
     */
    Long lPush(String key, Object value);

    /**
     * Listにプロパティを追加（タイムスタンプ付き）
     *
     * @param key   key
     * @param value value
     * @param time  expire時間
     * @return 追加後のサイズ
     */
    Long lPush(String key, Object value, long time);

    /**
     * まとめてListにプロパティを追加
     *
     * @param key    key
     * @param values valueの集合
     * @return 追加後のサイズ
     */
    Long lPushAll(String key, Object... values);

    /**
     * まとめてListにプロパティを追加（タイムスタンプ付き）
     *
     * @param key    key
     * @param time   expire時間
     * @param values valueの集合
     * @return 追加後のサイズ
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * Listからプロパティを削除
     *
     * @param key   key
     * @param count 削除した数
     * @param value value
     * @return 削除後のサイズ
     */
    Long lRemove(String key, long count, Object value);

    /**
     * bitmapに値を追加
     *
     * @param key    key
     * @param offset
     * @param b      ステータス
     * @return 结果
     */
    Boolean bitAdd(String key, int offset, boolean b);

    /**
     * 从bitmap中を取得偏移量的值
     *
     * @param key    key
     * @param offset 偏移量
     * @return 结果
     */
    Boolean bitGet(String key, int offset);

    /**
     * を取得bitmap的key值总和
     *
     * @param key key
     * @return 总和
     */
    Long bitCount(String key);

    /**
     * を取得bitmap范围值
     *
     * @param key    key
     * @param limit  范围
     * @param offset 开始偏移量
     * @return long类型集合
     */
    List<Long> bitField(String key, int limit, int offset);

    /**
     * を取得全部のbitmap
     *
     * @param key key
     * @return 以二进制字节数组返回
     */
    byte[] bitGetAll(String key);

    /**
     * 增加坐标
     *
     * @param key  key
     * @param x    x
     * @param y    y
     * @param name 地点名称
     * @return 返回结果
     */
    Long geoAdd(String key, Double x, Double y, String name);

    /**
     * 根据城市名称を取得坐标集合
     *
     * @param key   key
     * @param place 地点
     * @return 坐标集合
     */
    List<Point> geoGetPointList(String key, Object... place);

    /**
     * 计算两个城市之间的距离
     *
     * @param key      key
     * @param placeOne 地点1
     * @param placeTow 地点2
     * @return 返回距离
     */
    Distance geoCalculationDistance(String key, String placeOne, String placeTow);

    /**
     * を取得附该地点附近的其他地点
     *
     * @param key      key
     * @param place    地点
     * @param distance 附近的范围
     * @param limit    查几条
     * @param sort     排序规则
     * @return 返回附近的地点集合
     */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoNearByPlace(String key, String place, Distance distance, long limit, Sort.Direction sort);

    /**
     * を取得地点的hash
     *
     * @param key   key
     * @param place 地点
     * @return 返回集合
     */
    List<String> geoGetHash(String key, String... place);
}

