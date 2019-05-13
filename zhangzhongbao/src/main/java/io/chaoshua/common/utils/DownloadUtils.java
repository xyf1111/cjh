package io.chaoshua.common.utils;

import cn.hutool.core.io.IoUtil;
import org.springframework.util.ClassUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 *
 * @author wenying.lin
 * @email lwy@qykfa.com
 * @date 2018-07-23 16:51
 */
public class DownloadUtils {

    /**
     * 获取输入流
     *
     * @param linkUrl 链接地址
     * @return
     */
    public static InputStream getInputStream(String linkUrl) {
        URL url = null;
        try {
            url = new URL(linkUrl);
            URLConnection connection = url.openConnection();
            //请求超时时间
            connection.setConnectTimeout(5000);
            //输入流
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取流文件
     *
     * @param path 资源文件路径
     * @return
     */
    public static InputStream getResourceAsStream(String path) {
        return ClassUtils.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * 下载文件到本地
     *
     * @param urlStr
     * @param filename
     * @param savePath
     */
    public static void download(String urlStr, String filename, String savePath) {
        try {
            //输入流
            InputStream in = getInputStream(urlStr);
            //缓冲数据
            byte[] bytes = new byte[1024];
            //数据长度
            int len;
            //文件
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            OutputStream out = new FileOutputStream(file.getPath() + "\\" + filename);
            //先读到bytes中
            while ((len = in.read(bytes)) != -1) {
                //再从bytes中写入文件
                out.write(bytes, 0, len);
            }
            //关闭IO
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器下载
     *
     * @param inputStream 文件流
     * @param filename    文件名
     * @param response    response
     */
    public static void download(InputStream inputStream, String filename, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = IoUtil.toBuffered(inputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();

            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
