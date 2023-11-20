package com.blog.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ログ，リクエストの詳細をラップするために使う
 * @author shoji
 */
@Data
@AllArgsConstructor
public class RequestLog{
    private String url;
    private String ip;
    private String classMethod;
    private Object[] args;
}