package com.springboot.test.push.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.springboot.test.model.entity.DingUserInfo;
import com.taobao.api.ApiException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujian
 * @date 2019/9/18
 */
@Component
public class DingtalkService {


    public String getAccessToken() throws ApiException {
        String accessToken="";
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dingba4omltyydkf8mxv");
        request.setAppsecret("gOnDSAj5WpOibHXwNmSFwD-MCf5PR9sDPqs7jU1Xzw4Nko73dAgqD5mctrn2igE9");
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        if(response!=null){
            accessToken=response.getAccessToken();
        }
        return accessToken;
    }

    public  List<Long> getAuthScopes() throws ApiException {
        List<Long> result=new ArrayList<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/auth/scopes");
        OapiAuthScopesRequest request = new OapiAuthScopesRequest();
        request.setHttpMethod("GET");
        OapiAuthScopesResponse response = client.execute(request, getAccessToken());
        if(response!=null){
            result=response.getAuthOrgScopes().getAuthedDept();
        }
        return result;
    }

    public Map<String,Long> getDepartmentList() throws ApiException {
        Map<String,Long> result=new HashMap<>();
        List<Long> ids=getDepartmentListIds();
        System.out.println("分半数:"+ids.size());
        for(Long id:ids){
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest request = new OapiDepartmentListRequest();
            request.setId(id.toString());
            request.setFetchChild(true);
            request.setHttpMethod("GET");
            OapiDepartmentListResponse response = client.execute(request, getAccessToken());
            for(OapiDepartmentListResponse.Department department:response.getDepartment()) {
                result.put(department.getName(),department.getId());
            }
        }
        System.out.println("分半数:"+result.size());
        return result;
    }

    public List<Long> getDepartmentListIds() throws ApiException {
        List<Long> result=new ArrayList<>();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_ids");
        OapiDepartmentListIdsRequest request = new OapiDepartmentListIdsRequest();
        request.setId("1");
        request.setHttpMethod("GET");
        OapiDepartmentListIdsResponse response = client.execute(request, getAccessToken());
        result=response.getSubDeptIdList();
        return result;
    }

    public List<DingUserInfo> getUserInfoByDepartMentId() throws ApiException {
        List<DingUserInfo> result=new ArrayList<>();
        Map<String,Long> departmentIds=getDepartmentList();
        for(String key:departmentIds.keySet()){
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
            OapiUserListbypageRequest request = new OapiUserListbypageRequest();
            request.setDepartmentId(departmentIds.get(key));
            request.setSize(100L);
            request.setOrder("entry_desc");
            request.setHttpMethod("GET");
            boolean flag=true;
            int i=0;
            while (flag){
                request.setOffset(i*100L);
                OapiUserListbypageResponse execute = client.execute(request,getAccessToken());
                if(execute!=null && CollectionUtils.isNotEmpty(execute.getUserlist())){
                    System.out.println("name:"+key+";id:"+departmentIds.get(key)+";count:"+execute.getUserlist().size());
                    for(OapiUserListbypageResponse.Userlist user:execute.getUserlist()){
                        DingUserInfo info=new DingUserInfo(user.getMobile(),user.getName(),user.getEmail(),user.getUserid(),departmentIds.get(key).toString(),key);
                        result.add(info);
                    }
                    flag=execute.getHasMore();
                    i=i+1;
                }else {
                    flag=false;
                }
            }
        }

        System.out.println(result.toString());
        return result;
    }


    public void test(){

    }


    public void send() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList("245027093226102075");
        request.setAgentId(274855076L);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("xxx123411111");
        msg.getActionCard().setMarkdown("### 测试123111");
        msg.getActionCard().setSingleTitle("测试测试");
        msg.getActionCard().setSingleUrl("https://www.baidu.com");
        msg.setMsgtype("action_card");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,getAccessToken());
        System.out.println();
    }
}
