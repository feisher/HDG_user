package com.yusong.yslib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;


import com.yusong.yslib.ConfigApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

/**
 * @author feisher on 2017/10/26 13:18
 * Email：458079442@qq.com
 */
public class AppUtils {
    private AppUtils() {
    }
    /**
     * 判断对象是否为空
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        }if (obj instanceof String && TextUtils.isEmpty(String.valueOf(obj))) {
            return true;
        }if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     *
     * @param context 上下文
     * @param file  安装文件
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     */
    public static void installApk(@NonNull Context context,@NonNull File file,@NonNull String ... authority) {
        if (null!= file && file.exists()) {
            if (isNotEmpty(authority)) {
                context.startActivity(getInstallAppIntent(context,file,authority[0]));
            }else {
                context.startActivity(getInstallAppIntent(context,file,""));
            }

        }
    }
    public static Intent getInstallAppIntent(Context context,final File file, final String authority) {
        if (file == null) {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = null;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            if (isNotEmpty(authority)) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                data = FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
            }
        }
        intent.setDataAndType(data, type);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getCachePaht(Context context){
        return  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                context.getExternalCacheDir().getAbsolutePath() :
                context.getCacheDir().getAbsolutePath();
    }
    public static String getFilePaht(Context context){
        return  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                Environment.getExternalStorageDirectory().getAbsolutePath() :
                context.getCacheDir().getAbsolutePath();
    }
    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context){
        try{
            long cacheSize = getFolderSize(context.getCacheDir());
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                cacheSize += getFolderSize(context.getExternalCacheDir());
            }
            return getFormatSize(cacheSize);
        }catch (Exception e){}
        return "";
    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && context.getExternalCacheDir() != null) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 删除文件
     *
     * @param dir
     * @return
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children!=null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        }
        return false;
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File f:fileList) {
                if (f.isDirectory()) {
                    size = size + getFolderSize(f);
                } else {
                    size = size + f.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + " GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " TB";
    }

    /**
     * 获取SHa1值
     *
     * @param context
     * @return
     */
    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测网络资源是否存在
     *
     * @param strUrl
     * @return
     */
    public static boolean isNetFileAvailable(final String strUrl) {

        final InputStream[] netFileInputStream = {null};
        ConfigApplication.Companion.getPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(strUrl);
                    URLConnection urlConn = url.openConnection();
                    netFileInputStream[0] = urlConn.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            if (null != netFileInputStream[0]) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (netFileInputStream[0] != null) {
                    netFileInputStream[0].close();
                }
            } catch (IOException e) {
            }
        }
    }


}
