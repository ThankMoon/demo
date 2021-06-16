package jd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/*
  * 将jsoup转化成json对象
  * @author zjh
  * @version v1.0
  * @date 2019/3/13
  */
public class JSONPParser {
    public static JSONObject parseJSONP(String jsonp){
        // 从第一个字符串到第一个括号，去除最后括号
        // 获取第一个括号和最后一个括号的位置
        int startIndex = jsonp.indexOf("(");
        int endIndex = jsonp.lastIndexOf(")");
        // 获取标准json字符串
        String json = jsonp.substring(startIndex+1,endIndex);
        // 将json-->jsonObject
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject;
    }
}
