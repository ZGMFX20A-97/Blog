package com.blog.dao;

import com.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author shoji
 */
@Mapper
@Repository
public interface UserDao {
    /**
     * ユーザーのアカウントとパスワードを検索
     * @param username
     * @param password
     * @return user
     */
    User queryByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * idでユーザーデータを検索
     * @param id ユーザーid
     * @return user
     */
    User getUserInfoById(Integer id);

    /**
     * 全部のユーザーを取得
     * @return タグリスト
     */
    List<User> getAllUser();

    /**
     * ユーザー設定の変更
     * @param user ユーザー
     * @return boolean
     */
    int updateUser(User user);

    /**
     * ユーザー削除
     * @param id ユーザーid
     * @return ステータス値
     */
    int deleteUser(Integer id);

    /**
     * ユーザー保存
     * @param user ユーザー
     * @return ステータス値
     */
    int saveUser(User user);

    /**
     * ユーザー数を取得
     * @return ユーザー数
     */
    int getCount();

    /**
     * ユーザー名でユーザデータを検索
     * @param name ユーザー名
     * @return ユーザー名数
     */
    int getUserInfoByUsername(String name);
}
