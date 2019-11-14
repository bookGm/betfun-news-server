package io.information.modules.news.service.feign.vo;

import java.io.Serializable;

public class TickerVo implements Serializable {
    private static final long serialVersionUID = 1L;
    Double amt_24h;
    Integer amt_day;
    Double buy;
    Integer change;
    Long date;
    Double high;
    Double last;
    Double last_price;
    Double low;
    Double price_8clock;
    Double price_24h_before;
    Double price_day;
    Double sell;
    Integer vol_day;
    Double volume;

    public Double getAmt_24h() {
        return amt_24h;
    }

    public void setAmt_24h(Double amt_24h) {
        this.amt_24h = amt_24h;
    }

    public Integer getAmt_day() {
        return amt_day;
    }

    public void setAmt_day(Integer amt_day) {
        this.amt_day = amt_day;
    }

    public Double getBuy() {
        return buy;
    }

    public void setBuy(Double buy) {
        this.buy = buy;
    }

    public Integer getChange() {
        return change;
    }

    public void setChange(Integer change) {
        this.change = change;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public Double getLast_price() {
        return last_price;
    }

    public void setLast_price(Double last_price) {
        this.last_price = last_price;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getPrice_8clock() {
        return price_8clock;
    }

    public void setPrice_8clock(Double price_8clock) {
        this.price_8clock = price_8clock;
    }

    public Double getPrice_24h_before() {
        return price_24h_before;
    }

    public void setPrice_24h_before(Double price_24h_before) {
        this.price_24h_before = price_24h_before;
    }

    public Double getPrice_day() {
        return price_day;
    }

    public void setPrice_day(Double price_day) {
        this.price_day = price_day;
    }

    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    public Integer getVol_day() {
        return vol_day;
    }

    public void setVol_day(Integer vol_day) {
        this.vol_day = vol_day;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
