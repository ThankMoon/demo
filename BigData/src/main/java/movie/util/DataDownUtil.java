package movie.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 大数据文件批量检索(电影影评)数据采集核心工具类
 * @author zjh
 * @version v1.0
 * */
public class DataDownUtil {

    /**
     * 根据网址和页面的编码集获取网页的源代码
     * @author zjh
     * @param url 网址
     * @param encoding 编码集
     * @return String 网页的源代码
     * <br /><br />
     * <a  href="https://movie.douban.com/subject/3878007/">
     */
    public static String getHtmlResourceByUrl(String url, String encoding) {
        //存储源代码的容器
        StringBuffer buffer = new StringBuffer();
        URL urlObj = null;
        URLConnection uc = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        try {
            // 建立网络连接
            urlObj = new URL(url);
            // 打开网络连接
            uc = urlObj.openConnection();
            // 建立文件的输入流
            isr = new InputStreamReader(uc.getInputStream(), encoding);
            // 建立文件缓冲流
            reader = new BufferedReader(isr);
            // 建立临时变量
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                buffer.append(temp + "\n");//一边读，一边写
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("网络不给力，请检查网络设置。");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("网络连接失败，请稍后重试。");
        } finally {
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }


    /**
     * 解析源代码，批量采集需要的影评数据
     * @author zjh
     * @return String 网页的源代码
     */
    public static String getContext() {
        String context = "";
        int start = 0;
        while (start >= 0 && start <= 200) {
            System.out.println("==========现在正在读取第" + start + "条记录==========");
            //String url = "https://movie.douban.com/subject/3878007/comments?start=" + start + "&limit=20&sort=new_score&status=P";
            String url = "https://movie.douban.com/subject/27060077/comments?start=" + start + "&limit=20&sort=new_score&status=P";
            start += 20;
            String encoding = "utf-8";

            //1、根据网址和页面的编码集获取网页的源代码
            String html = getHtmlResourceByUrl(url, encoding);
            //System.out.println(html);

            //2、解析源代码，批量采集我们需要的影评数据
            Document document = Jsoup.parse(html);
            // 获取电影影评最外层div元素id = "comments"对象
            Element element = document.getElementById("comments");
            // 获取一组影评信息class = "comment-item"
            Elements elements = element.getElementsByClass("comment-item");
            for (Element ele : elements) {
                // 用户名
                String name = ele.getElementsByTag("a").last().text();
                // 评论时间
                String time = ele.getElementsByClass("comment-time").text();
                // 评论的内容
                String desc = ele.getElementsByClass("short").text();
                // 点赞数量
                String votes = ele.getElementsByClass("votes").text();
                // 推荐等级
                String rate = ele.select("span[title]").attr("title");
                context += "\nname:" + name + "\ntime:" + time + "\ndesc:" + desc + "\nvotes:" + votes + "\nrate:" + rate + "\n";
                //System.out.println("\nname:" + name + "\ntime:" + time + "\ndesc:" + desc + "\nvotes:" + votes);
            }
        }
        return context;
    }

    /**
     * 将内容一行行地写入到文件中
     * @author zjh
     * @param content 内容
     * @param fileName 保存文件的路径
     * @return void 无
     */
    public static void writeFileByLine(String content, String fileName) {
        // 创建一个写入的文件
        File file = new File(fileName);
        // 获取输出文件流的对象
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            // 写入到文件内容中
            writer.print(content);
            // 刷新同步一次
            writer.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    //Java入口
    public static void main(String[] args) {
        System.out.println("80%设计思考，20%时间写代码。" +
                "代码不是给自己看的，是给别人看的。" +
                "要做创新型的工作，写代码就不要百度。");

        String data = getContext();
        System.out.println(data);
        String fileName = "D:\\Desktop\\GreenBook.txt";

        //3、将文字信息转换成文本文件
         writeFileByLine(data ,fileName);

        //4、同步到大数据分布式文件系统中

        //5、计算，处理，可视化

    }
}
