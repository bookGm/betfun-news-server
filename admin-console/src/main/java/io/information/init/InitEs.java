//package io.information.init;
//
//import io.elasticsearch.dao.EsArticleDao;
//import io.elasticsearch.dao.EsFlashDao;
//import io.elasticsearch.dao.EsUserDao;
//import io.elasticsearch.entity.EsArticleEntity;
//import io.elasticsearch.entity.EsFlashEntity;
//import io.elasticsearch.entity.EsUserEntity;
//import io.elasticsearch.entity.LocationEntity;
//import io.information.common.utils.BeanHelper;
//import io.information.common.utils.IdGenerator;
//import io.information.modules.app.entity.InArticle;
//import io.information.modules.app.entity.InNewsFlash;
//import io.information.modules.app.entity.InUser;
//import io.information.modules.app.service.IInArticleService;
//import io.information.modules.app.service.IInNewsFlashService;
//import io.information.modules.app.service.IInUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class InitEs {
//    @Autowired
//    EsArticleDao esArticleDao;
//    @Autowired
//    EsFlashDao esFlashDao;
//    @Autowired
//    EsUserDao esUserDao;
//    @Autowired
//    IInArticleService articleService;
//    @Autowired
//    IInNewsFlashService flashService;
//    @Autowired
//    IInUserService userService;
//
//    @PostConstruct
//    public void init() {
//        //只初始化一次
//        Iterable<EsArticleEntity> esArticles = esArticleDao.findAll();
//        if (esArticles.iterator().hasNext()) {
//            return;
//        }
//        Iterable<EsFlashEntity> esFlashs = esFlashDao.findAll();
//        if (esFlashs.iterator().hasNext()) {
//            return;
//        }
//        Iterable<EsUserEntity> esUsers = esUserDao.findAll();
//        if (esUsers.iterator().hasNext()) {
//            return;
//        }
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String simple = format.format(new Date());
//        Long uId = 5L;
//        for (int i = 0; i < 2; i++) {
//            //用户
//            EsUserEntity esUser = new EsUserEntity();
//            esUser.setuId(uId);
//            esUser.setuAccount("15231848846");
//            esUser.setuName("云迹");
//            esUser.setuPwd("b8a9ab9eb88aedc956a4791cb1be212615c177226eb25a5b3a1496fc17eb869f");
//            esUser.setuSalt("8fd413b44ceabf99480cde819336d8d8cbbeea08f62a311db029ffd1da96ed18");
//            esUser.setuNick("云迹");
//            esUser.setuPhoto("http://guansuo.info/news/upload/20191231115456head.png");
//            esUser.setuPhone("15231848846");
//            esUser.setuIntro("best day");
//            esUser.setuFans(41L);
//            esUser.setLikeNUmber(0L);
//            esUser.setuAuthStatus(2);
//            esUser.setuFocus(33);
//            esUser.setuAuthType(0);
//            esUser.setuCreateTime(new Date());
//            esUser.setuPotential(0);
//            esUser.setReadNumber(10L * i);
//            esUser.setArticleNumber(1);
//            esUserDao.save(esUser);
////            InUser user = BeanHelper.copyProperties(esUser, InUser.class);
////            userService.save(user);
//            //文章
//            EsArticleEntity post = new EsArticleEntity();
//            post.setaId(IdGenerator.getId());
//            post.setuId(uId);
//            post.setuName("云迹");
//            post.setaTitle("BTC多次下探关键支撑区域，短线多头反抗力量较弱");
//            post.setaContent("<p><strong>作者 | 哈希派分析团队</strong></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115703REec.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115704LbS7.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115705QcoK.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115709nIqd.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115706fL8b.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115706Zplf.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115707rUBf.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115711ljn0.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/2019111511570823Cj.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115709RtJg.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115713FkW7.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115709H9ca.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115710RqBo.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115710xuu6.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/2019111511571433tK.jpeg\" title=\"\" alt=\"\"></p>\n" +
//                    "<p><img src=\"https://appserversrc.8btc.com/images/20191115115715eK0h.jpeg\" title=\"\" alt=\"\"></p>");
//            post.setaBrief("作者 | 哈希派分析团队");
//            post.setaKeyword("比特币,以太坊,行情分析");
//            post.setaCover("https://appserversrc.8btc.com/viking/20191115115657_sNby.jpeg");
//            post.setaType(1);
//            post.setaStatus(2);
//            post.setaSource("巴比特");
//            post.setaLink("https://www.8btc.com/article/514617");
//            post.setaCreateTime(new Date());
//            post.setaLike(0L);
//            post.setaCollect(i);
//            post.setaCritic(0L);
//            post.setaReadNumber((long) (i * 10));
//            post.setaSimpleTime(simple);
//            InArticle inArticle = BeanHelper.copyProperties(post, InArticle.class);
//            esArticleDao.save(post);
//            articleService.save(inArticle);
//            //快讯
//            EsFlashEntity esFlash = new EsFlashEntity();
//            esFlash.setnCreateTime(new Date());
//            esFlash.setnBad(5);
//            esFlash.setnBull(6);
//            esFlash.setnId(IdGenerator.getId());
//            esFlash.setnTitle("动态 | 金山云一次性通过2019可信区块链测试 排名第一");
//            esFlash.setnBrief("今天，中国排名第一 (金融界）");
//            esFlash.setnContent("今天，在中国信息通信研究院、中国通信标准化协会联合主办的 2019 可信区块链峰会上，金山云一次性通过2019年可信区块链标准评测，排名第一，KBaaS和KLedger作为金山云区块链的两大核心产品，一次性通过全部评测。本次峰会主要分享区块链的最新动态，展示应用热点及标杆案例，探讨区块链应用面临的挑战何以及赋能实体经济的最佳路径等议题。（金融界）");
//            InNewsFlash flash = BeanHelper.copyProperties(esFlash, InNewsFlash.class);
//            esFlashDao.save(esFlash);
//            flashService.save(flash);
//            //其他
//            LocationEntity entity = new LocationEntity();
//            entity.setId(IdGenerator.getUUID());
//            entity.setFirstName(i+"其他");
//            entity.setLastName("其他"+i);
//            entity.setAge(0);
//            entity.setAbout("其他");
//        }
//    }
//
//    private List<String> getTitle() {
//        List<String> list = new ArrayList<>();
//        list.add("《如梦令·常记溪亭日暮》");
//        list.add("《醉花阴·薄雾浓云愁永昼》");
//        list.add("《声声慢·寻寻觅觅》");
//        list.add("《永遇乐·落日熔金》");
//        list.add("《如梦令·昨夜雨疏风骤》");
//        list.add("《渔家傲·雪里已知春信至》");
//        list.add("《点绛唇·蹴[1]罢秋千》");
//        list.add("《点绛唇·寂寞深闺》");
//        list.add("《蝶恋花·泪湿罗衣脂粉满》");
//        list.add("《蝶恋花 离情》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《减字木兰花·卖花担上》");
//        list.add("《临江仙·欧阳公作《蝶恋花》");
//        list.add("《临江仙·庭院深深深几许》");
//        list.add("《念奴娇·萧条庭院》");
//        list.add("《菩萨蛮·风柔日薄春犹早》");
//        list.add("《菩萨蛮·归鸿声断残云碧》");
//        list.add("《武陵春·风住尘香花已尽》");
//        list.add("《一剪梅·红藕香残玉蕈秋》");
//        list.add("《渔家傲·天接云涛连晓雾》");
//        list.add("《鹧鸪天·暗淡轻黄体性柔》");
//        list.add("《鹧鸪天·寒日萧萧上锁窗》");
//        list.add("《一剪梅·红藕香残玉簟秋》");
//        list.add("《如梦令·常记溪亭日暮》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《蝶恋花·泪湿罗衣脂粉满》");
//        list.add("《蝶恋花·暖日晴风初破冻》");
//        list.add("《鹧鸪天·寒日萧萧上锁窗》");
//        list.add("《醉花阴·薄雾浓云愁永昼》");
//        list.add("《鹧鸪天·暗淡轻黄体性柔》");
//        list.add("《蝶恋花·永夜恹恹欢意少》");
//        list.add("《浣溪沙》");
//        list.add("《浣溪沙》");
//        list.add("《如梦令·谁伴明窗独坐》");
//        return list;
//    }
//
//    private List<String> getContent() {
//        List<String> list = new ArrayList<>();
//        list.add("初中 宋·李清照 常记溪亭日暮，沉醉不知归路，兴尽晚回舟，误入藕花深处。争渡，争渡");
//        list.add("重阳节 宋·李清照 薄雾浓云愁永昼，瑞脑消金兽。佳节又重阳，玉枕纱厨，半夜凉初透。东");
//        list.add("闺怨诗 宋·李清照 寻寻觅觅，冷冷清清，凄凄惨惨戚戚。乍暖还寒时候，最难将息。三杯两");
//        list.add("元宵节 宋·李清照 落日熔金，暮云合璧，人在何处。染柳烟浓，吹梅笛怨，春意知几许。元");
//        list.add("婉约诗 宋·李清照 昨夜雨疏风骤，浓睡不消残酒，试问卷帘人，却道海棠依旧。知否，知否");
//        list.add("描写梅花 宋·李清照 雪里已知春信至，寒梅点缀琼枝腻，香脸半开娇旖旎，当庭际，玉人浴出");
//        list.add(" 宋·李清照 蹴罢秋千，起来慵整纤纤手。露浓花瘦，薄汗轻衣透。见客入来，袜刬金");
//        list.add("闺怨诗 宋·李清照 寂寞深闺，柔肠一寸愁千缕。惜春春去。几点催花雨。倚遍阑干，只是无");
//        list.add("婉约诗 宋·李清照 泪湿罗衣脂粉满。四叠阳关，唱到千千遍。人道山长水又断。萧萧微雨闻");
//        list.add("描写春天 宋·李清照 暖雨晴风初破冻，柳眼梅腮，已觉春心动。酒意诗情谁与共？泪融残粉花");
//        list.add("寒食节 宋·李清照 淡荡春光寒食天，玉炉沈水袅残烟，梦回山枕隐花钿。海燕未来人斗草，");
//        list.add(" 宋·李清照 髻子伤春慵更梳，晚风庭院落梅初，淡云来往月疏疏，玉鸭薰炉闲瑞脑，");
//        list.add(" 宋·李清照 莫许杯深琥珀浓，未成沉醉意先融。疏钟已应晚来风。瑞脑香消魂梦断，");
//        list.add("闺怨诗 宋·李清照 小院闲窗春已深，重帘未卷影沉沉。倚楼无语理瑶琴，远岫出山催薄暮。");
//        list.add("爱情诗 宋·李清照 绣幕芙蓉一笑开，斜偎宝鸭亲香腮，眼波才动被人猜。一面风情深有韵，");
//        list.add("描写春天 宋·李清照 卖花担上，买得一枝春欲放。泪染轻匀，犹带彤霞晓露痕。怕郎猜道，奴");
//        list.add("》 宋·李清照 欧阳公作《蝶恋花》，有“深深深几许”之句，予酷爱之。用其语作“庭");
//        list.add("描写梅花 宋·李清照 庭院深深深几许，云窗雾阁春迟，为谁憔悴损芳姿。夜来清梦好，应是发");
//        list.add("寒食节 宋·李清照 萧条庭院，又斜风细雨，重门须闭。宠柳娇花寒食近，种种恼人天气。险");
//        list.add("思乡诗 宋·李清照 风柔日薄春犹早，夹衫乍著心情好。睡起觉微寒，梅花鬓上残。故乡何处");
//        list.add("描写春天 宋·李清照 归鸿声断残云碧，背窗雪落炉烟直。烛底凤钗明，钗头人胜轻。角声催晓");
//        list.add("闺怨诗 宋·李清照 风住尘香花已尽，日晚倦梳头。物是人非事事休，欲语泪先流。闻说双溪");
//        list.add(" 宋·李清照 红藕香残玉蕈秋，轻解罗裳，独上兰舟。云中谁寄锦书来？雁字回时，月");
//        list.add("豪放诗 宋·李清照 天接云涛连晓雾。星河欲转千帆舞。仿佛梦魂归帝所。闻天语。殷勤问我");
//        list.add("描写花 宋·李清照 暗淡轻黄体性柔。情疏迹远只香留。何须浅碧深红色，自是花中第一流。");
//        list.add("描写秋天 宋·李清照 寒日萧萧上琐窗，梧桐应恨夜来霜。酒阑更喜团茶苦，梦断偏宜瑞脑香。");
//        list.add("闺怨诗 宋·李清照 红藕香残玉簟秋。轻解罗裳，独上兰舟。云中谁寄锦书来？雁字回时，月");
//        list.add(" 宋·李清照 常记溪亭日暮。沈醉不知归路。兴尽晚回舟，误入藕花深处。争渡。争渡");
//        list.add(" 宋·李清照 莫许杯深琥珀浓。未成沈醉意先融。已应晚来风。瑞脑香消魂梦断，");
//        list.add(" 宋·李清照 小院闲窗春色深。重帘未卷影沈沈。倚楼无语理瑶琴。远岫出山催薄暮，");
//        list.add(" 宋·李清照 淡荡春光寒食天。玉炉沈水袅残烟。梦回山枕隐花钿。海燕未来人斗草，");
//        list.add(" 宋·李清照 泪湿罗衣脂粉满。四叠阳关，唱到千千遍。人道山长山又断。萧萧微雨闻");
//        list.add(" 宋·李清照 暖日晴风初破冻。柳眼眉腮，已觉春心动。酒意诗情谁与共。泪融残粉花");
//        list.add(" 宋·李清照 寒日萧萧上锁窗。梧桐应恨夜来霜。酒阑更喜团茶苦，梦断偏宜瑞脑香。");
//        list.add(" 宋·李清照 薄雾浓云愁永昼。瑞脑消金兽。佳节又重阳，玉枕纱厨，半夜凉初透。东");
//        list.add(" 宋·李清照 暗淡轻黄体性柔。情疏迹远只香留。何须浅碧深红色，自是花中第一流。");
//        list.add(" 宋·李清照 永夜恹恹欢意少。空梦长安，认取长安道。为报今年春色好。花光月影宜");
//        list.add(" 宋·李清照 髻子伤春慵更梳。晚风庭院落梅初。淡云来往月疏疏。玉鸭熏炉闲瑞脑，");
//        list.add(" 宋·李清照 绣面芙蓉一笑开。斜飞宝鸭衬香腮。眼波才动被人猜。一面风情深有韵，");
//        list.add(" 宋·李清照 谁伴明窗独坐，我共影儿俩个。灯尽欲眠时，影也把人抛躲。无那，无那");
//        return list;
//    }
//
//
//    private List<String> getBrief() {
//        List<String> list = new ArrayList<>();
//        list.add("初中 宋·李清照 常记溪亭日暮。争渡，争渡");
//        list.add("重阳节 宋·李清照 薄雾浓云愁永昼");
//        list.add("闺怨诗 宋·李清照 寻寻觅觅，冷冷清清");
//        list.add("元宵节 宋·李清照 落日熔金。元");
//        list.add("婉约诗 宋·李清照 昨夜雨疏风骤。知否，知否");
//        list.add("描写梅花 宋·李清照 雪里已知春信至");
//        list.add(" 宋·李清照 蹴罢秋千。见客入来，袜刬金");
//        list.add("闺怨诗 宋·李清照 寂寞深闺");
//        list.add("婉约诗 宋·李清照 泪湿罗衣脂粉满。");
//        list.add("描写春天 宋·李清照 酒意诗情谁与共？泪融残粉花");
//        list.add("寒食节 宋·李清照 淡荡春光寒食天");
//        list.add(" 宋·李清照 晚风庭院落梅初");
//        list.add(" 宋·李清照 瑞脑香消魂梦断");
//        list.add("闺怨诗 宋·李清照 小院闲窗春已深");
//        list.add("爱情诗 宋·李清照 一面风情深有韵");
//        list.add("描写春天 宋·李清照 卖花担上");
//        list.add("宋·李清照 欧阳公作《蝶恋花》");
//        list.add("描写梅花 宋·李清照");
//        list.add("寒食节 宋·李清照 萧条庭院");
//        list.add("思乡诗 宋·李清照 故乡何处");
//        list.add("描写春天 宋·李清照 归鸿声断残云碧");
//        list.add("闺怨诗 宋·李清照 风住尘香花已尽，欲语泪先流。");
//        list.add(" 宋·李清照 轻解罗裳，独上兰舟。");
//        list.add("豪放诗 宋·李清照 天接云涛连晓雾。");
//        list.add("描写花 宋·李清照 暗淡轻黄体性柔。");
//        list.add("描写秋天 宋·李清照 寒日萧萧上琐窗");
//        list.add("闺怨诗 宋·李清照");
//        list.add(" 宋·李清照 常记溪亭日暮。");
//        list.add(" 宋·李清照 莫许杯深琥珀浓。");
//        list.add(" 宋·李清照 小院闲窗春色深。");
//        list.add(" 宋·李清照 淡荡春光寒食天。");
//        list.add(" 宋·李清照 泪湿罗衣脂粉满。");
//        list.add(" 宋·李清照 暖日晴风初破冻。");
//        list.add(" 宋·李清照 寒日萧萧上锁窗。");
//        list.add(" 宋·李清照 薄雾浓云愁永昼。");
//        list.add(" 宋·李清照 暗淡轻黄体性柔。");
//        list.add(" 宋·李清照 永夜恹恹欢意少。");
//        list.add(" 宋·李清照 髻子伤春慵更梳。");
//        list.add(" 宋·李清照 绣面芙蓉一笑开。");
//        list.add(" 宋·李清照 谁伴明窗独坐");
//        return list;
//    }
//
//
//    private List<String> getKeyword() {
//        List<String> list = new ArrayList<>();
//        list.add("初中 宋·李清照");
//        list.add("重阳节 宋·李清照");
//        list.add("闺怨诗 宋·李清照");
//        list.add("元宵节 宋·李清照");
//        list.add("婉约诗 宋·李清照");
//        list.add("描写梅花 宋·李清照");
//        list.add(" 宋·李清照 蹴罢秋千");
//        list.add("闺怨诗 宋·李清照");
//        list.add("婉约诗 宋·李清照");
//        list.add("描写春天 宋·李清照");
//        list.add("寒食节 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add("闺怨诗 宋·李清照");
//        list.add("爱情诗 宋·李清照");
//        list.add("描写春天 宋·李清照");
//        list.add("宋·李清照 欧阳公");
//        list.add("梅花 宋·李清照");
//        list.add("寒食节 宋·李清照");
//        list.add("思乡诗 宋·李清照");
//        list.add("描写春天 宋·李清照");
//        list.add("闺怨诗 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add("豪放诗 宋·李清照");
//        list.add("描写花 宋·李清照");
//        list.add("描写秋天 宋·李清照");
//        list.add("闺怨诗 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        list.add(" 宋·李清照");
//        return list;
//    }
//
//}