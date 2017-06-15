package cn.lygtc.coolweather.gson;

/**
 * Created by student on 2017/6/15.
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String api;
        public String pm25;
    }
}
