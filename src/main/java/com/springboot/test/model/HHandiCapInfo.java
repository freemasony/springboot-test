package com.springboot.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/1.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class HHandiCapInfo implements Serializable{

    @JsonProperty("a")
    private String handicapNegative;//让负

    @JsonProperty("d")
    private String handicapFlat;//让平

    @JsonProperty("h")
    private String handicapWins;//让胜

    @JsonProperty("fixedodds")
    private String fixedodds;//让球数

}
