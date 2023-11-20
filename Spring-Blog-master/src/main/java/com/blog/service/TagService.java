package com.blog.service;

import com.blog.entity.Tag;

import java.util.List;

/**
 * @author shoji
 */
public interface TagService {
    /**
     * 保存タグ
     * @param tag タグ
     * @return ステータス値
     */
    int saveTag(Tag tag);

    /**
     * タグを取得
     * @param id タグid
     * @return タグ
     */
    Tag getTag(Integer id);

    /**
     * タグネームでタグを取得
     * @param  name タグ名
     * @return タグ
     */
    Tag getTagByName(String name);

    /**
     * 全部の的タグ名を取得
     * @return タグリスト
     */
    List<Tag> getAllTag();

    /**
     * ホームページでブログタグ展示
     * @return タグリスト
     */
    List<Tag> getBlogTag();

    /**
     * Stringからtagコレクションを取得
     * @param text タグ名
     * @return タグリスト
     */
    List<Tag> getTagByString(String text);

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
