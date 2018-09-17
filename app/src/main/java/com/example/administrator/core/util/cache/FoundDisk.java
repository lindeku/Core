package com.example.administrator.core.util.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.example.administrator.core.util.EncryptionKeyUtil;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



/*import com.jakewharton.disklrucache.DiskLruCache;*/

/**
 * Created by 林颖 on 2018/4/27.
 *
 */

@SuppressWarnings("JavaDoc")
public class FoundDisk {
        private BufferedOutputStream outputStream;
        private BufferedInputStream inputStream;
        private static FoundDisk foundDisk = null;
        private static DiskLruCache mdiskLruCache = null;
        private static int FILE_MAX_SIZE = 10 * 1024 * 1024;
        private DiskLruCache.Editor editor;
        private DiskLruCache.Snapshot snapshot;
        private FoundDisk(){}
        private FoundDisk(Context context, String uinqueName) throws IOException {
                File cacheDir = getDiskCacheDir(context, uinqueName);
                if (!cacheDir.exists()) {
                        cacheDir.mkdir();

                }
                mdiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, FILE_MAX_SIZE);
        }

        public static FoundDisk openDisk(Context context, String uniqueName)  {
                if (mdiskLruCache != null && foundDisk != null) {
                        return foundDisk;
                }
                try {
                        foundDisk = new FoundDisk(context, uniqueName);
                } catch (IOException e) {
                        return null;
                }
                return foundDisk;
        }


        /**
         * 文件存放路径
         * @param context
         * @param uniqueName
         * @return
         */
        private static File getDiskCacheDir(Context context, String uniqueName) {
                String cachePath;
                if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                        ||!Environment.isExternalStorageRemovable()){
                        cachePath=context.getExternalCacheDir().getPath();
                }else {
                        cachePath = context.getCacheDir().getPath();
                }

                return new File(cachePath + File.separator + uniqueName);
        }

        /**
         *获得版本号
         * @param context
         * @return 版本号
         */
        private static int getAppVersion(Context context) {
                try {
                        PackageInfo info = context.getPackageManager()
                                .getPackageInfo(context.getPackageName(), 0);
                        return info.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                        return 1;
                }
        }

        public DiskLruCache getMdiskLruCache() {
                return mdiskLruCache;
        }

        /**
         * pet 存储
         *
         * @param key
         * @param value
         * @return
         */
        public boolean pet(String key, String value) {
                try {
                        String hashKeyForDisk = EncryptionKeyUtil.hashKeyForDisk(key);
                        editor = mdiskLruCache.edit(hashKeyForDisk);
                        outputStream = new BufferedOutputStream(editor.newOutputStream(0));
                        outputStream.write(value.getBytes());
                        editor.commit();
                        return true;
                } catch (IOException e) {
                        try {
                                editor.abort();
                        } catch (IOException e1) {
                                e1.printStackTrace();
                        }finally {
                                return false;
                        }

                } finally {
                        if (outputStream != null) {
                                try {
                                        outputStream.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }

                }

        }
        public boolean put(String key, byte[] value) {
                try {
                        String keyForDisk = EncryptionKeyUtil.hashKeyForDisk(key);
                        editor = mdiskLruCache.edit(keyForDisk);
                        outputStream = new BufferedOutputStream(editor.newOutputStream(0));
                        outputStream.write(value);
                        editor.commit();
                        return true;
                } catch (IOException e) {
                        try {
                                editor.abort();
                        } catch (IOException e1) {
                                e1.printStackTrace();
                        }finally {
                                return false;
                        }

                } finally {
                        if (outputStream != null) {
                                try {
                                        outputStream.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
        @SuppressWarnings({"finally", "ReturnInsideFinallyBlock"})
        public boolean put(String key, Object object){
                ObjectOutputStream objectOutputStream=null;
                String keyForDisk=EncryptionKeyUtil.hashKeyForDisk(key);
                try {
                        DiskLruCache.Snapshot  snapsho=mdiskLruCache.get(keyForDisk);
                        if(snapsho!=null){
                                mdiskLruCache.remove(keyForDisk);
                        }
                        editor = mdiskLruCache.edit(keyForDisk);
                         objectOutputStream=new ObjectOutputStream(editor.newOutputStream(0));
                        objectOutputStream.writeObject(object);
                        editor.commit();
                        return true;
                } catch (IOException e) {
                        try {
                                editor.abort();
                        } catch (IOException e1) {
                                e1.printStackTrace();
                        }finally {
                                return false;
                        }
                }finally {
                        if (objectOutputStream != null) {
                                try {
                                        objectOutputStream.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }


        /**
         * get 获取
         * @param key
         * @return
         */
        public String getAsString(String key) {
                String keyForDisk = EncryptionKeyUtil.hashKeyForDisk(key);
                BufferedReader reader=null;
                StringBuilder builder=new StringBuilder();
                try {
                        String lin=null;
                        snapshot = mdiskLruCache.get(keyForDisk);
                        if (snapshot!=null){
                                reader=new BufferedReader( new InputStreamReader(snapshot.getInputStream(0)));
                                if ((lin=reader.readLine())!=null) {
                                        builder.append(lin);
                                }
                                return builder.toString();
                        }
                        return null;
                } catch (IOException e) {
                        return null;
                } finally {
                        if (inputStream != null) {
                                try {
                                        inputStream.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }
        public Object getAsObject(String key){
                ObjectInputStream objectInputStream=null;
                String keyForDisk = EncryptionKeyUtil.hashKeyForDisk(key);
                try {
                        snapshot = mdiskLruCache.get(keyForDisk);
                        if(snapshot!=null){
                                objectInputStream=new ObjectInputStream(snapshot.getInputStream(0));
                                return  objectInputStream.readObject();
                        }
                        return null;
                } catch (Exception e) {
                        return null;
                }finally {
                        if(objectInputStream!=null){
                                try {
                                        objectInputStream.close();
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }
        }

        public boolean remove(String key){
          try {
                 return  mdiskLruCache.remove(key);
          } catch (IOException e) {
                        return false;
           }
        }

}
