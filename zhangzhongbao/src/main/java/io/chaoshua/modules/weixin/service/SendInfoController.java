package io.chaoshua.modules.weixin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.background.entity.WxStepEntity;
import io.chaoshua.modules.background.service.WxStepService;
import io.chaoshua.modules.weixin.entity.*;
import io.chaoshua.modules.weixin.utils.HttpRequest;
import io.chaoshua.modules.weixin.utils.HttpsUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dws on 2018/12/13 0013.
 */

@Service
public class SendInfoController {

@Autowired
private WxStepService wxStepService;
@Autowired
private HttpsUtil httpsUtil;

    /**
     * 发送给指定用户
     */
    public  String sendInfo(String openId, String first, String keyword1, String keyword2, String remark){
        try {
            WxStepEntity wxStepEntity = getWxStep();
            String token = httpsUtil.getWeiXinToken(wxStepEntity.getAppId(),wxStepEntity.getAppsecret());
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token;
            String param = "";
            Map<String,TemplateData> maps = new HashedMap();
            TemplateData templateData1 = new TemplateData(first,"#0076ff");
            TemplateData templateData2 = new TemplateData(keyword1,"#0076ff");
            TemplateData templateData3 = new TemplateData(keyword2,"#0076ff");
            TemplateData templateData4 = new TemplateData(remark,"#0076ff");
            maps.put("first",templateData1);
            maps.put("keyword1",templateData2);
            maps.put("keyword2",templateData3);
            maps.put("remark",templateData4);
            TemplateMessage templateMessage = new TemplateMessage();
            templateMessage.setTemplate_id(wxStepEntity.getTemplateId());
            templateMessage.setData(maps);
            templateMessage.setTouser(openId);
            templateMessage.setUrl(wxStepEntity.getUrl()+"Mission");
            param = JSONObject.toJSONString(templateMessage);
            String returnJson = HttpRequest.sendPost(url,param);
            JSONObject returnData = JSON.parseObject(returnJson);
            String errmsg = (String)returnData.get("errmsg");
            if (!"ok".equals(errmsg)){
                return "error";
            }else {
                return  "success";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 获取用户列表
     * @param nextOpenid
     * @param accessToken
     * @return
     */
public List<String> getUserList(String nextOpenid, String accessToken,String content){
    //用户openid列表信息
    WeixinUserList openIdListInfo = null;
    WxOpenidInfo wxOpenidInfo = null;
    List<String> openIds = new LinkedList<>();
    synchronized(this){
        try {
            //循环获取用户openid列表
            do{
                //微信公众号获取用户列表信息接口地址
                String requestUrl = null;
                if(StringUtils.isBlank(nextOpenid)){
                    requestUrl = new StringBuffer().append("https://api.weixin.qq.com/cgi-bin/user/get?access_token=").append(accessToken).toString();
                }else {
                    requestUrl = new StringBuffer().append("https://api.weixin.qq.com/cgi-bin/user/get?access_token=")
                            .append(accessToken).append("&next_openid=").append(nextOpenid).toString();
                }
                String json = HttpsUtil.GET(requestUrl);
                openIdListInfo = JSON.parseObject(json,WeixinUserList.class);
                if(openIdListInfo != null && openIdListInfo.getErrcode() == 0){
                    //获取用户openid列表对象
                     wxOpenidInfo = openIdListInfo.getData();
                     for (int i= 0;i<wxOpenidInfo.getOpenid().size();i++){
                         openIds.add(wxOpenidInfo.getOpenid().get(i));
                     }
                     sendTextToOpenid(accessToken,openIds,content);
                    //拉取列表的最后一个用户的OPENID
                    nextOpenid = openIdListInfo.getNext_openid();
                }
            }
            while (openIdListInfo.getCount() == 10000);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
        return openIds;
    }


    /**
     * 根据OpenId进行群发文本消息
     * @param accessToken  授权token
     * @param openids  openid
     * @param content
     * @return
     */
    public void sendTextToOpenid(String accessToken, List<String> openids, String content){
        Map<String, Object> textParams = new HashMap<>();
        textParams.put("content", content);
        TreeMap<String,Object> dataParams = new TreeMap<>();
        dataParams.put("touser", openids);
        dataParams.put("text", textParams);
        dataParams.put("msgtype", "text");
        String data = JSONObject.toJSONString(dataParams);
        System.out.println("根据OpenId进行群发文本消息:" + data);
        String json = HttpRequest.sendPost("https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token="+accessToken, data);
        try {
            Map map = JSON.parseObject(json);
            System.out.println("map：" + map.get("errmsg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WxStepEntity getWxStep(){
        WxStepEntity wxStepEntity = wxStepService.selectOne(new EntityWrapper<WxStepEntity>().eq("status",1));
        return wxStepEntity;
    }
//    public    void  main(String[] args){
//        sendInfo("om-xX6Oi45kgUTU9sUe8WzP0C6Bg","111","222","333","444");
//    }
}
