package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author shoji
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendLink implements Serializable {

    private Integer id;
    private String blogName;
    private String blogAddress;
    private String pictureAddress;
    private Date createTime;

}
