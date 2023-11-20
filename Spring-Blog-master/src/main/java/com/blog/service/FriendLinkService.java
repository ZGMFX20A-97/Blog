package com.blog.service;

import com.blog.entity.FriendLink;

import java.util.List;

/**
 * @author shoji
 */
public interface FriendLinkService {

    /**
     * すべてのFriendlinkを調べる
     * @return Friendlinklist
     */
    List<FriendLink> listFriendLink();

    /**
     * Friendlinkを追加
     * @param friendLink
     * @return ステータス（数字で表す）
     */
    int saveFriendLink(FriendLink friendLink);

    /**
     * IdでFriendLinkを検索
     * @param id FriendLinkid
     * @return FriendLink
     */
    FriendLink getFriendLink(Integer id);

    /**
     * アドレスでFriendLinkを調べる
     * @param blogAddress FriendLink
     * @return FriendLink
     */
    FriendLink getFriendLinkByBlogAddress(String blogAddress);

    /**
     * FriendLinkを変更する
     * @param friendLink
     * @return ステータス（数字で表す）
     */
    int updateFriendLink(FriendLink friendLink);

    /**
     * FriendLinkを削除する
     * @param id FriendLinkid
     */
    void deleteFriendLink(Integer id);

}