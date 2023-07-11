/**
 * @(#)ZipUtils.java, 2018/2/25.
 * <p/>
 * Copyright 2018 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.yinan.play.demo.util;



import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 傅皆绮(hzfujieqi @ corp.netease.com)
 */
public class ZipUtils {
    /**
     * 压缩文件列表
     *
     * @param destFilePath    目标文件路径
     * @param srcFilePathList 源文件路径列表
     * @return 目标文件
     */
    public static File zipFilePathList(String destFilePath, Collection<String> srcFilePathList) {
        File destFile = new File(destFilePath);
        return zipFilePathList(destFile, srcFilePathList);
    }

    /**
     * 压缩文件列表
     *
     * @param destFilePath 目标文件路径
     * @param srcFileList  源文件列表
     * @return 目标文件
     */
    public static File zipFileList(String destFilePath, Collection<File> srcFileList) {
        File destFile = new File(destFilePath);
        return zipFileList(destFile, srcFileList);
    }

    /**
     * 压缩文件列表
     *
     * @param destFile        目标文件
     * @param srcFilePathList 源文件路径列表
     * @return 目标文件
     */
    public static File zipFilePathList(File destFile, Collection<String> srcFilePathList) {
        List<File> srcFileList = new ArrayList<>();
        for (String srcFilePath : srcFilePathList) {
            srcFileList.add(new File(srcFilePath));
        }
        return zipFileList(destFile, srcFileList);
    }

    /**
     * 压缩文件列表
     *
     * @param destFile    目标文件
     * @param srcFileList 源文件列表
     * @return 目标文件
     */
    public static File zipFileList(File destFile, Collection<File> srcFileList) {
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            boolean succ = true;
            if (destFile.exists()) {
                succ = destFile.delete();
            }
            succ = succ || destFile.createNewFile();
            if (!succ){
                return destFile;
            }
            fileOutputStream = new FileOutputStream(destFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            zipFileList(srcFileList, zipOutputStream);
        } catch (IOException ignored) {
        } finally {
            try {
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ignored) {
            }
        }
        return destFile;
    }

    /**
     * 压缩文件列表
     *
     * @param fileList        待压缩的文件列表
     * @param zipOutputStream 压缩输出流
     */
    public static void zipFileList(Collection<File> fileList, ZipOutputStream zipOutputStream) {
        if (!CollectionUtils.isEmpty(fileList)) {
            for (File file : fileList) {
                zipFileList(file, zipOutputStream);
            }
        }
    }

    /**
     * 压缩文件列表
     *
     * @param inputFile       待压缩的文件
     * @param zipOutputStream 压缩输出流
     */
    public static void zipFileList(File inputFile, ZipOutputStream zipOutputStream) {
        if (inputFile.exists()) {
            // 文件打包
            if (inputFile.isFile()) {
                FileInputStream fileInputStream = null;
                BufferedInputStream bufferedInputStream = null;
                try {
                    fileInputStream = new FileInputStream(inputFile);
                    bufferedInputStream = new BufferedInputStream(fileInputStream, 4096);

                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    zipOutputStream.putNextEntry(entry);

                    int size;
                    byte[] buffer = new byte[4096];
                    while ((size = bufferedInputStream.read(buffer)) != -1) {
                        zipOutputStream.write(buffer, 0, size);
                    }
                } catch (IOException e) {
                } finally {
                    try {
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                    } catch (IOException ignored) {
                    }
                }
            }

            // 目录打包
            else {
                try {
                    File[] fileList = inputFile.listFiles();
                    if (fileList != null && fileList.length > 0) {
                        for (File file : fileList) {
                            zipFileList(file, zipOutputStream);
                        }
                    }
                } catch (RuntimeException ignored) {
                }
            }
        }
    }
}
