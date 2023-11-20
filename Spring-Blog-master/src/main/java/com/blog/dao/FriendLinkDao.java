package com.blog.dao;

import com.blog.entity.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author shoji
 */
@Mapper
@Repository
public interface FriendLinkDao {
    /**
     * Friendlinkのリスト
     * @return Friendlinkリスト
     */
    List<FriendLink> listFriendLink();

    /**
     * Friendlink保存
     * @param friendLink Friendlink
     * @return ステータス値
     */
    int saveFriendLink(FriendLink friendLink);

    /**
     * Friendlinkの数を取得
     * @return Friendlink数
     */
    int getCount();

    /**
     * Friendlinkを取得
     * @param id Friendlink id
     * @return Friendlink
     */
    FriendLink getFriendLink(Integer id);

    /**
     * アドレスを通じてFriendlinkを取得
     * @param blogAddress Friendlinkアドレス
     * @return Friendlink
     */
    FriendLink getFriendLinkByBlogAddress(String blogAddress);

    /**
     * 更新Friendlink
     * @param friendLink Friendlink
     * @return ステータス値
     */
    int updateFriendLink(FriendLink friendLink);

    /**
     * Friendlink削除
     * @param id Friendlink id
     */
    void deleteFriendLink(Integer id);

}