package com.snow.es.api;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


@Log4j2
public class aaaa {
    public static void main(String[] args) throws Exception {
        try {
            readZipFile("F:\\wKjYAmKXpa2AAyvEAADX9SKSG_E97.zip");

//            tt("F:\\wKjYAmKXpa2AAyvEAADX9SKSG_E97.zip","F:\\tt");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                System.err.println("file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze),Charset.forName("GBK")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }



    public static void tt(String zipFile,String outDir) throws IOException {
        File outFileDir = new File(outDir);
        if (!outFileDir.exists()) {
            boolean isMakDir = outFileDir.mkdirs();
            if (isMakDir) {
                log.info("创建压缩目录成功");
            }
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("gbk"));
        for (Enumeration enumeration = zip.entries(); enumeration.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) enumeration.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            if (entry.isDirectory()) { //处理压缩文件包含文件夹的情况
                File fileDir = new File(outDir + zipEntryName);
                fileDir.mkdir();
                continue;
            }
            File file = new File(outDir, zipEntryName);
            file.createNewFile();
            OutputStream out = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) > 0) {
                out.write(buff, 0, len);
            }
            in.close();
            out.close();

        }
    }



}

