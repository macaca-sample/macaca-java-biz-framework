package macaca.java.biz;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class BaseUtils {

	 /**
     * 执行系统命令
     *
     * @param cmd
     * @return
     */
    public static List<String> exec2(String cmd) {
    	ResultGenerator.customLog("执行系统命令", cmd);
        Runtime run = Runtime.getRuntime();//返回与当前 Java 应用程序相关的运行时对象
        List<String> lines = new ArrayList<String>();
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = inBr.readLine()) != null) {
                lines.add(line);
            }
            inBr.close();
            in.close();
        } catch (Exception e) {
            ResultGenerator.customLog("命令执行异常", e.getMessage());
        }
        return lines;
    }

    /**
     * 安装app
     * Support: Android Only
     * @param deviceId 设备id,当存在多个设备时需指定设备id,如果只有一台设备，可以不必指定
     * @param apkPath 安装包路径
     */
    public static void installApp (String deviceId,String apkPath){
    	if (isStringNotNull(deviceId)) {
			exec2("adb -s "+ deviceId + "install " + apkPath);
		} else {
			exec2("adb install" + apkPath);
		}
    }
  	 /**
  	  * 启动app
  	  * Support: Android only
  	  * @param deviceId 设备id,当存在多个设备时需指定设备id,如果只有一台设备，可以不必指定
  	  * @param packageName 要启动app的packageName,与initDriver时设置的desiredCapabilities一致
  	  * @param activityName 要启动app的activityName,与initDriver时设置的desiredCapabilities一致
  	  */
  	 public static void startApp(String deviceId,String packageName,String activityName) {
  	    if (isStringNotNull(deviceId)) {
  			exec2("adb -s "+ deviceId + " shell am start -S -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000 -n "+packageName+"/"+activityName);
		} else {
			exec2("adb shell am start -S -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -f 0x10200000 -n "+packageName+"/"+activityName);
		}

  	 }

  	 /**
  	  * 清理app
  	  * Support: Android only
  	  * @param deviceId 设备id,当存在多个设备时需指定设备id,如果只有一台设备，可以不必指定
  	  * @param packageName 要启动app的packageName,与initDriver时设置的desiredCapabilities一致
  	  */
  	 public static void clearApp(String deviceId,String packageName) {

  		 if (isStringNotNull(deviceId)) {
  	    	exec2("adb -s " + deviceId + " shell pm clear "+packageName);
		}else {
			exec2("adb shell pm clear "+packageName);
		}

  	 }

  	 /**
  	  * 杀死app
  	  * Support: Android only
  	  * @param deviceId 设备id,当存在多个设备时需指定设备id,如果只有一台设备，可以不必指定
  	  * @param packageName 要启动app的packageName,与initDriver时设置的desiredCapabilities一致
  	  */
  	 public static void forceStopApp(String deviceId,String packageName) {

  		 if (isStringNotNull(deviceId)) {
  			exec2("adb -s "+ deviceId + " shell am force-stop "+packageName);
		} else {
			exec2("adb shell am force-stop "+packageName);
		}

  	 }

  	/**
 	 * 获取文件md5
 	 * @param file
 	 * @return
 	 */
 	 public static String getFileMD5(File file) {
 		 if (!file.isFile()) {
 		      return null;
 		    }
 		    MessageDigest digest = null;
 		    FileInputStream in=null;
 		    byte buffer[] = new byte[1024];
 		    int len;
 		    try {
 		      digest = MessageDigest.getInstance("MD5");
 		      in = new FileInputStream(file);
 		      while ((len = in.read(buffer, 0, 1024)) != -1) {
 		        digest.update(buffer, 0, len);
 		      }
 		      in.close();
 		    } catch (Exception e) {
 		      e.printStackTrace();
 		      return null;
 		    }
 		    BigInteger bigInt = new BigInteger(1, digest.digest());
 		    return bigInt.toString(16);
 	}


 	public static  boolean isStringNotNull(String str) {
 		if (str == null || str.length() == 0) {
			return false;
		}

 		return true;
 	}

}
