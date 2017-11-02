package macaca.java.biz;



/**
 * 通用Page基类，用于页面的通用处理，比如页面返回以及其他
 * @author xiulian.yin
 *
 */
public class BasePage  {

	/**
	 * antClient driver
	 */
	public BaseMacacaClient driver;

	/**
	 * page desc 页面描述，日志输出时会用到，用于对当前页面一个可辨识的描述信息
	 */
	public String pageDesc;


	public BaseMacacaClient getDriver() {
		return driver;
	}

	public void setDriver(BaseMacacaClient driver) {
		this.driver = driver;
	}

	/**
	 * 构造函数 使用此方法只指定了页面描述，如果对页面进行操作，还需手动指定driver对象
	 * @param pageDesc 当前页面描述
	 */
	public BasePage(String pageDesc) {
		this.pageDesc = pageDesc;
	}

	/**
	 * 构造函数
	 * @param pageDesc 页面描述信息
	 * @param driver 在页面上的各种操作需要利用driver进行，需要将外部的driver指给当前Page
	 */
	public BasePage(String pageDesc,BaseMacacaClient driver){
		this.pageDesc = pageDesc;
		this.driver = driver;
	}


	/**
	 * 判断当前页面是否已经加载(判断方式为给出一个唯一标识的控件，通过判断该控件是否已经显示来确认页面是否已经加载)
	 * @param bean 用于唯一标识当前页面的控件
	 * @return true:已经显示， false:未显示
	 */
	public boolean hasPageShown(CommonUIBean bean)  {
		boolean flag = driver.isElementExistAfterWaiting(bean);
		return flag;
	}


}
