package com.blog.service.impl;

import com.blog.dao.MessageDao;
import com.blog.entity.Message;
import com.blog.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shoji
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageDao messageDao;

    /**
     * イテレーターで洗い出した全部のサブクラスメッセージを入れるためのコレクション
     */
    private List<Message> tempReplies = new ArrayList<>();

    /**
     * ホームページおすすめコメント
     * @return メッセージリスト
     */
    @Override
    public  List<Message> findByIndexParentId(){
        return messageDao.findByParentIdNull(Long.parseLong("-1"));
    }

    /**
     * メッセージの展示
     * @return メッセージリスト
     */
    @Override
    public List<Message> listMessage() {
        //親クラスメッセージを検索
        List<Message> messages = messageDao.findByParentIdNull(Long.parseLong("-1"));
        for(Message message : messages){
            Long id = message.getId();
            String parentNickname1 = message.getNickname();
            List<Message> childMessages = messageDao.findByParentIdNotNull(id);
            //子クラスメッセージを検索
            combineChildren(childMessages, parentNickname1);
            message.setReplyMessages(tempReplies);
            tempReplies = new ArrayList<>();
        }
        return messages;
    }


    /**
     * 子クラスメッセージを検索
     * @param childMessages 子メッセージ
     * @param parentNickname1 親メッセージ
     */
    private void combineChildren(List<Message> childMessages, String parentNickname1) {
        //一级子クラスメッセージの存在を判断
        if(childMessages.size() > 0){
            //子クラスメッセージのidを循環で探す
            for(Message childMessage : childMessages){
                String parentNickname = childMessage.getNickname();
                childMessage.setParentNickname(parentNickname1);
                tempReplies.add(childMessage);
                Long childId = childMessage.getId();
                //二级以上及び全部の子クラスのメッセージを検索
                recursively(childId, parentNickname);
            }
        }
    }

    /**
     * 子クラスメッセージを循環で探す
     * @param childId 子クラスid
     * @param parentNickname1 親メッセージ
     */
    private void recursively(Long childId, String parentNickname1) {
        //子クラス一级メッセージのidで子クラス二级メッセージを探す
        List<Message> replayMessages = messageDao.findByReplayId(childId);
        if(replayMessages.size() > 0){
            for(Message replayMessage : replayMessages){
                String parentNickname = replayMessage.getNickname();
                replayMessage.setParentNickname(parentNickname1);
                Long replayId = replayMessage.getId();
                tempReplies.add(replayMessage);
                recursively(replayId,parentNickname);
            }
        }
    }

    /**
     * メッセージ保存
     * @param message メッセージ
     * @return ステータス
     */
    @Override
    public int saveMessage(Message message) {
        message.setCreateTime(new Date());
        return messageDao.saveMessage(message);
    }

    /**
     *  メッセージ削除
     * @param id メッセージid
     */
    @Override
    public void deleteMessage(Long id) {
        messageDao.deleteMessage(id);
    }
}