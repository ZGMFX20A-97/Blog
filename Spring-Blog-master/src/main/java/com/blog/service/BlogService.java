package com.blog.service;

import com.blog.entity.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author shoji
 */
public interface BlogService {

    /**
     * idで文章を検索
     * @param id 文章id
     * @return 文章
     */
    Blog getBlog(Long id);

    /**
     * ブログをフロントで展示
     * @param id 文章id
     * @return 文章
     */
    Blog getDetailedBlog(Long id);

    /**
     * すべての文章を取得
     * @return 文章list
     */
    List<Blog> getAllBlog();

    /**
     * カテゴリでブログ取得
     * @param typeId カテゴリid
     * @return 文章list
     */
    List<Blog> getByTypeId(Integer typeId);

    /**
     * タグでブログ取得
     * @param tagId タグid
     * @return 文章list
     */
    List<Blog> getByTagId(Integer tagId);

    /**
     * トップページの展示
     * @return 文章list
     */
    List<Blog> getIndexBlog();

    /**
     * おすすめブログの展示
     * @return 文章list
     */
    List<Blog> getAllRecommendBlog();

    /**
     * ブログのグローバル検索
     * @param query キーワード
     * @return 文章list
     */
    List<Blog> getSearchBlog(String query);

    /**
     * ブログのアーカイブ
     * @return 文章list
     */
    Map<String,List<Blog>> archiveBlog();

    /**
     * ブログ数の調べ
     * @return 文章の数
     */
    int countBlog();

    /**
     * ブログの保存
     * @param blog 文章
     * @return ステータス（数字で表す）
     */
    int saveBlog(Blog blog);

    /**
     * ブログを更新
     * @param blog 文章
     * @return ステータス（数字で表す）
     */
    int updateBlog(Blog blog);

    /**
     * ブログを削除
     * @param id 文章id
     * @return ステータス（数字で表す）
     */
    int deleteBlog(Long id);

    /**
     * バックでタイトル・カテゴリ・おすすめによりブログを検索
     * @param blog 文章
     * @return 文章list
     */
    List<Blog> searchAllBlog(Blog blog);

    /**
     * Topブログの取得
     * @return ステータス（数字で表す）
     */
    List<Blog> getHotBlog();
}
