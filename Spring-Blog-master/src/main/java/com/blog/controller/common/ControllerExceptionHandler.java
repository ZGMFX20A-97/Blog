package com.blog.controller.common;

import com.blog.config.SettingsConfig;
import com.blog.pojo.WebhookMessage;
import com.blog.util.WxChatbotClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 非ステータスコードによるexceptionは自分で作ったerror画面に行く
 * ステータスコードによるerrorは自動的にそれぞれの処理画面へ行く
 * controllerがスローした全部のexceptionをキャッチし，統一に処理する
 * @author shoji
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @Resource
    private SettingsConfig settings;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * このメソッドは全般的なexceptionが処理できる
     * @param request リクエスト
     * @param e エクセプション
     * @return error画面
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {
        //ログがexception内容を出力
        logger.error("Request url: {}, Request ip: {}, Exception: {} ", request.getRequestURI(), request.getRemoteAddr(), e.getMessage());
        //ResponseStatusアノテーションがついているexceptionは処理しない
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        if (!"0".equals(settings.getWx_Webhook())){
            WebhookMessage message = new WebhookMessage();
            message.setText("ブログの異常リクエストのお知らせ\nリクエストurl:" + request.getRequestURI() + "\nリクエストip:" + request.getRemoteAddr() + "\nerrorの理由:" + e.getMessage());
            WxChatbotClient.send(settings.getWx_Webhook(), message);
        }
        //exceptionのinfoをerror画面に送る
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }

}
