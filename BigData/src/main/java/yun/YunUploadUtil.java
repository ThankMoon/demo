package yun;

import movie.util.HdfsUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 大数据文件批量上传，云盘核心工具类
 *
 * @author zjh
 * @version v1.0
 * @date 2019/3/22
 */
public class YunUploadUtil {

    /**
     * 文件批量上传
     *
     * @param request  请求
     * @param response 响应
     * @return HashMap 集合
     * @author zjh
     */
    public static HashMap<String, Object> uploadFile(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<String, Object>(16);
        String filename;
        // 设置编码集
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            // 获取文件上传的目录
            String realPath = request.getSession().getServletContext().getRealPath("/");
            // 定义上传服务器的目录
            String dirPath = realPath + "/bigdata";
            // 判断服务器中是否存在bigdata文件夹
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // 上传操作
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解析批量的文件列表
            List items = upload.parseRequest(request);
            if (null != items) {
                Iterator itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        continue;
                    } else {
                        // 重命名，获取源文件的名称arry.jpg.png.txt
                        String name = item.getName();
                        // 获取文件后缀名“.”的索引
                        int i = name.lastIndexOf(".");
                        // 截取源文件的后缀名
                        String ext = name.substring(i, name.length());
                        // 重命名
                        filename = System.currentTimeMillis() + ext;
                        // 将文件流转换成服务器的文件
                        File saveFile = new File(dirPath, filename);
                        // 写入文件到系统中
                        item.write(saveFile);
                        map.put("name", item.getName());
                        map.put("newName", filename);
                        map.put("size", item.getSize());
                        map.put("url", "bigdata/" + filename);
                    }
                }
            }
            // 同步上传至大数据分布式文件系统中
            HdfsUtil hu = new HdfsUtil();
            hu.uploadFile(dirPath, "2019-yun" + new Random().nextInt(10000));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Java入口
     *
     * @return void 无
     * @author zjh
     */
    public static void main(String[] args) {

        // 1.编写文件批量上传工具类

        // 2.将IO流转换成服务器的文件

        // 3.将服务器的文件存储在大数据分布式文件系统中

        // 4.对接前后台，实现动态化
    }
}
