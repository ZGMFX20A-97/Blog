package com.blog.dao;

import com.blog.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author shoji
 */
@Mapper
@Repository
public interface MessageDao {

    /**
     * メッセージを追加
     * @param message メッセージ
     * @return ステータス値
     */
    int saveMessage(Message message);

    /**
     * メッセージ数を取得
     * @return メッセージ数
     */
    int getCount();

    /**
     * おすすめメッセージの検索
     * @param ParentId メッセージ親id
     * @return メッセージリスト
     */
    List<Message> findByIndexParentId(@Param("ParentId") Long ParentId);

    /**
     * メッセージの検索
     * @param ParentId メッセージ親id
     * @return メッセージリスト
     */
    List<Message> findByParentIdNull(@Param("ParentId") Long ParentId);

    /**
     * 一级返事の検索
     * @param id メッセージid
     * @return メッセージリスト
     */
    List<Message> findByParentIdNotNull(@Param("id") Long id);

    /**
     * 二级and全部のサブ返事を検索
     * @param childId メッセージid
     * @return メッセージリスト
     */
    List<Message> findByReplayId(@Param("childId") Long childId);

    /**
     * コメント削除
     * @param id メッセージid
     */
    void deleteMessage(Long id);

}
