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
public class OrderSplit implements Serializable{

    @JsonProperty("orderId")
    private Integer orderId;

    @JsonProperty("orderCode")
    private String orderCode;

    @JsonProperty("logisticsname")
    private String logisticsname;

    @JsonProperty("logisticId")
    private Integer logisticId;


}
