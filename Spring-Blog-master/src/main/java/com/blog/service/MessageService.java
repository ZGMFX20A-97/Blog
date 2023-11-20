package com.blog.service;

import com.blog.entity.Message;

import java.util.List;

/**
 * @author shoji
 */
public interface MessageService {

    /**
     * メッセージlistを検索
     * @return メッセージlist
     */
    List<Message> listMessage();

    /**
     * トップページにおすすめメッセージを展示
     * @return メッセージlist
     */
    List<Message> findByIndexParentId();

    /**
     * メッセージを保存する
     * @param message
     * @return ステータス（数字）
     */
    int saveMessage(Message message);

    /**
     * メッセージを削除
     * @param id メッセージid
     */
    void deleteMessage(Long id);

}
