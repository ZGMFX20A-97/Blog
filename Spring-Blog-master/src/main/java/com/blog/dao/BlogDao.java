package com.blog.dao;

import com.blog.entity.Blog;
import com.blog.entity.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author shoji
 */
@Mapper
@Repository
public interface BlogDao {
    /**
     * バックでブログを表示する
     * @param id ブログid
     * @return 文章
     */
    Blog getBlog(Long id);

    /**
     *  ブログの詳細
     * @param id ブログid
     * @return 文章
     */
    Blog getDetailedBlog(@Param("id") Long id);

    /**
     * 全部のブログを取得
     * @return ブログリスト
     */
    List<Blog> getAllBlog();

    /**
     * 文章のアクセス数
     * @param id ブログid
     * @param values
     * @return
     */
    int updateViews(Long id,int values);

    /**
     * カテゴリidからブログを取得
     * @param typeId カテゴリid
     * @return ブログリスト
     */
    List<Blog> getByTypeId(Integer typeId);

    /**
     * タグidからブログを取得
     * @param tagId タグid
     * @return ブログリスト
     */
    List<Blog> getByTagId(Integer tagId);

    /**
     * ホームページのブログ展示
     * @return 文章リスト
     */
    List<Blog> getIndexBlog();

    /**
     * おすすめブログ展示
     * @return 文章リスト
     */
    List<Blog> getAllRecommendBlog();

    /**
     * ブログのグローバル検索
     * @param query キーワード
     * @return 文章リスト
     */
    List<Blog> getSearchBlog(String query);

    /**
     * タイトル、カテゴリよりおすすめブログを検索
     * @param blog 文章
     * @return 文章リスト
     */
    List<Blog> searchAllBlog(Blog blog);

    /**
     * 全部の発表年を検索し，コレクションを返す
     * @return 日付
     */
    List<String> findGroupYear();

    /**
     * 年でブログを検索
     * @param year 年
     * @return 文章リスト
     */
    List<Blog> findByYear(@Param("year") String year);

    /**
     * ブログ保存
     * @param blog 文章
     * @return ステータス値
     */
    int saveBlog(Blog blog);

    /**
     * タグとブログの保存
     * @param blogAndTag 文章和タグ
     * @return ステータス値
     */
    int saveBlogAndTag(BlogAndTag blogAndTag);

    /**
     * ブログ更新
     * @param blog 文章
     * @return ステータス値
     */
    int updateBlog(Blog blog);

    /**
     * ブログ削除
     * @param id ブログid
     * @return ステータス値
     */
    int deleteBlog(Long id);

    /**
     * トレンドブログのおすすめ
     * @return 文章リスト
     */
    List<Blog> getHotBlog();

    /**
     * 文章数を取得
     * @return 数
     */
    int getCount();

    /**
     * 閲覧数を取得
     * @return 閲覧数
     */
    int getViews();

    /**
     * 平均閲覧数を取得
     * @return 閲覧数
     */
    int getAvgViews();
}
