package com.example.public_transportation_query_system.util;

import java.io.File;

public class FileUtil {

    /**
     * 获取 path 相对于程序根目录的绝对路径
     * @param path 路径
     * @return
     */
    public static String getRootPathWidth(String path) {
        File file = new File(path);

        try {
            path = file.getCanonicalPath();
        } catch (Exception e) {}

        return path;
    }

    /**
     * 获取 path 相对于程序长传目录的绝对路径
     * @param path 路径
     * @return
     */
    public static String getUploadPathWith(String path) {
        return getRootPathWidth("./upload" + path);
    }

    /**
     * 返回文件路径是否存在并且在上传目录内
     * @return
     */
    public static boolean isSafeUploadFile(String filePath) {
        File file = new File("./upload" + filePath);
        String path = "";

        try {
            path = file.getCanonicalPath();
        } catch (Exception e) {}

        return file.isFile() && path.indexOf(getUploadPathWith("")) == 0;
    }
}
