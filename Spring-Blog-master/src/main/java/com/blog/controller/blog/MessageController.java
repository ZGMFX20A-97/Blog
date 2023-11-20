package com.blog.controller.blog;

import com.blog.config.RedisKey;
import com.blog.config.SettingsConfig;
import com.blog.entity.Message;
import com.blog.entity.User;
import com.blog.service.MessageService;
import com.blog.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author shoji
 */
@Controller
public class MessageController {

    @Resource
    private RedisService redisService;

    @Resource
    private SettingsConfig settingsConfig;

    @Resource
    private MessageService messageService;

    /**
     * を検索メッセージ
     * @param model モデル
     * @return レンダリングしたモデル
     */
    @GetMapping({"/messageComment","/message"})
    public String messages(Model model) {
        List<Message> messages = messageService.listMessage();
        model.addAttribute("messages", messages);
        return "message";
    }

    /**
     * メッセージ追加
     * @param message
     * @param session ユーザー
     * @param model モデル
     * @return レンダリングしたモデル
     */
    @PostMapping("/message")
    public String post(Message message, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        //アイコンの設定
        if (user != null) {
            message.setAdminMessage(true);
            message.setAvatar(user.getAvatar());
        } else {
            message.setAvatar(settingsConfig.getDefault_avatar());
        }
        if (message.getParentMessage().getId() != null) {
            message.setParentMessageId(message.getParentMessage().getId());
        }
        messageService.saveMessage(message);
        List<Message> messages = messageService.listMessage();
        model.addAttribute("messages", messages);
        return "message";
    }

    /**
     * メッセージの削除
     * @param id メッセージid
     * @return レンダリングしたモデル
     */
    @GetMapping("/messages/{id}/delete")
    public String delete(@PathVariable Long id){
        messageService.deleteMessage(id);
        deleteCache();
        return "message";
    }

    public void deleteCache(){
        redisService.set(RedisKey.MESSAGES, messageService.findByIndexParentId());
    }
}