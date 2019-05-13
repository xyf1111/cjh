package io.chaoshua.modules.weixin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.modules.background.entity.WxStepEntity;
import io.chaoshua.modules.background.service.WxStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by dws on 2018/12/13 0013.
 */
@Service
public class HttpsUtil {
@Autowired
private WxStepService wxStepService;
    /**
     * 获取微信token
     * @return
     */
    public  String getWeiXinToken(String APPID,String APPSECRET) {
        String grant_type = "client_credential";
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String param = "appid=" + APPID + "&secret=" + APPSECRET + "&grant_type=" + grant_type;
        String tokenResult = HttpRequest.sendGet(url, param);
        Map map = (Map) JSON.parse(tokenResult);
        return map.get("access_token").toString();
    }

    /**
     * 获取用户openId
     * @param getcode
     * @return
     */
    public  String getOpenid(@RequestParam(value="code",required=false)String getcode) {//接收用户传过来的code，required=false表明如果这个参数没有传过来也可以。
        WxStepEntity wxStepEntity = getWxStep();
        String code=getcode;
        //接收从客户端获取的code
        //向微信后台发起请求获取openid的url
        String WX_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=JSCODE&grant_type=authorization_code";
        //这三个参数就是之后要填上自己的值。
        //替换appid，appsecret，和code
        String requestUrl = WX_URL.replace("APPID", wxStepEntity.getAppId()).//填写自己的appid
                replace("SECRET", wxStepEntity.getAppsecret()).replace("JSCODE", code);//填写自己的appsecret，

        //调用get方法发起get请求，并把返回值赋值给returnvalue
        String  returnvalue= GET(requestUrl);
        System.out.println(requestUrl);//打印发起请求的url
        System.out.println(returnvalue);//打印调用GET方法返回值
        //定义一个json对象。
        JSONObject convertvalue=new JSONObject();

        //将得到的字符串转换为json
        convertvalue=(JSONObject) JSON.parse(returnvalue);
        //把openid和sessionkey分别赋值给openid和sessionkey
        String openid=(String) convertvalue.get("openid");
        String sessionkey=(String) convertvalue.get("session_key");//定义两个变量存储得到的openid和session_key.

        return openid;//返回openid
    }

    //发起get请求的方法。
    public static String GET(String url) {
        String result = "";
        BufferedReader in = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.connect();
            Map<String, List<String>> map = conn.getHeaderFields();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            in = new BufferedReader(isr);
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            // 异常记录
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
            } catch (Exception e2) {
                // 异常记录
            }
        }
        return result;
    }

    private WxStepEntity getWxStep(){
        WxStepEntity wxStepEntity = wxStepService.selectOne(new EntityWrapper<WxStepEntity>().eq("status",1));
        return wxStepEntity;
    }
}
