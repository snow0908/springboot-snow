package com.snow.easyexcel.util;

import java.io.*;
import java.util.zip.ZipOutputStream;

public class IOCloseUtils {
    public static void ioClose(BufferedWriter bw) throws IOException {
        if(bw!=null){
            bw.close();
        }
    }

    public static void ioClose(BufferedInputStream bis, FileInputStream fos) throws IOException {
        if (null != bis) {
            bis.close();
        }
        if (null != fos) {
            fos.close();
        }
    }
    public static void ioClose(BufferedOutputStream bos, ZipOutputStream out) throws IOException {
        if (null != bos) {
            bos.close();
        }
        if (null != out) {
            out.close();
        }
    }

}
