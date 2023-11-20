package com.blog.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author shoji
 */
@Component
@Data
@NoArgsConstructor
@PropertySource(value= "classpath:messages.properties",encoding = "UTF-8")
public class SettingsConfig {

    @Value("${web_Name}")
    private String web_Name;

    @Value("${web_IndexName}")
    private String web_IndexName;

    @Value("${web_Keywords}")
    private String web_Keywords;

    @Value("${web_Description}")
    private String web_Description;

    @Value("${web_Ico}")
    private String web_Ico;

    @Value("${web_Github}")
    private String web_Github;


    @Value("${web_instagram}")
    private String web_instagram;

    @Value("${web_facebook}")
    private String web_facebook;

    @Value("${web_Logo}")
    private String web_Logo;

    @Value("${web_Background}")
    private String web_Background;

    @Value("${web_Home}")
    private String web_Home;

    @Value("${web_KakuGen}")
    private String web_KakuGen;

    @Value("${default_avatar}")
    private String default_avatar;

    @Value("${message_Background}")
    private String message_Background;

    @Value("${about_Background}")
    private String about_Background;

    @Value("${friend_Background}")
    private String friend_Background;

    @Value("${search_Background}")
    private String search_Background;

    @Value("${tags_Background}")
    private String tags_Background;

    @Value("${time_Background}")
    private String time_Background;

    @Value("${types_Background}")
    private String types_Background;

    @Value("${valine_AppID}")
    private String valine_AppID;

    @Value("${valine_AppKey}")
    private String valine_AppKey;

    @Value("xxxx")
    private String Wx_Webhook;

}
