package com.example.administrator.core.util.file;

import android.os.Environment;

import com.example.administrator.core.app.Configure;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 林颖 on 2018/5/17.
 *
 */

public class FileUtil {
    private final static String FILE_CEATE_TIME_FORM="_yyyyMMdd_HHmmss";
    private final static String MEMORY_CARD_CACHE= Configure.getContext().getExternalCacheDir().getPath();
    private final static String BUILT_IN_CACHE= Configure.getContext().getCacheDir().getPath();

    /**
     *  获得文件后缀名
     */
    public static String getExtension(String filePath){
        String suffix = "";
        final File file=new File(filePath);
        final String name=file.getName();
        final int idx=name.indexOf(".");
        if(idx>0){
            suffix=name.substring(idx+1);
        }
        return suffix;

    }

    public static File writeToDisk(InputStream is, String dir, String prefix, String extension) {
        final File fileByTime = createFileByTime(dir, prefix, extension);
        BufferedInputStream bis=null;
        FileOutputStream fos=null;
        BufferedOutputStream bos=null;
        try {
            fos=new FileOutputStream(fileByTime);
            bis=new BufferedInputStream(is);
            bos=new BufferedOutputStream(fos);

            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }
            bos.flush();
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
            if(bos!=null){
                bos.close();
              }
              if(fos!=null){
                  fos.close();
              }
              if(bis!=null){
                  bos.close();
              }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileByTime;
    }

    public static File writeToDisk(InputStream is, String dir,String fileName){
       final File file = createFile(dir, fileName);
        BufferedInputStream bis=null;
        FileOutputStream fos=null;
        BufferedOutputStream bos=null;
        try{
            bis=new BufferedInputStream(is);
            fos=new FileOutputStream(file);
            bos=new BufferedOutputStream(fos);
            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }
            bos.flush();
            fos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bis!=null){
                    bis.close();
                }
                if(bos!=null){
                    bos.close();
                }
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 思路
     * ------------没有文件名的情况
     * 先处理文件 后缀名加上当前时间作为文件名
     * 再实例化文件存放路径
     * 再把文件存放路径作为相对路径，实例化一个文件
     * @param cacheCatalogue 目录名
     * @param timeFormatHeader 后缀名大写
     * @param extension 后缀名
     * @return file
     */
    private static File  createFileByTime(String cacheCatalogue,String timeFormatHeader, String extension){
        final String fileName=getFileNameByTime(timeFormatHeader,extension);//处理文件名
        return createFile(cacheCatalogue, fileName);
    }
    //返回完整文件名带后缀
    private static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    //处理文件名
    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date=new Date(System.currentTimeMillis());
        final SimpleDateFormat format=new SimpleDateFormat("'" + timeFormatHeader + "'" + FILE_CEATE_TIME_FORM);
        return format.format(date);
    }
    private static File createFile(String cacheCatalogue, String fileName){
        return new File(createDir(cacheCatalogue),fileName);
    }
    //处理相对路径
    private static File createDir(String cacheCatalogue) {
        String dir;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                ||!Environment.isExternalStorageRemovable()){
            dir=MEMORY_CARD_CACHE+"/"+cacheCatalogue;
        }else {
            dir=BUILT_IN_CACHE+"/"+cacheCatalogue;
        }
        final File fileDir=new File(dir);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static String getRawFile(int id){
        final InputStream is = Configure.getContext().getResources().openRawResource(id);
        final BufferedInputStream bis = new BufferedInputStream(is);
        final InputStreamReader isr = new InputStreamReader(bis);
        final BufferedReader br = new BufferedReader(isr);
        final StringBuilder stringBuilder = new StringBuilder();
        String str;
        try {
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

}
