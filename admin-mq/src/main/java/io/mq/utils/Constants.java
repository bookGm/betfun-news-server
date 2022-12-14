package io.mq.utils;

/**
 * mq 自定义配置
 */
public class Constants {
    public static final String defaultExchange="exchangeTest";
    public static final String queue="news_es_queue";
    public static final String routeKey="key_news_es";





    /**
     * 文章交换机
     */
    public static final String articleExchange="exchangeArticle";
    /**
     * 文章新增队列
     */
    public static final String article_Save_Queue="news_article_save";
    /**
     * 文章删除队列
     */
    public static final String article_Delete_Queue="news_article_delete";
    /**
     * 文章修改队列
     */
    public static final String article_Update_Queue="news_article_update";
    /**
     * 文章新增
     */
    public static final String article_Delete_RouteKey="save_news_article";
    /**
     * 文章删除
     */
    public static final String article_Save_RouteKey="delete_news_article";
    /**
     * 文章修改
     */
    public static final String article_Update_RouteKey="update_news_article";


    /**
     * 用户交换机
     */
    public static final String userExchange="exchangeUser";
    /**
     * 用户新增队列
     */
    public static final String user_Save_Queue="news_user_save";
    /**
     * 用户删除队列
     */
    public static final String user_Delete_Queue="news_user_delete";
    /**
     * 用户修改队列
     */
    public static final String user_Update_Queue="news_user_update";
    /**
     * 用户新增
     */
    public static final String user_Save_RouteKey="save_news_user";
    /**
     * 用户删除
     */
    public static final String user_Delete_RouteKey="delete_news_user";
    /**
     * 用户修改
     */
    public static final String user_Update_RouteKey="update_news_user";


    /**
     * 日志交换机
     */
    public static final String logExchange="exchangeLog";
    /**
     * 日志新增队列
     */
    public static final String log_Save_Queue="news_log_save";
    /**
     * 日志新增key
     */
    public static final String log_Save_RouteKey="save_news_log";


    /**
     * 快讯交换机
     */
    public static final String flashExchange="exchangeFlash";
    /**
     * 快讯新增队列
     */
    public static final String flash_Save_Queue="news_flash_save";
    /**
     * 快讯删除队列
     */
    public static final String flash_Delete_Queue="news_flash_delete";
    /**
     * 快讯修改队列
     */
    public static final String flash_Update_Queue="news_flash_update";
    /**
     * 快讯新增
     */
    public static final String flash_Save_RouteKey="save_news_flash";
    /**
     * 快讯删除
     */
    public static final String flash_Delete_RouteKey="delete_news_flash";
    /**
     * 快讯修改
     */
    public static final String flash_Update_RouteKey="update_news_flash";


    /**
     * 系统交换机
     */
    public static final String systemExchange="exchangeSystem";
    /**
     * 系统新增队列
     */
    public static final String system_Save_Queue="news_system_save";
    /**
     * 系统删除队列
     */
    public static final String system_Delete_Queue="news_system_delete";
    /**
     * 系统修改队列
     */
    public static final String system_Update_Queue="news_system_update";
    /**
     * 系统新增
     */
    public static final String system_Delete_RouteKey="save_news_system";
    /**
     * 系统删除
     */
    public static final String system_Save_RouteKey="delete_news_system";
    /**
     * 系统修改
     */
    public static final String system_Update_RouteKey="update_news_system";
}
