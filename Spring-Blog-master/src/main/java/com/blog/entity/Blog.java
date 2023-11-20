package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author shoji
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private Integer views;
    private Boolean flag;
    private Boolean appreciation;
    private Boolean shareStatement;
    private Boolean commentable;
    private Boolean published;
    private Boolean recommend;
    private Date createTime;
    private Date updateTime;

    /**
     * mybatisの中でリレーショナルクエリするためのフィールド
     */
    private Integer typeId;
    private Integer userId;

    /**
     * 多数のIDを取得
     */
    private String tagIds;

    private String description;

    private Type type;

    private User user;

    private List<Tag> tags = new ArrayList<>();

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    /**
     * tagsコレクションをtagIdsStringの形に転換：“1,2,3”,ブログを編集するときにブログのtagを表示
     * @param tags
     * @return tagid
     */
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for(Tag tag: tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }
    public String tagsToNames(){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();
            boolean flag = false;
            for(Tag tag: tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getName());
            }
            return ids.toString();
        }else {
            return "";
        }
    }
}
