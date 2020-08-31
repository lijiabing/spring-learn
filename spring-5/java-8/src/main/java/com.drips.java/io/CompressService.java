package com.drips.java.io;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件测试
 *
 * @author lijb
 */
public class CompressService {


    private static final String ZIP_FILE = "F:\\test\\target\\test.zip";
    private static final String JPG_FILE = "F:\\test\\source\\0.jpg";
    private static final String FILE_NAME = "test";


    public static void main(String[] args) {
        CompressService compressService = new CompressService();
        compressService.zipFileChannel();
    }

    public void zipFileNoBuffer() {
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                try (InputStream inputStream = new FileInputStream(JPG_FILE)) {
                    zipOutputStream.putNextEntry(new ZipEntry(FILE_NAME + i));
                    int temp = 0;
                    while ((temp = inputStream.read()) != -1) {
                        zipOutputStream.write(temp);
                    }
                }
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void zipFileBuffer() {
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
            long beginTime = System.currentTimeMillis();
            for (int i = 0; i < 10; i++) {
                try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(JPG_FILE))) {
                    zipOutputStream.putNextEntry(new ZipEntry(FILE_NAME + i));
                    int temp = 0;
                    while ((temp = inputStream.read()) != -1) {
                        bufferedOutputStream.write(temp);
                    }
                }
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void zipFileChannel() {
        long beginTime = System.currentTimeMillis();
        File zipFile = new File(ZIP_FILE);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
             WritableByteChannel writableByteChannel = Channels.newChannel(zipOutputStream)) {
            for (int i = 0; i < 10; i++) {
                try (FileChannel fileChannel = new FileInputStream(JPG_FILE).getChannel()) {
                    zipOutputStream.putNextEntry(new ZipEntry(FILE_NAME + i));
                    fileChannel.transferTo(0, 4194304, writableByteChannel);
                }
            }
            printInfo(beginTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void printInfo(long time) {
        System.out.println("压缩时间=" + (System.currentTimeMillis() - time) + "ms");
    }
}
