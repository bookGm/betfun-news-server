package io.information.netty.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class ChannelUtil {
    /**
     * 用于记录c-s连接后建立的通道
     */
    static final Set<Channel> CHANNELS = new CopyOnWriteArraySet<>();
    //    private static final Set<Channel> CHANNELS = new ConcurrentSkipListSet<>();
    static final Map<String,Channel> CLIENT_CHANNELS=new ConcurrentHashMap();

    /**
     * ChannelUtil日志输出
     */
    private static final Logger LOG = LoggerFactory.getLogger(ChannelUtil.class);
    /**
     * 用于记录通道响应的结果集
     */
    private static final Map<String, Object> RESULT_MAP = new ConcurrentHashMap<>();

    private ChannelUtil() {
    }

    /**
     * 计算结果集（存储响应结果）
     *
     * @param key    唯一标识
     * @param result 结果集
     */
    public static void calculateResult(String key, Object result) {
        RESULT_MAP.put(key, result);
    }

    /**
     * 获取结果集的key
     *
     * @param key 保存的唯一标识
     * @return 结果集的 key (通道标识)
     */
    public static String getResultKey(String key) {
        return (String) getResult(key);
    }

    /**
     * 根据结果集的 key 获取结果集
     *
     * @param key 结果集的key
     * @return 结果集
     */
    public static Object getResult(String key) {
        return RESULT_MAP.get(key);
    }

    /**
     * 注册通道
     *
     * @param channel 通道
     */
    public static void registerChannel(Channel channel) {
        ChannelId id = channel.id();
        LOG.info("{} -> [添加通道] {}", ChannelUtil.class.getName(), id);
        boolean add = CHANNELS.add(channel);
        LOG.info("添加通道结果："+add);
    }

    public static void bind(String clientName,Channel channel)
    {
        CLIENT_CHANNELS.put(clientName,channel);
    }

    public static Channel getClientChannel(String key)
    {
        return ChannelUtil.CLIENT_CHANNELS.get(key);
    }

    public static int getClientChannelSize()
    {
        return CLIENT_CHANNELS.size();
    }

    public static void sendAll(String t){
        for(Map.Entry<String,Channel> m:CLIENT_CHANNELS.entrySet()){
            m.getValue().writeAndFlush(t);
        }
    }
}
