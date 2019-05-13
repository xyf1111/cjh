package io.chaoshua.modules.seller.controller;

import io.chaoshua.common.ueditor.ActionEnter;
import io.chaoshua.common.utils.Constant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;


/**
 * 用于处理关于ueditor插件相关的请求
 *
 * @author Guoqing
 * @date 2017-11-29
 */
@RestController
@CrossOrigin
@RequestMapping("/app/seller/ueditor")
public class AppUeditorController {

    @RequestMapping(value = "/exec")
    @ResponseBody
    public String exec(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        String result = new ActionEnter(request, Constant.rootpath).exec();
        //给前台返回jsonp格式的数据
        return result;
    }

}
