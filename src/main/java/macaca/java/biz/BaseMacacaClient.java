package macaca.java.biz;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

import com.alibaba.fastjson.JSONObject;

import macaca.client.MacacaClient;
import macaca.client.common.GetElementWay;

/**
 * 自定义的MacacaClient基类，用于在MacacaClient基础上封装自己的处理
 * @author xiulian.yin
 *
 */
public class BaseMacacaClient extends MacacaClient{

	// 定义平台类型枚举
	public enum PlatformType {
		IOS,
		ANDROID
	}

	// 当前运行的平台
	public PlatformType curPlatform;

	// 用于diff两张图片是否相同
	public static final String  BEFORE_IMAGE_NAME = "before.png";
	public static final String  AFTER_IMAGE_NAME = "after.png";

	public PlatformType getCurPlatform() {
		return curPlatform;
	}

	public void setCurPlatform(PlatformType curPlatform) {
		this.curPlatform = curPlatform;
	}


	/**
	 * 查找控件，当同样的控件有多个时，返回第一个
	 * @param bean
	 * @throws Exception
	 */
	public void findElement(CommonUIBean bean) throws Exception {

		if(curPlatform == PlatformType.IOS ) {
			// 当前为iOS平台
			getElement(bean.getIosBy(), bean.getIosValue());
		}
		else
		{
			getElement(bean.getAndroidBy(), bean.getAndroidValue());
		}
	}

	/**
	 * 根据索引获取控件，当同样的控件可能存在多个时，查询返回的是一个数组，此时通过传入目标控件的索引获取指定控件
	 * @param bean 要查找的控件
	 * @param index 目标控件index
	 * @throws Exception
	 */
	public void findElementByIndex(CommonUIBean bean ,int index) throws Exception{
		if(curPlatform == PlatformType.IOS ) {
			// 当前为iOS平台
			getElement(bean.getIosBy(),bean.getIosValue() , index);
		}
		else
		{
			// 当前为安卓平台
			getElement(bean.getAndroidBy(), bean.getAndroidValue(),index);
		}

	}

	/**
	 * 当一类控件存在多个时，返回共有多少个该控件
	 * @param bean 要查找的控件
	 * @return 控件数组个数
	 * @throws Exception
	 */
	public int  countOfElment(CommonUIBean bean) throws Exception {

		if(curPlatform == PlatformType.IOS ) {
			// 当前为iOS平台
			return countOfElements(bean.getIosBy(), bean.getIosValue());
		}
		else
		{
			// 当前为安卓平台
			return countOfElements(bean.getAndroidBy(), bean.getAndroidValue());
		}
	}

	/**
	 * 循环查找某个element，直到查找完毕
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public void waitForElement(CommonUIBean bean) throws Exception {

		if(curPlatform == PlatformType.IOS ) {
			waitForElement(bean.getIosBy(), bean.getIosValue());
		}
		else
		{
			waitForElement(bean.getAndroidBy(), bean.getAndroidValue());
		}
	}

	/**
	 * 循环查找某个element,直到查找完毕，适用于当前控件存在多个时，按照索引查找
	 * @param bean  要查找的控件
	 * @param index 目标控件index
	 * @throws Exception
	 */
	public void waitForElement(CommonUIBean bean,int index) throws Exception {
		if(curPlatform == PlatformType.IOS ) {
			waitForElement(bean.getIosBy(), bean.getIosValue(),index);
		}
		else
		{
			waitForElement(bean.getAndroidBy(), bean.getAndroidValue(),index);
		}
	}
	/**
	 * 判断某个控件是否存在
	 * @param bean
	 * @return
	 */
	public boolean isElementExist(CommonUIBean bean) {
		try {

			if(curPlatform == PlatformType.IOS ) {
				return isElementExist(bean.getIosBy(), bean.getIosValue());
			}
			else
			{
				return isElementExist(bean.getAndroidBy(),bean.getAndroidValue());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			ResultGenerator.catchedException(e);
			return false;
		}

	}

	/**
	 * 判断某个控件是否存在
	 * @param bean
	 * @return
	 */
	public boolean isElementExist(CommonUIBean bean,int index) {
		try {

			if(curPlatform == PlatformType.IOS ) {
				return isElementExist(bean.getIosBy(), bean.getIosValue(),index);
			}
			else
			{
				return isElementExist(bean.getAndroidBy(),bean.getAndroidValue(),index);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			ResultGenerator.catchedException(e);
			return false;
		}

	}

	/**
	 * 是否存在目标控件，如果当前没有该控件，在给定时间内循环查询，查询间隔以及时长通过setWaitElementTimeInterval,setWaitElementTimeout可设置
	 * @param bean
	 * @return
	 */
	public boolean isElementExistAfterWaiting(CommonUIBean bean) {
		try {
			if(this.isElementExist(bean)) {
				return true;
			} else {
				waitForElement(bean);
				return this.isElementExist(bean);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ResultGenerator.catchedException(e);
			return false;
		}

	}


	/**
	 * 是否存在目标控件，如果当前没有该控件，在给定时间内循环查询，查询间隔以及时长通过setWaitElementTimeInterval,setWaitElementTimeout可设置
	 * @param bean
	 * @return
	 */
	public boolean isElementExistAfterWaiting(CommonUIBean bean, int index) {
		try {
			if(this.isElementExist(bean, index)) {
				return true;
			} else {
				waitForElement(bean,index);
				return this.isElementExist(bean,index);
			}
		} catch (Exception e) {
			// TODO: handle exception
			ResultGenerator.catchedException(e);
			return false;
		}

	}


	/**
	 * 自定义返回事件，对于iOS,通过右滑手势实现，对于Android，需要另外处理
	 * @throws Exception
	 */
	public void customBack() throws Exception {
		if(curPlatform == PlatformType.IOS) {
			// iOS返回，通过模拟右滑返回实现
			try {
				super
				.swipe(0, 100, 300, 100, 500);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				ResultGenerator.catchedException(e);
			}
		} else {
			super.back();
		}
	}

	/**
	 * 点击某一个控件，执行操作为 查找控件-点击控件
	 * @param bean 要点击的控件
	 */
	public void onclickBean(CommonUIBean bean) {
		try {
			if (isElementExistAfterWaiting(bean)) {
				findElement(bean);
				click();
				ResultGenerator.success("点击:"+bean.elementDesc,"" );
			} else {
				ResultGenerator.findElementFail(bean);
			}

		} catch (Exception e) {
			// TODO: handle exception
			ResultGenerator.catchedException(e);
		}
	}

	/**
	 * 点击某一个控件，执行操作为 查找控件-点击控件
	 * @param bean 目标控件
	 * @param index 目标控件索引
	 */
	public void onclickBeanAtIndex(CommonUIBean bean, int index) {
		try {
			if (isElementExistAfterWaiting(bean,index)) {
				findElementByIndex(bean, index);
				click();
				ResultGenerator.success("点击:"+bean.elementDesc +"["+ index+"]" ,"");
			} else {
				ResultGenerator.findElementFail(bean);
			}

		} catch (Exception e) {
			// TODO: handle exception
			ResultGenerator.catchedException(e);
		}
	}
	/**
	 * 实现简单的输入
	 * @param bean 要输入文本的控件
	 * @param input 要输入的关键字
	 */
    public void inputBean(CommonUIBean bean,String input){
    	try {
    		if (isElementExistAfterWaiting(bean)) {
				findElement(bean);
				sendKeys(input);
				ResultGenerator.success("输入: "+bean.elementDesc+";value:"+input,"");
			} else {
				ResultGenerator.findElementFail(bean);
			}

		} catch (Exception e) {
			// TODO: handle exception
			ResultGenerator.catchedException(e);
		}
    }


    /**
   	 * 滑动当前页面到指定控件
   	 * Support: Android iOS Web(WebView)
   	 * @param wayToFind
   	 * 			目标控件查找方式
   	 * @param value
   	 * 			目标控件查找值
   	 * @return
   	 * 			true:找到控件，并完成滑动
   	 * 		    false:控件不存在，滑到底部依然没有查到
   	 */
   	public boolean scrollToElement (GetElementWay wayToFind, String value){

   		JSONObject windowSize;
   		try {
   			windowSize = getWindowSize();
   			int windowWidth = windowSize.getIntValue("width");
   			int windowHeight = windowSize.getIntValue("height");

   			int startX = windowWidth-20;
   			int endX = startX;
   			int startY = windowHeight*3/5;
   			int endY = windowHeight*2/5;

   			String beforeScreenShot = null ;
   			String afterScreenShot = null;
   			String beforePng = "before.png";
   			String afterPng = "after.png";
   			while (!isElementExist(wayToFind, value)) {

   				File shotOne = new File(beforePng);
   				File shotTwo = new File(afterPng);
   				beforeScreenShot = getFileMD5(shotOne);
   				afterScreenShot = getFileMD5(shotTwo);
   				if (beforeScreenShot != null &&
   					beforeScreenShot.length() > 0) {
   					if (beforeScreenShot.equals(afterScreenShot)) {
   						// the same screen image ,it means current view has scroll to bottom
   						System.out.println("the given element does not exist");
   						deleteDiffImages();
   						return false;
   					}
   				}

   				saveScreenshot(beforePng);
   				System.out.println("scroll: ("+startX+","+startY+","+endX+","+endY+")");
   				swipe(startX, startY, endX, endY, 50);
   				Thread.sleep(1000);
   				saveScreenshot(afterPng);

   			}


   			deleteDiffImages();

   			return true;

   		} catch (Exception e) {
   			// TODO Auto-generated catch block
   			deleteDiffImages();
   			e.printStackTrace();
   		}

   		deleteDiffImages();
   		return false;

   	}

   	private void deleteDiffImages() {
   		try {
   			// 如果存在图片，则删除,防止污染图片
   			File shotOne = new File(BEFORE_IMAGE_NAME);
   			File shotTwo = new File(AFTER_IMAGE_NAME);
   			if (shotOne.exists()) {
   				shotOne.delete();
   			}

   			if (shotTwo.exists()) {
   				shotTwo.delete();
   			}
   		} catch (Exception e) {
   			// TODO: handle exception
   			e.printStackTrace();
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
   	 }




