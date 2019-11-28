

package io.information.modules.news.service.feign.common;


import io.information.modules.news.service.feign.vo.BbtcTickersListVo;

import java.util.List;

/**
 * 返回数据
 */
public class FeignBbApp {
    private String[] kline;
    private List<BbtcTickersListVo> tickers;

    public String[] getKline() {
        return kline;
    }

    public void setKline(String[] kline) {
        this.kline = kline;
    }

    public List<BbtcTickersListVo> getTickers() {
        return tickers;
    }

    public void setTickers(List<BbtcTickersListVo> tickers) {
        this.tickers = tickers;
    }
}
