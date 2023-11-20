package com.blog.service;

import com.blog.entity.Type;

import java.util.List;

/**
 * @author shoji
 */
public interface TypeService {
    /**
     * カテゴリ保存
     * @param type カテゴリ
     * @return ステータス値
     */
    int saveType(Type type);

    /**
     * カテゴリ名を取得
     * @param id カテゴリid
     * @return カテゴリ
     */
    Type getType(Integer id);

    /**
     * カテゴリ名でカテゴリ名を取得
     * @param name カテゴリ名
     * @return カテゴリ
     */
    Type getTypeByName(String name);

    /**
     * 全部のカテゴリを取得
     * @return カテゴリリスト
     */
    List<Type> getAllType();

    /**
     * ホームページ右側でカテゴリに当てはまるブログ数を展示
     * @return カテゴリリスト
     */
    List<Type> getBlogType();

    /**
     * カテゴリ更新
     * @param type カテゴリ
     * @return ステータス値
     */
    int updateType(Type type);

    /**
     * カテゴリ削除
     * @param id カテゴリid
     * @return ステータス値
     */
    int deleteType(Integer id);
}
