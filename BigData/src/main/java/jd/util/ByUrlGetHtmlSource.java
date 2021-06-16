package jd.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * 通过网址获取网页源码
 * @author zjh
 * @version v1.0
 * @date 2019/3/13
 */
public class ByUrlGetHtmlSource {

    /*
     * 获取网页源码
     * @author zjh
     * @param url 网址
     * @return String 源码信息
     */
    public static String getHtmlResource(String urlString, String encoding) {
        StringBuilder sb = new StringBuilder();
        InputStreamReader isr = null;
        try {
            // 建立网络连接
            URL url = new URL(urlString);
            // 打开网络连接
            URLConnection uc = url.openConnection();
            // 创建文件输入流(stream都是流，装饰者模式)
            isr = new InputStreamReader(uc.getInputStream(), encoding);
            // 创建缓冲
            BufferedReader br = new BufferedReader(isr);
            // 循环条件，如果这一行不等于空（换行符不等于空）
            // 建立一个临时文件
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("您的网络链接有问题，请检查网络。");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("您访问的网页打不开，请稍后再试。");
        } finally {// 为了保证资源一定会被关闭
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        getHtmlResource("", "");
    }
}


