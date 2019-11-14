package io.information.modules.news.service.feign.vo;

import java.io.Serializable;

public class BbtcTickersListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    String coin;
    Double convert_cny;
    String currency;
    String currency_show;
    Integer day5_vol;
    String exchange;
    Double [] kline;
    String pair;
    String symbol_show;
    TickerVo ticker;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Double getConvert_cny() {
        return convert_cny;
    }

    public void setConvert_cny(Double convert_cny) {
        this.convert_cny = convert_cny;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency_show() {
        return currency_show;
    }

    public void setCurrency_show(String currency_show) {
        this.currency_show = currency_show;
    }

    public Integer getDay5_vol() {
        return day5_vol;
    }

    public void setDay5_vol(Integer day5_vol) {
        this.day5_vol = day5_vol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public Double[] getKline() {
        return kline;
    }

    public void setKline(Double[] kline) {
        this.kline = kline;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getSymbol_show() {
        return symbol_show;
    }

    public void setSymbol_show(String symbol_show) {
        this.symbol_show = symbol_show;
    }

    public TickerVo getTicker() {
        return ticker;
    }

    public void setTicker(TickerVo ticker) {
        this.ticker = ticker;
    }
}
