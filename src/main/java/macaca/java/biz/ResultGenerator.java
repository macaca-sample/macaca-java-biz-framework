package macaca.java.biz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultGenerator {

	public static final String NAME = "result.log";
	public static final String CUSTOM_LOG = "custom.log";
	public static final String SEPARATOR = "|";
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static void write2File(String name, boolean append, String content){
		System.out.println(content);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(name, append);
			fos.write(content.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}finally{
			if(fos != null){
//				CommonUtils.closeQuietly(fos);
			}
		}
	}

	/**
	 * 清除log数据,包含result.log custom.log
	 */
	public static void clearOldData() {
		File resultFile = new File(NAME);
		if (resultFile.exists() && resultFile.isFile()) {
			resultFile.delete();
		}

		File customLogFile = new File(CUSTOM_LOG);
		if (customLogFile.exists() && customLogFile.isFile()) {
			customLogFile.delete();
		}
	}

	/**
	 * 成功时记录，desc是预留参数，目前随便填什么都可以
	 *
	 * @param action, 当前操作
	 * @param desc， 描述，预留字段，可以设为空字符串
	 */
	public static void success(String action, String desc){
		//为了保证customLog的完整性，把resultLog的输出也打印到customLog上
		write2File(NAME, true,  getStringDate() + "," + action+SEPARATOR+true+SEPARATOR+desc+LINE_SEPARATOR);
		write2File(CUSTOM_LOG, true,  getStringDate() + "," + action+SEPARATOR+true+SEPARATOR+desc+LINE_SEPARATOR);

	}

	/**
	 * 页面加载成功日志记录
	 * @param page 当前page对象
	 */
	public static void loadPageSucc(BasePage page) {
		success(page.pageDesc,"页面加载成功");
	}

	/**
	 * 页面加载失败时生成日志记录
	 * @param page 当前页面对象
	 */
	public static void loadPageFail(BasePage page) {
		fail(page.pageDesc,"页面加载失败",BaseErrorType.PAGE_NOT_LOAD);
	}

	/**
	 * 控件查找失败时生成日志记录
	 * @param targetElement 目标UI控件
	 */
	public static void findElementFail(CommonUIBean targetElement){
		// 因为控件没查到不一定意味着这个case是失败的，但如果用fail就会导致整个case标记为失败，因此这里调用success方法
		customLog(targetElement.elementDesc, "控件不存在");
	}

	/**
	 * 进入catch Exception方法时触发日志记录
	 */
	public static void catchedException(Exception e) {
//		e.printStackTrace();
		customLog("异常捕捉", e.getMessage());
	}
	//action|false|desc|type(code)  --->   action|false|type|desc
	/**
	 *
	 * @param action 关键动作描述
	 * @param desc	错误描述
	 * @param type	错误类型, 必须选择一个错误类型
	 */
	public static void fail(String action,  String desc, BaseErrorType type){
		write2File(NAME, true,  getStringDate() + "," + action + SEPARATOR + false + SEPARATOR + desc + SEPARATOR + type.getId() + LINE_SEPARATOR);
		write2File(CUSTOM_LOG, true,  getStringDate() + "," + action+SEPARATOR + false + SEPARATOR + desc + SEPARATOR + type.getId() + LINE_SEPARATOR);
	}

	/**
	 * 自定义日志输出
	 * @param action 动作信息
	 * @param desc 自定义描述信息
	 */
	public static void customLog(String action,String desc){
		write2File(CUSTOM_LOG, true, getStringDate() + "," + action+SEPARATOR+desc+LINE_SEPARATOR);
	}
	public static void main(String[] args) {
//		success("登录成功", "");
	}


	/**
	 *
	 * @return 返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}

}
