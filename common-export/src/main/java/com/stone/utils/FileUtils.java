package com.stone.utils;

import java.io.File;

/**
 * @author yuanxiu
 * @date 2020/11/7
 */
public class FileUtils {

    public static File createEmptyExcelFile(String fileName) {
        String rootpath = System.getProperty("java.io.tmpdir");
        //路径为 /home/black-pearl/tomcat/temptemp
        if (!rootpath.endsWith("/")) {
            rootpath = rootpath + "/";
        }
        String fileFullName = rootpath + "temp/" + fileName;
        File file = new File(fileFullName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

}
