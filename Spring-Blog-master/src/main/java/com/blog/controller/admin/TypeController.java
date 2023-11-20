package com.blog.controller.admin;

import com.blog.entity.Type;
import com.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author shoji
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Resource
    TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pageNum")int pageNum, Model model){
        PageHelper.startPage(pageNum, 5);
        List<Type> allType = typeService.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String toAddType(Model model){
        //type型のオブジェクトをフロントへ送る：th:object
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Integer id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        System.out.println(type);
        if(t != null){
            attributes.addFlashAttribute("msg", "重複したカテゴリを追加するのは不可能です");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "追加に成功しました");
        }
        typeService.saveType(type);
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editType(@PathVariable Integer id, Type type, RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            attributes.addFlashAttribute("msg", "重複したカテゴリを追加するのは不可能です");
            return "redirect:/admin/types/input";
        }else {
            attributes.addFlashAttribute("msg", "編集に成功しました");
        }
        typeService.updateType(type);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg", "削除に成功しました");
        return "redirect:/admin/types";
    }
}
