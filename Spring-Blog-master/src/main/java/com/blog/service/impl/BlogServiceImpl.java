package com.blog.service.impl;

import com.blog.dao.BlogDao;
import com.blog.config.RedisKey;
import com.blog.exception.NotFoundException;
import com.blog.entity.Blog;
import com.blog.entity.BlogAndTag;
import com.blog.entity.Tag;
import com.blog.service.BlogService;
import com.blog.service.RedisService;
import com.blog.util.MarkdownUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author shoji
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    RedisService cache;

    @Resource
    BlogDao blogDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.getBlog(id);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = Optional.ofNullable(blogDao.getDetailedBlog(id))
                            .orElseThrow(() -> new NotFoundException("当該ブログは存在しません"));
        String content = blog.getContent();
        //Markdownをhtmlへ転換
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return blog;
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogDao.getAllBlog();
    }

    @Override
    public List<Blog> getByTypeId(Integer typeId) {
        return blogDao.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Integer tagId) {
        return blogDao.getByTagId(tagId);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogDao.getIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogDao.getAllRecommendBlog();
    }

    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.findGroupYear();
        //setで重なった年を消す
        Set<String> set = new HashSet<>(years);
        Map<String, List<Blog>> map = new HashMap<>(8);
        set.forEach(year -> map.put(year, blogDao.findByYear(year)));
        return map;
    }

    @Override
    public int countBlog() {
        return getAllBlog().size();
    }

    @Override
    public List<Blog> searchAllBlog(Blog blog) {
        return blogDao.searchAllBlog(blog);
    }

    @Override
    public List<Blog> getHotBlog() {
        return blogDao.getHotBlog();
    }

    /**
     * ステータス値
     * @param blog 文章
     * @return 文章保存
     */
    @Override
    public int saveBlog(Blog blog) {
        final Date now = new Date();
        blog.setCreateTime(now );
        blog.setUpdateTime(now);
        blog.setViews(0);
        //ブログ保存
        blogDao.saveBlog(blog);
        //ブログ保存後だけインクリメントしたidを取得できる
        Long id = blog.getId();
        //タグのデータをt_blogs_tagテーブルへ保存
        blog.getTags().forEach(tag -> {
            //新しく追加するときはインクリメントのidを取得できず,mybatisの中で修正する
            BlogAndTag blogAndTag = new BlogAndTag(tag.getId(), id);
            blogDao.saveBlogAndTag(blogAndTag);
        });
        return 1;
    }

    /**
     * ブログ編集
     * @param blog 文章
     * @return ステータス値
     */
    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //タグデータをt_blogs_tagテーブルへ保存
        blog.getTags().forEach(tag -> {
            BlogAndTag blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        });
        if (cache.hHasKey(RedisKey.ARTCILE, String.valueOf(blog.getId()))){
            cache.hSet(RedisKey.ARTCILE, String.valueOf(blog.getId()), blog);
        }
        return blogDao.updateBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        //もしキャッシュの中にこのキーが存在していれば
        if (cache.hHasKey(RedisKey.ARTCILEVIEWS, String.valueOf(id))){
            cache.hDel(RedisKey.ARTCILEVIEWS, String.valueOf(id));
        }
        if (cache.hHasKey(RedisKey.ARTCILE, String.valueOf(id))){
            cache.hDel(RedisKey.ARTCILE, String.valueOf(id));
        }
        return blogDao.deleteBlog(id);
    }

}
