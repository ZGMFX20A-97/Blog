package com.blog.dao;

import com.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shoji
 */
@Mapper
@Repository
public interface TypeDao {
    /**
     * カテゴリ保存
     * @param type カテゴリ
     * @return ステータス値
     */
    int saveType(Type type);

    /**
     * カテゴリ数を取得
     * @return カテゴリ数
     */
    int getCount();

    /**
     * カテゴリを取得
     * @param id カテゴリid
     * @return カテゴリ
     */
    Type getType(Integer id);

    /**
     * カテゴリ名で検索
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
     * ホームページ右側でカテゴリに当てはまるのブログ数を展示
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
