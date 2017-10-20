package com.springboot.test.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springboot.test.common.HttpClientUtil;
import com.springboot.test.common.JsonSerializeUtil;
import com.springboot.test.model.JingCaiMatchInfo;
import com.springboot.test.model.OrderSplit;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/1.
 */
@Service
public class AdminService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Logger logger = LoggerFactory.getLogger(AdminService.class);

    private HashMap<String,JingCaiMatchInfo> jingCaiMatchInfoHashMap= Maps.newHashMap();


    public void getOdds(String url){

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd|HH:mm");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String result=HttpClientUtil.getGetResponse(new URI(url,false).getEscapedURI());

            ObjectMapper mapper = new ObjectMapper();

            HashMap map=mapper.readValue(result, HashMap.class);
           LinkedHashMap<String,LinkedHashMap> list= (LinkedHashMap<String,LinkedHashMap>) map.get("data");
           for(String strings:list.keySet()){
               JingCaiMatchInfo obj=MapToObject(list.get(strings),JingCaiMatchInfo.class);
               System.out.println("Object :"+ JsonSerializeUtil.objectToJson(obj));

               String date=sdf.format(sdf1.parse(obj.getDate()+" "+obj.getTime()));
               String key=date+"|"+obj.getNum();
               jingCaiMatchInfoHashMap.put(key,obj);
           }

           System.out.println("-------------------------------------");

        } catch (URIException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public <T> T MapToObject(LinkedHashMap linkedHashMap, Class<T> c){
        List<T> beanList = new ArrayList<T>();
        ObjectMapper mapper = new ObjectMapper();
        if(linkedHashMap!=null&& !linkedHashMap.isEmpty()){
            T obj =mapper.convertValue(linkedHashMap,c);
            return obj;
        }
        return null;
    }


    public List<OrderSplit> getId(){
        List<OrderSplit> list= Lists.newArrayList();
        String sql= "   select s.id orderId,s.order_code orderCode ,l.logisticsname,l.id logisticId" +
                    "   from order_split s " +
                    "   right join logistics l ON s.id=l.ordersplitid " +
                    "   RIGHT JOIN order_goodsinfor g ON g.split_order_id=s.id " +
                    "   where s.id is not null ";

        try {
            RowMapper<OrderSplit> mapper = new BeanPropertyRowMapper<OrderSplit>(OrderSplit.class);

            return jdbcTemplate.query(sql, new Object[] {}, mapper);

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }




}
