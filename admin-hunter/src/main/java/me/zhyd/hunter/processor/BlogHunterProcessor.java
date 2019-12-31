package me.zhyd.hunter.processor;

import me.zhyd.hunter.Hunter;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.scheduler.BlockingQueueScheduler;
import me.zhyd.hunter.util.HunterPrintWriter;
import me.zhyd.hunter.downloader.HttpClientDownloader;
import org.apache.commons.collections.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 爬虫入口
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
public class BlogHunterProcessor extends HunterProcessor {

    public BlogHunterProcessor(String url, boolean convertImage) {
        super(url, convertImage);
    }

    public BlogHunterProcessor(String url, boolean convertImage, HunterPrintWriter writer) {
        super(url, convertImage, writer);
    }

    public BlogHunterProcessor(HunterConfig config) {
        super(config);
    }

    public BlogHunterProcessor(HunterConfig config, String uuid) {
        super(config, uuid);
    }

    /**
     * @param config Hunter Config
     * @param writer
     * @param uuid
     */
    public BlogHunterProcessor(HunterConfig config, HunterPrintWriter writer, String uuid) {
        super(config, writer, uuid);
    }

    private static volatile JedisPool jedisPool = null;

    /**
     * 获取jedis连接池唯一实例
     * @return
     */
    public JedisPool getJedisPoolInstance(){
        if (null == jedisPool) {
            synchronized (BlogHunterProcessor.class) { //这里使用双端检测设计模式
                if (null == jedisPool) {
                    JedisPoolConfig config = new JedisPoolConfig();
                    //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
                    config.setBlockWhenExhausted(true);

                    //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
                    config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

                    //是否启用pool的jmx管理功能, 默认true
                    config.setJmxEnabled(true);

                    //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool", JMX不熟,具体不知道是干啥的...默认就好.
                    config.setJmxNamePrefix("pool");

                    //是否启用后进先出, 默认true
                    config.setLifo(true);

                    //最大空闲连接数, 默认8个
                    config.setMaxIdle(8);

                    //最大连接数, 默认8个
                    config.setMaxTotal(8);

                    //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
                    config.setMaxWaitMillis(-1);

                    //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
                    config.setMinEvictableIdleTimeMillis(1800000);

                    //最小空闲连接数, 默认0
                    config.setMinIdle(0);

                    //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
                    config.setNumTestsPerEvictionRun(3);

                    //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
                    config.setSoftMinEvictableIdleTimeMillis(1800000);

                    //在获取连接的时候检查有效性, 默认false
                    config.setTestOnBorrow(false);

                    //在空闲时检查有效性, 默认false
                    config.setTestWhileIdle(false);

                    //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
                    config.setTimeBetweenEvictionRunsMillis(-1);

                    jedisPool = new JedisPool(config, "127.0.0.1", 6379, 10000);
                }
            }
        }
        return jedisPool;
    }
    /**
     * 释放
     * @param jedisPool 释放哪个池中
     * @param jedis        的哪个对象
     */
    public static void release(JedisPool jedisPool, Jedis jedis){
        if(null != jedis){
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 运行爬虫并返回结果
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<VirtualArticle> execute() {
        List<String> errors = this.validateModel(config);
        if (CollectionUtils.isNotEmpty(errors)) {
            writer.print("校验不通过！请依据下方提示，检查输入参数是否正确......");
            for (String error : errors) {
                writer.print(">> " + error);
            }
            return null;
        }

        CopyOnWriteArrayList<VirtualArticle> virtualArticles = new CopyOnWriteArrayList<>();
        Hunter spider = Hunter.create(this, config, uuid);
        JedisPool jedis=getJedisPoolInstance();
        spider.addUrl(config.getEntryUrls().toArray(new String[0]))
                .setScheduler(new BlockingQueueScheduler(config))
                .addPipeline((resultItems, task) -> this.process(resultItems, virtualArticles, spider))
                .setDownloader(new HttpClientDownloader())
                .thread(config.getThreadCount());

        //设置抓取代理IP
        if (!CollectionUtils.isEmpty(config.getProxyList())) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            SimpleProxyProvider provider = SimpleProxyProvider.from(config.getProxyList().toArray(new Proxy[0]));
            httpClientDownloader.setProxyProvider(provider);
            spider.setDownloader(httpClientDownloader);
        }
        // 测试代理
        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        SimpleProxyProvider provider = SimpleProxyProvider.from(
                new Proxy("61.135.217.7", 80)
        );
        httpClientDownloader.setProxyProvider(provider);
        spider.setDownloader(httpClientDownloader);*/

        // 启动爬虫
        spider.run();
        return virtualArticles;
    }


}
