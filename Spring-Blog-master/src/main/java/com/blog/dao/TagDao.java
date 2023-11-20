package com.blog.dao;

import com.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author shoji
 */
@Mapper
@Repository
public interface TagDao {
    /**
     * タグ保存
     * @param tag タグ
     * @return ステータス値
     */
    int saveTag(Tag tag);

    /**
     * タグ数を取得
     * @return タグ数
     */
    int getCount();

    /**
     * タグを取得
     * @param id タグid
     * @return タグ
     */
    Tag getTag(Integer id);

    /**
     * ネームでタグを取得
     * @param name
     * @return タグ
     */
    Tag getTagByName(String name);

    /**
     * 全部のタグを取得
     * @return タグリスト
     */
    List<Tag> getAllTag();

    /**
     * ホームページブログタグ展示
     * @return タグリスト
     */
    List<Tag> getBlogTag();

    /**
     * タグ更新
     * @param tag タグ
     * @return ステータス値
     */
    int updateTag(Tag tag);

    /**
     * タグ削除
     * @param id タグid
     * @return ステータス値
     */
    int deleteTag(Integer id);
}
