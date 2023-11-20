package com.blog.service;

import com.blog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shoji
 */
public interface UserService {

    /**
     * ログイン
     * @param username 账号
     * @param password 密码
     * @return user
     */
    User checkUser(@Param("username") String username, @Param("password") String password);

    /**
     * ユーザーIDで取得ユーザーデータを取得
     * @param id   主キー
     * @return user
     */
    User getUserInfoById(Integer id);

    /**
     * ユーザーデータの更新
     * @param user  user对象
     * @return ステータス値
     */
    int updateUser(User user);

    /**
     * ユーザーデータの保存
     * @param user  userオブジェクト
     * @return ステータス値
     */
    int saveUser(User user);

    /**
     * 全部のユーザーを取得
     * @return ユーザー
     */
    List<User> getUsers();

    /**
     * ユーザー削除
     * @param id ユーザーid
     * @return ステータス値
     */
    int deleteUser(Integer id);

    /**
     * ユーザー数を検索
     * @param name
     * @return ユーザー数
     */
    int getUserInfoByUsername(String name);
}
