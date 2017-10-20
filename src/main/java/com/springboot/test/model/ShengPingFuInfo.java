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
public class ShengPingFuInfo implements Serializable{

    @JsonProperty("a")
    private String negative;//负

    @JsonProperty("d")
    private String flat;//平

    @JsonProperty("h")
    private String wins;//胜

    @JsonProperty("fixedodds")
    private String fixedodds;//让球数

}
