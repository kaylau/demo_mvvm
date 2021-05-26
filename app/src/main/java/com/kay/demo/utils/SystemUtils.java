package com.kay.demo.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.kay.demo.GlobalApp;
import com.kay.demo.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

/**
 * create time 2020/5/28
 * Description：SystemUtils
 */
public class SystemUtils {
    /**
     * http://www.cnblogs.com/lvcha/p/3721091.html
     * Installtion ID
     * 以上几种方式都或多或少存在一定的局限性或者Bug，如果并不是确实需要对硬件本身进行绑定，使用自己生成的UUID也是一个不错的选择，因为该方法无需访问设备的资源，也跟设备类型无关。
     * <p>
     * 这种方式的原理是在程序安装后第一次运行时生成一个ID，该方式和设备唯一标识不一样，不同的应用程序会产生不同的ID，同一个程序重新安装也会不同。所以这不是设备的唯一ID，但是可以保证每个用户的ID是不同的。可以说是用来标识每一份应用程序的唯一ID（即Installtion ID），可以用来跟踪应用的安装数量等。
     * <p>
     * Google Developer Blog提供了这样的一个框架：
     * http://blog.csdn.net/u013062469/article/details/51851659
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId() {
        String deviceId = "";
        try {
            TelephonyManager tm = (TelephonyManager) GlobalApp.Companion.getContext().getSystemService
                    (Context
                            .TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();//10.0之后获取不到
            tm = null;
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = Settings.Secure.getString(GlobalApp.Companion.getContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            //处理10.0之后的bug
            deviceId = Settings.Secure.getString(GlobalApp.Companion.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * Static factory to retrieve a type 4 (pseudo randomly generated) UUID.
     * <p>
     * The {@code UUID} is generated using a cryptographically strong pseudo
     * random number generator.
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    /**
     * 获取手机IMSI号
     * https://blog.csdn.net/u013059863/article/details/49847109
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        String imei = "";
        TelephonyManager mTelephonyMgr = null;
        try {
            mTelephonyMgr = (TelephonyManager) GlobalApp
                    .Companion.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imei = mTelephonyMgr.getImei();
            } else {
                imei = getDeviceId();
            }
        } catch (Exception e) {
            //处理10.0之后的bug
            imei = getDeviceId();
        }
        if (null == imei) {
            imei = getDeviceId();
        }
        return imei;
    }

    /**
     * androidId
     *
     * @return
     */
    public static String getAndroidId() {
        return Settings.Secure.getString(GlobalApp.Companion.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * 设备序列号
     *
     * @return
     */
    public static String getSerial(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取屏幕分辨率高_宽
     *
     * @param context
     * @return
     */
    public static String getWindowW_H(Context context) {
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        int mScreenHeigh = dm.heightPixels;
        return mScreenWidth + "_" + mScreenHeigh;
    }

    /**
     * 获取屏幕分辨率宽
     */
    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenWidth = dm.widthPixels;
        return mScreenWidth;
    }

    /**
     * 获取屏幕分辨率高
     */
    public static int getWindowHeigh(Context context) {
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int mScreenHeigh = dm.heightPixels;
        return mScreenHeigh;
    }

    /**
     * 获取当前的运营商
     *
     * @param context
     * @return 运营商名字
     */
    @SuppressLint("MissingPermission")
    public static String getCarrier(Context context) {
        String ProvidersName = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String IMSI = telephonyManager.getSubscriberId();
            if (IMSI != null) {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                    ProvidersName = "中国移动";
                } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                    ProvidersName = "中国联通";
                } else if (IMSI.startsWith("46003")) {
                    ProvidersName = "中国电信";
                } else {
                    ProvidersName = "其他";
                }
                return ProvidersName;
            } else {
                return telephonyManager.getSimOperatorName();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return telephonyManager.getSimOperatorName();
        }
    }

    /**
     * 获取ip地址
     *
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }


            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return "";
    }


    public static String getMac(Context context) {

        String strMac = "02:00:00:00:00:00";

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            strMac = getLocalMacAddressFromWifiInfo(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            strMac = getMacAddress(context);
            return strMac;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!TextUtils.isEmpty(getMacAddress(context))) {
                strMac = getMacAddress(context);
                return strMac;
            } else if (!TextUtils.isEmpty(getMachineHardwareAddress())) {
                strMac = getMachineHardwareAddress();
                return strMac;
            } else {
                strMac = getLocalMacAddressFromBusybox();
                return strMac;
            }
        }
        return strMac;
    }

    /**
     * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
     *
     * @return
     */
    public static String getLocalMacAddressFromBusybox() {
        String result = "";
        String Mac = "";
        result = callCmd("busybox ifconfig", "HWaddr");
        // 如果返回的result == null，则说明网络不可取
        if (result == null) {
            return "网络异常";
        }
        // 对该行数据进行解析
        // 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
        if (result.length() > 0 && result.contains("HWaddr") == true) {
            Mac = result.substring(result.indexOf("HWaddr") + 6,
                    result.length() - 1);
            result = Mac;
        }
        return result;
    }

    private static String callCmd(String cmd, String filter) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);

            while ((line = br.readLine()) != null
                    && line.contains(filter) == false) {
                result += line;
            }

            result = line;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * android 7.0及以上 （2）扫描各个网络接口获取mac地址
     *
     */
    /**
     * 获取设备HardwareAddress地址
     *
     * @return
     */
    public static String getMachineHardwareAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        String hardWareAddress = "";
        NetworkInterface iF = null;
        if (interfaces == null) {
            return "";
        }
        while (interfaces.hasMoreElements()) {
            iF = interfaces.nextElement();
            try {
                hardWareAddress = bytesToString(iF.getHardwareAddress());
                if (hardWareAddress != null)
                    break;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return hardWareAddress;
    }

    /***
     * byte转为String
     *
     * @param bytes
     * @return
     */
    private static String bytesToString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        for (byte b : bytes) {
            buf.append(String.format("%02X:", b));
        }
        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    /**
     * 根据wifi信息获取本地mac
     *
     * @param context
     * @return
     */
    private static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac = winfo.getMacAddress();
        if (null == mac) {
            mac = "";
        }
        return mac;
    }

    /**
     * android 6.0及以上、7.0以下 获取mac地址
     *
     * @param context
     * @return
     */
    private static String getMacAddress(Context context) {

        // 如果是6.0以下，直接通过wifimanager获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String macAddress0 = getMacAddress0(context);
            if (!TextUtils.isEmpty(macAddress0)) {
                return macAddress0;
            }
        }
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
//            Log.e("----->" + "NetInfoManager", "getMacAddress:" + ex.toString());
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/eth0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
//                Log.e("----->" + "NetInfoManager",
//                        "getMacAddress:" + e.toString());
            }

        }
        return macSerial;
    }

    private static String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
//                Log.e("----->" + "NetInfoManager",
//                        "getMacAddress0:" + e.toString());
                return "";
            }

        }
        return "";

    }

    /**
     * Check whether accessing wifi state is permitted
     *
     * @param context
     * @return
     */
    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
//            Log.e("----->" + "NetInfoManager", "isAccessWifiStateAuthorized:"
//                    + "access wifi state is enabled");
            return true;
        } else
            return false;
    }

    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    private static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    private static String getMacAddress() {
        String strMacAddr = "";
        try {
            // 获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip)
                    .getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {
            return strMacAddr;
        }
        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            // 列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface
                    .getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {// 是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface
                        .nextElement();// 得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();// 得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            return ip;
        }
        return ip;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 获取网络连接状态
     *
     * @param context
     * @return
     */
    public static String getNetworkConnection(Context context) {
        String TAG = "Basic Network Demo";
        // Whether there is a Wi-Fi connection.
        boolean wifiConnected = false;
        // Whether there is a mobile connection.
        boolean mobileConnected = false;
        // BEGIN_INCLUDE(connect)
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                return "WIFI";
            } else if (mobileConnected) {
                return "DATA";
            }
        }
        return null;

    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isRoot() {

        boolean root = false;

        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        return root;
    }


    private static final int INTERNAL_STORAGE = 0;
    private static final int EXTERNAL_STORAGE = 1;

    /**
     * 判断SD是否挂载
     */
    public static boolean isSDCardMount() {
        return TextUtils.equals(Environment.getExternalStorageState(),
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机存储 ROM 信息
     * <p>
     * type： 用于区分内置存储于外置存储的方法
     * <p>
     * 内置SD卡 ：INTERNAL_STORAGE = 0;
     * <p>
     * 外置SD卡： EXTERNAL_STORAGE = 1;
     **/
    public static boolean getStorageInfo(Context context, int type) {

        String path = getStoragePath(context, type);
        /**
         * 无外置SD 卡判断
         * **/
        if (isSDCardMount() == false || TextUtils.isEmpty(path) || path == null)
            return false;
        else
            return true;
//
//        File file = new File(path);
//        StatFs statFs = new StatFs(file.getPath());
//        String stotageInfo;
//
//        long blockCount = statFs.getBlockCountLong();
//        long bloackSize = statFs.getBlockSizeLong();
//        long totalSpace = bloackSize * blockCount;
//
//        long availableBlocks = statFs.getAvailableBlocksLong();
//        long availableSpace = availableBlocks * bloackSize;
//
//        stotageInfo = "可用/总共："
//                + Formatter.formatFileSize(context, availableSpace) + "/"
//                + Formatter.formatFileSize(context, totalSpace);
//
//        return stotageInfo;

    }

    /**
     * 使用反射方法 获取手机存储路径
     **/
    public static String getStoragePath(Context context, int type) {

        StorageManager sm = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        try {
            Method getPathsMethod = sm.getClass().getMethod("getVolumePaths",
                    String.class);
            String[] path = (String[]) getPathsMethod.invoke(sm, Object.class);

            switch (type) {
                case INTERNAL_STORAGE:
                    return path[type];
                case EXTERNAL_STORAGE:
                    if (path.length > 1) {
                        return path[type];
                    } else {
                        return null;
                    }

                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 手机 可用 RAM
     */
    public static String getAvailableRAM(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.availMem;

        return Formatter.formatFileSize(context, size);
    }

    /**
     * 获取 手机 RAM 信息 方法 一
     */
    public static String getTotalRAM(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.totalMem;

        return Formatter.formatFileSize(context, size);
    }

    /**
     * 获取手机内部可用存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public static String getAvailableInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long availableBlocksLong = statFs.getAvailableBlocksLong();
        long blockSizeLong = statFs.getBlockSizeLong();
        return Formatter.formatFileSize(context, availableBlocksLong
                * blockSizeLong);
    }

    /**
     * 获取手机内部存储空间
     *
     * @param context
     * @return 以M, G为单位的容量
     */
    public static String getTotalInternalMemorySize(Context context) {
        File file = Environment.getDataDirectory();
        StatFs statFs = new StatFs(file.getPath());
        long blockSizeLong = statFs.getBlockSizeLong();
        long blockCountLong = statFs.getBlockCountLong();
        long size = blockCountLong * blockSizeLong;
        return Formatter.formatFileSize(context, size);
    }

    /**
     * 获取虚拟按键的高度
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight - contentHeight;
    }

    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度
     */
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获得屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static void displayItemNum(final BottomNavigationView mBottomNavigationView, final int mPosition, final int mCount) {
        try {
            final int maxSize = mBottomNavigationView.getItemIconSize();
            if (mPosition > maxSize || mPosition < 0) {
                return;
            }
            //Acquisition child View BottomNavigationMenuView Objects
            final BottomNavigationMenuView menuView = (BottomNavigationMenuView) mBottomNavigationView.getChildAt(0);
            final View mTab = menuView.getChildAt(mPosition);
            final BottomNavigationItemView itemView = (BottomNavigationItemView) mTab;
            View mBadge = itemView.getChildAt(3);
            if (mBadge == null) {
                //避免重复添加创建
                mBadge = LayoutInflater.from(mBottomNavigationView.getContext()).inflate(R.layout.bottom_bar_item_badge, menuView, false);
                itemView.addView(mBadge);
            }
            final TextView mTv = mBadge.findViewById(R.id.tv_msg_count);
            if (mCount <= 0) {
                mTv.setVisibility(View.GONE);
                mTv.setText(String.format("%s", mCount));
            } else {
                mTv.setVisibility(View.VISIBLE);
                if (mCount > 99) {
                    mTv.setText(String.format("%s+", 99));
                } else {
                    mTv.setText(String.format("%s", mCount));
                }
            }

        } catch (Exception e) {
            Log.e("displayItemNum", e.getMessage());
            e.printStackTrace();
        }
    }


}
