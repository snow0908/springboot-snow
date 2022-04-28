package com.snow.common.util;

import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩包工具
 *
 * @author lbh
 */
@Log4j2
public class ZipUtil {

    private static final String SECRET_MD5_FILE = "secretMd5.json";


    /**
     * zip文件压缩
     *
     * @param textValueMap 待压缩文本内容  , key 文件名称需要包含后缀 value 文件内容
     * @param zipName      zip名称
     */
    public static File stringToZipBytesWithSecretMd5(Map<String, String> textValueMap, String zipName) {
        //压缩文件的临时路径
        File tempPath = new File(System.getProperty("user.dir") + File.separator + "temp");
        boolean mkdirOk = true;
        if (!tempPath.exists() || !tempPath.isDirectory()) {
            //如果不存在，则创建该文件夹
            mkdirOk = tempPath.mkdir();
        }
        if (!mkdirOk) {
            throw new ServiceException("文件夹不存在,且创建失败");
        }

        if (StringUtil.isEmpty(zipName)||zipName.length() < 3){
            throw new ServiceException("文档名,至少三位");
        }

        File tempZipFile;
        try {
            tempZipFile = File.createTempFile(zipName, ".zip", tempPath);
        } catch (IOException e) {
            throw new ServiceException("生成临时文件失败", e);
        }
        //创建zip输出流
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempZipFile))) {
            //主文件
            Map<String, String> secretMd5Map = new HashMap<>(textValueMap.size());
            for (Map.Entry<String, String> entry : textValueMap.entrySet()) {
                String fileName = entry.getKey();
                String textValue = entry.getValue();
                byte[] valueBytes = textValue.getBytes();
                writeZipEntry(out, fileName, valueBytes);
                secretMd5Map.put(fileName, DigestUtils.md5DigestAsHex(valueBytes));
            }
            String secretMd5Str = GsonUtil.toJson(secretMd5Map);
            //MD5 验证文件是否被修改
            writeZipEntry(out, "secretMd5.json", secretMd5Str.getBytes());
        } catch (Exception e) {
            log.error("生成压缩文件失败", e);
            throw new ServiceException("生成压缩文件失败", e);
        }
        return tempZipFile;
    }

    public static File byteToZipBytesWithSecretMd5(Map<String, byte[]> textValueMap, String zipName) {
        //压缩文件的临时路径
        File tempPath = new File(System.getProperty("user.dir") + File.separator + "temp");
        boolean mkdirOk = true;
        if (!tempPath.exists() || !tempPath.isDirectory()) {
            //如果不存在，则创建该文件夹
            mkdirOk = tempPath.mkdir();
        }
        if (!mkdirOk) {
            throw new ServiceException("文件夹不存在,且创建失败");
        }

        if (StringUtil.isEmpty(zipName)||zipName.length() < 3){
            throw new ServiceException("文档名,至少三位");
        }

        File tempZipFile;
        try {
            tempZipFile = File.createTempFile(zipName, ".zip", tempPath);
        } catch (IOException e) {
            throw new ServiceException("生成临时文件失败", e);
        }
        //创建zip输出流
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempZipFile))) {
            //主文件
            Map<String, String> secretMd5Map = new HashMap<>(textValueMap.size());
            for (Map.Entry<String, byte[]> entry : textValueMap.entrySet()) {
                String fileName = entry.getKey();
                byte[] valueBytes = entry.getValue();
                writeZipEntry(out, fileName, valueBytes);
                secretMd5Map.put(fileName, DigestUtils.md5DigestAsHex(valueBytes));
            }
            String secretMd5Str = GsonUtil.toJson(secretMd5Map);
            //MD5 验证文件是否被修改
            writeZipEntry(out, "secretMd5.json", secretMd5Str.getBytes());
        } catch (Exception e) {
            log.error("生成压缩文件失败", e);
            throw new ServiceException("生成压缩文件失败", e);
        }
        return tempZipFile;
    }

    /**
     * zip文件压缩
     *
     * @param textValueMap 待压缩文本内容  , key 文件名称需要包含后缀 value 文件内容
     * @param zipName      zip名称
     */
    public static File stringToZipBytesWithSecret(Map<String, String> textValueMap, String zipName) {
        //压缩文件的临时路径
        File tempPath = new File(System.getProperty("user.dir") + File.separator + "temp");
        boolean mkdirOk = true;
        if (!tempPath.exists() || !tempPath.isDirectory()) {
            //如果不存在，则创建该文件夹
            mkdirOk = tempPath.mkdir();
        }
        if (!mkdirOk) {
            throw new ServiceException("文件夹不存在,且创建失败");
        }

        if (StringUtil.isEmpty(zipName)||zipName.length() < 3){
            throw new ServiceException("文档名,至少三位");
        }

        File tempZipFile;
        try {
            tempZipFile = File.createTempFile(zipName, ".zip", tempPath);
        } catch (IOException e) {
            throw new ServiceException("生成临时文件失败", e);
        }
        //创建zip输出流
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempZipFile))) {
            //主文件
            for (Map.Entry<String, String> entry : textValueMap.entrySet()) {
                String fileName = entry.getKey();
                String textValue = entry.getValue();
                byte[] valueBytes = textValue.getBytes();
                writeZipEntry(out, fileName + ".log", valueBytes);
                out.close();
            }
        } catch (Exception e) {
            log.error("生成压缩文件失败", e);
            throw new ServiceException("生成压缩文件失败", e);
        }
        return tempZipFile;
    }

    /**
     * @param inputStream 输入流
     */
    public static Map<String, byte[]> readZipFile(InputStream inputStream) throws IOException {
        ZipInputStream zIn = new ZipInputStream(inputStream);
        Map<String, byte[]> zipEntryMap = new LinkedHashMap<>(2);
        ZipEntry entry;
        while ((entry = zIn.getNextEntry()) != null) {
            zipEntryMap.put(entry.getName(), readZipEntry(zIn));
        }
        return zipEntryMap;
    }
    public static Map<String, byte[]> readZipFileWithMd5(InputStream inputStream) throws IOException {

        Map<String, byte[]> zipEntryMap = readZipFile(inputStream);

        if (zipEntryMap.get(SECRET_MD5_FILE) == null) {
            throw new ServiceException("文件异常,FILE_ERR");
        }
        String secret = new String(zipEntryMap.get(SECRET_MD5_FILE));
        Map<String, String> secretMap;
        try {
            secretMap = GsonUtil.toMaps(secret);
        } catch (Exception e) {
            throw new ServiceException("文件损坏,SECRET_ERR");
        }
        Map<String, byte[]> byteMap = new HashMap<>(zipEntryMap.size());
        for (Map.Entry<String, String> e : secretMap.entrySet()) {
            String fileName = e.getKey();
            String md5Secret = e.getValue();
            byte[] bytes = zipEntryMap.get(fileName);
            if (bytes == null) {
                throw new ServiceException("文件损坏,FILE_LOSE");
            }
            String md5 = DigestUtils.md5DigestAsHex(bytes);
            if (!md5.equals(md5Secret)) {
                throw new ServiceException("文件损坏,FILE_UPDATE");
            }
            byteMap.put(fileName, bytes);
        }

        return byteMap;
    }


    /**
     * 读取zipEntry
     *
     * @param zIn zip输入流
     */
    private static byte[] readZipEntry(ZipInputStream zIn) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = zIn.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ServiceException("导出临时文件不存在");
        } finally {
            zIn.closeEntry();
        }
    }

    /**
     * 写入zipEntry
     *
     * @param out      zip输出流
     * @param fileName 文件名称
     * @param bytes    bytes
     */
    private static void writeZipEntry(ZipOutputStream out, String fileName, byte[] bytes) throws IOException {
        ZipEntry textEntry = new ZipEntry(fileName);
        out.putNextEntry(textEntry);
        // 读入需要下载的文件的内容，打包到zip文件
        out.write(bytes, 0, bytes.length);
        out.closeEntry();
    }
}
