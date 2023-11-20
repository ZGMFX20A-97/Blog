package com.blog.aspect;

import com.blog.pojo.RequestLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * アスペクト
 * @author shoji
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ポイントカット（コントローラーにアスペクトを導入する）
     */
    @Pointcut("execution(* com.blog.controller.*.*.*(..))")
    public void log(){}

    /**
     * Before
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //リクエストがnullの場合後続の処理を中止する
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //シグネチャーを通してクラス名・メソッド名を取得
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        //引数の取得
        Object[] args = joinPoint.getArgs();
        //リクエストの詳細に関するログを構築
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);
        //リクエストの内容を出力
        logger.info("Request: {}", requestLog);
    }

    /**
     * After
     */
    @After("log()")
    public void doAfter(){
        logger.info("------------doAfter------------");
    }

    /**
     *標的メソッド実行完了後に入れる
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result){
        //戻り値を出力
        logger.info("Result: {}", result);
    }
}
