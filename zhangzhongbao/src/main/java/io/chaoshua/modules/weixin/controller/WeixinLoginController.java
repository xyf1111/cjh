package io.chaoshua.modules.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.background.entity.WxStepEntity;
import io.chaoshua.modules.background.service.WxStepService;
import io.chaoshua.modules.weixin.utils.HttpsUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by dws on 2018/12/14 0014.
 */
@RestController
@RequestMapping("/app/user/weiXinLogin")
public class WeixinLoginController {
    @Autowired
    private WxStepService wxStepService;

    @RequestMapping("/loginInit")
    public String loginInit(HttpServletRequest request, HttpServletResponse response)  {
        WxStepEntity wxStepEntity = getWxStep();
        // 默认域名
        String domain = "xengaq7.top";
        if(wxStepEntity != null && StringUtils.isNotBlank(wxStepEntity.getUrl())){
            domain =  getStr(wxStepEntity.getUrl());
        }
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
        String backUrl= String.format("http://%s/app/user/weiXinLogin/getWechatGZAccessToken",domain);

        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId
        String url = null;
        try {
          //  https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsap
            url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxStepEntity.getAppId()+
                    "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                    "&response_type=code" +
                    "&scope=snsapi_base " +
                    "&state=STATE#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:"+url;
    }

    @RequestMapping("/getWechatGZAccessToken")
    public String getWechatGZAccessToken(HttpServletRequest request,HttpServletResponse response) throws Exception{
        WxStepEntity wxStepEntity = getWxStep();
        //微信公众号的APPID和APPSECRET
        String code=request.getParameter("code");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + wxStepEntity.getAppId()+
                "&secret=" +wxStepEntity.getAppsecret()+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = HttpsUtil.GET(url);
        Map<String,Object> data = JSONObject.parseObject(result);
        String openid=data.get("openid").toString();
        String token=data.get("access_token").toString();
        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        String infoResult = HttpsUtil.GET(infoUrl);
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("getWechatGZAccessToken=" + infoResult);
        System.out.println("-----------------------------------------------------------------------------");
        return infoResult;
    }

    private WxStepEntity getWxStep(){
        WxStepEntity wxStepEntity = wxStepService.selectOne(new EntityWrapper<WxStepEntity>().eq("status",1));
        return wxStepEntity;
    }

    private static String getStr(String Str) {
        String newStr = Str.replace("http://", "");
        String string = newStr.substring(0, newStr.indexOf("/"));
        System.out.println(string);
        return string;
    }

}
