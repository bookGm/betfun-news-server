package me.zhyd.hunter.config.platform;

import me.zhyd.hunter.config.HunterConfig;

/**
 * @author huht
 * @version 1.01
 * @since 1.8
 */
public class BbtcPlatform extends BasePlatform {

    public BbtcPlatform() {
        super(Platform.BBTC.getPlatform());
    }

    @Override
    public HunterConfig process(String url) {
        return this.get(url);
    }
}
