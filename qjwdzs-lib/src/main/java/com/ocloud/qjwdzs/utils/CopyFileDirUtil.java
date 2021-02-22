package com.ocloud.qjwdzs.utils;


import java.io.*;

public class CopyFileDirUtil  {
    public static int IS_EXISTS = 0;

    /**
     * 文件的拷贝
     *
     * @param sourcePath
     * @param targetPath
     */
    public static void copyFile(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);
        copyFile(sourceFile, targetFile);
    }

    /**
     * 文件的拷贝
     *
     * @param sourceFile
     * @param targetFile
     */
    public static void copyFile(File sourceFile, File targetFile) {
        if (!sourceFile.exists()) {
            System.out.println("源文件没有找到");
            return;
        }
        if (targetFile.exists()) {
            System.out.println("目标文件已经存在，删除已存在的目标文件");
            targetFile.delete();
        }
        BufferedInputStream fis = null;
        BufferedOutputStream fos = null;
        int len = 100;
        int count = -1;
        byte[] buffer = new byte[len];
        try {
            fis = new BufferedInputStream(new FileInputStream(sourceFile));
            fos = new BufferedOutputStream(new FileOutputStream(targetFile));
            while ((count = fis.read(buffer, 0, len)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("文件没有找到");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件读取异常");
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {
                System.out.println("流关闭异常");
            }
        }
    }
}