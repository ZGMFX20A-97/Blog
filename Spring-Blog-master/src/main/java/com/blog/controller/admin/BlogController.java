package com.blog.controller.admin;

import com.blog.config.RedisKey;
import com.blog.entity.Blog;
import com.blog.entity.User;
import com.blog.service.BlogService;
import com.blog.service.RedisService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author shoji
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private RedisService redisService;

    @Resource
    private TagService tagService;

    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagService.getAllTag());
    }

    /**
     * バックでブログリストを表示
     * @param pageNum ページ数
     * @param model モデル
     * @return レンダリングしたモデル
     */
    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog = blogService.getAllBlog();
        //ページ分けした結果のオブジェクト
        PageInfo<? extends Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        return "admin/blogs";
    }

    /**
     * 条件でブログを検索
     * @param blog
     * @param pageNum ページ数
     * @param model モデル
     * @return レンダリングしたモデル
     */
    @PostMapping("/blogs/search")
    public String searchBlogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog = blogService.searchAllBlog(blog);
        //ページ分けした結果のオブジェクト
        PageInfo<? extends Blog> pageInfo = new PageInfo<>(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "検索が成功しました");
        setTypeAndTag(model);
        return "admin/blogs";
    }

    /**
     * ブログぺージの追加
     * @param model モデル
     * @return レンダリングしたモデル
     */
    @GetMapping("/blogs/input")
    public String toAddBlog(Model model){
        //ブログ型のオブジェクトをフロントへ送る：th:object
        model.addAttribute("blog", new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /**
     * ブログ編集ページ
     * @param id ブログid
     * @param model モデル
     * @return レンダリングしたモデル
     */
    @GetMapping("/blogs/{id}/input")
    public String toEditBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        //tagsのコレクションをtagIdsのStringへ転換
        blog.init();
        updateCache(blog);
        //blogオブジェクトをフロントへ送る：th:object
        model.addAttribute("blog", blog);
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /**
     * ブログの編集・追加
     * @param blog
     * @param session session
     * @param attributes 属性
     * @return
     */
    @PostMapping("/blogs")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTypeId(blog.getType().getId());
        blog.setTags(tagService.getTagByString(blog.getTagIds()));
        if (blog.getId() == null) {
            blogService.saveBlog(blog);
        } else {
            updateCache(blog);
            blogService.updateBlog(blog);
        }
        attributes.addFlashAttribute("msg", "追加に成功しました");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        deleteCache(id);
        attributes.addFlashAttribute("msg", "削除に成功しました");
        return "redirect:/admin/blogs";
    }

    public void updateCache(Blog blog){
        if (redisService.hHasKey(RedisKey.ARTCILE, String.valueOf(blog.getId()))){
            redisService.hSet(RedisKey.ARTCILE, String.valueOf(blog.getId()), blog);
        }
    }

    public void deleteCache(Long id){
        if (redisService.hHasKey(RedisKey.ARTCILE, String.valueOf(id))){
            redisService.hDel(RedisKey.ARTCILE, String.valueOf(id));
        }
        if (redisService.hHasKey(RedisKey.ARTCILEVIEWS, String.valueOf(id))){
            redisService.hDel(RedisKey.ARTCILEVIEWS, String.valueOf(id));
        }
        redisService.set(RedisKey.INDEXBLOG, blogService.getIndexBlog());
        redisService.set(RedisKey.RECOMMENDBLOG, blogService.getAllRecommendBlog());
    }
}
