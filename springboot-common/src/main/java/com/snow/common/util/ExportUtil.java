package com.snow.common.util;

import com.snow.common.pojo.PublicResult;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 导出文件文件的工具类
 */
@Log4j2
public class ExportUtil {

    /**
     * 导出文本文件
     *
     * @param response   响应对象
     * @param jsonString json数据
     * @param fileName   文件名称
     */
    public static void writeToTxt(HttpServletResponse response, HttpServletRequest request, String jsonString, String fileName) throws UnsupportedEncodingException {//设置响应的字符集

        String userAgent = request.getHeader("User-Agent");
        // 针对IE或者以IE为内核的浏览器：
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器的处理：
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        response.setCharacterEncoding("UTF-8");
        //设置响应内容的类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        //通过后缀可以下载不同的文件格式
        response.addHeader("Content-Disposition", String.format("attachment; filename=%s.txt", fileName));
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(delNull(jsonString).getBytes(StandardCharsets.UTF_8));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            log.error("导出文件文件出错", e);
        } finally {
            try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
                log.error("关闭流对象出错 }", e);
            }
        }
    }

    public static PublicResult<Map<String, Object>> outPutToBytes(File file, String fileName) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("file-name", fileName + ".zip");
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            map.put("bytes", outputStream.toByteArray());
            return PublicResult.success("导出成功", map);
        } catch (Exception e) {
            throw new ServiceException("导出临时文件不存在");
        } finally {
            boolean isDelete = file.delete();
            if (!isDelete) {
                log.error("临时文件删除失败");
            }
        }
    }


    /**
     * 如果字符串对象为 null，则返回空字符串，否则返回去掉字符串前后空格的字符串
     *
     * @param str
     * @return
     */
    public static String delNull(String str) {
        String returnStr = "";
        if (StringUtils.isNotBlank(str)) {
            returnStr = str.trim();
        }
        return returnStr;
    }


}
