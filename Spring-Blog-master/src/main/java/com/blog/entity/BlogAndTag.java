package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ブログとタグの関連をデータベースへ保存する
 * @author shoji
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogAndTag implements Serializable {

    private Integer tagId;

    private Long blogId;
}
