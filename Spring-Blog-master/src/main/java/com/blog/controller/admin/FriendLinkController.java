package com.blog.controller.admin;

import com.blog.entity.FriendLink;
import com.blog.service.FriendLinkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author shoji
 */
@Controller
@RequestMapping("/admin")
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;

    /**
     * すべてのFriendlinkを検索
     * @param model モデル
     * @param pageNum ページ数
     * @return レンダリングしたモデル
     */
    @GetMapping({"/friendLinks","/friendlinks"})
    public String friend(Model model, @RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,10);
        List<FriendLink> listFriendLink = friendLinkService.listFriendLink();
        PageInfo<FriendLink> pageInfo = new PageInfo<>(listFriendLink);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/friendLinks";
    }

    /**
     * Friendlinkを追加する画面へ遷移
     * @param model モデル
     * @return Friendlink追加ページ
     */
    @GetMapping("/friendLinks/input")
    public String input(Model model) {
        model.addAttribute("friendLink", new FriendLink());
        return "admin/friendLinks-input";
    }

    /**
     * Friendlink追加
     * @param friendLink
     * @param result 結果
     * @param attributes 属性
     * @return Friendlinkページへリダイレクト
     */
    @PostMapping("/friendLinks")
    public String post(@Valid FriendLink friendLink, BindingResult result, RedirectAttributes attributes){
        FriendLink obj = friendLinkService.getFriendLinkByBlogAddress(friendLink.getBlogAddress());
        if (obj != null) {
            attributes.addFlashAttribute("msg", "同じURLを追加することができません");
            return "redirect:/admin/friendLinks/input";
        }

        if(result.hasErrors()){
            return "admin/friendLinks-input";
        }
        friendLink.setCreateTime(new Date());
        int num = friendLinkService.saveFriendLink(friendLink);
        if (num == 0 ) {
            attributes.addFlashAttribute("msg", "追加に失敗しました");
        } else {
            attributes.addFlashAttribute("msg", "追加に成功しました");
        }
        return "redirect:/admin/friendLinks";
    }

    /**
     * Friendlinkの編集画面へ遷移
     * @param id Friendlink id
     * @param model モデル
     * @return Friendlink修正ページ
     */
    @GetMapping("/friendLinks/{id}/input")
    public String editInput(@PathVariable Integer id, Model model) {
        model.addAttribute("friendLink", friendLinkService.getFriendLink(id));
        return "admin/friendLinks-input";
    }

    /**
     * Friendlink変更
     * @param friendLink friendLink
     * @param attributes 属性
     * @return friendLinkページ
     */
    @PostMapping("/friendLinks/{id}")
    public String editPost(@Valid FriendLink friendLink, RedirectAttributes attributes) {
        int t = friendLinkService.updateFriendLink(friendLink);
        if (t == 0 ) {
            attributes.addFlashAttribute("msg", "編集に失敗しました");
        } else {
            attributes.addFlashAttribute("msg", "編集に成功しました");
        }
        return "redirect:/admin/friendLinks";
    }

    /**
     * 削除Friendlink
     * @param id Friendlink id
     * @param attributes 属性
     * @return Friendlinkページ
     */
    @GetMapping("/friendLinks/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        friendLinkService.deleteFriendLink(id);
        attributes.addFlashAttribute("msg", "削除に成功しました");
        return "redirect:/admin/friendLinks";
    }

}