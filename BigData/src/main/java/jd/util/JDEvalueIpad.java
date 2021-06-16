package jd.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 京东Ipad评价获取（ajax）
 * @author zjh
 * @version v1.0
 * @date 2019/3/13
 */
public class JDEvalueIpad {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            // 定义url
            String url = "https://sclub.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98vv59087&productId=7019143&score=0&sortType=5&page=" + i + "&pageSize=10&isShadowSku=0&fold=1";
            String html = ByUrlGetHtmlSource.getHtmlResource(url, "GBK");
            // 转换成Json对象
            JSONObject jsonObject = JSONPParser.parseJSONP(html);
            // 获取comments
            JSONArray comments = jsonObject.getJSONArray("comments");
            // 遍历
            for (int j = 0, k = comments.size(); j < k; j++) {
                // 获取到每一个用户的评价列表
                JSONObject jo = comments.getJSONObject(j);
                // 获取到用户的评价信息
               String content =  jo.getString("content");
                // 对数据做初步清洗，去除非中文字符
                String rex = "[^\\u4e00-\\u9fa5]";
                // 创建正则
                Pattern compile = Pattern.compile(rex);
                // 匹配
                Matcher matcher = compile.matcher(content);
                // 替换字符
                content = matcher.replaceAll("");

            }
        }
    }
}
