package com.springboot.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/6/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class JingCaiMatchInfo implements Serializable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("num")
    private String num;

    @JsonProperty("date")
    private String date;

    @JsonProperty("time")
    private String time;

    @JsonProperty("l_cn_abbr")
    private String cupName;

    @JsonProperty("h_cn")
    private String homeTeam;//主队

    @JsonProperty("a_cn_abbr")
    private String awayTeam;//客队

    @JsonProperty("hhad")
    public HHandiCapInfo hHandiCapInfo;//让球

    @JsonProperty("had")
    public ShengPingFuInfo ShengPingFuInfo;//非让球


    @JsonProperty("crs")
    private LinkedHashMap<String,String> crsMap;//-1-a 负其他  -1-d 平其他    -1-h 胜其他


    @JsonProperty("ttg")
    private LinkedHashMap<String,String> ttgMap;// s0 0球 s1 1球 s2 2球 ····


    @JsonProperty("hafu")
    private LinkedHashMap<String,String> hafuMap;// a 负 d 平 h 胜
}
