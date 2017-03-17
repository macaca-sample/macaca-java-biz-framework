# 1.0.4 / 2017-03-17
### 增加一个API
   // BaseMacacaClient.java
	* scrollToElementCustom (GetElementWay wayToFind, String value, boolean isHorizontal, int stepSize) // 滑动当前页面到指定控件(支持横屏滑动和竖屏滑动)
	
### 修改一个API
   // BaseMacacaClient.java
       * scrollToElement (GetElementWay wayToFind, String value) //注释掉了之前的方法体，在里面调用了scrollToElementCustom (GetElementWay wayToFind, String value, boolean isHorizontal, int stepSize)实现滑动当前页面到指定控件
      
各API详细使用规则可参考注释文档

# 1.0.1 / 2017-02-28
### 增加部分API
	
   // BaseMacacaClient.java
	* scrollToBottom() // 滑动视图到底部
	* scrollToTop() // 滑动视图到顶部
	* switchFromNativeToWebView() // 切换上下文到webview（在对webview操作前执行）
	* switchFromeWebviewToNative（） // 切换上下文到native
	
#1.0 / 2017-01-22
### 增加App操作相关的API,如启动app,清理app，杀死app(目前只提供针对Android平台的操作)

#### 在server启动的情况下，可使用如下API

 	// BaseMacacaClient.java
 	* startApp()
	* clearApp()
	* forceStopApp()

#### 在server未启动的情况下，无法获取正在运行的app信息，如果要操作，需要手动传入，相关API如下
 	// BaseUtils.java
 	* startApp(String deviceId,String packageName,String activityName)
 	* clearApp(String deviceId,String packageName)
 	* forceStopApp(String deviceId,String packageName)

各API详细使用规则可参考注释文档

# 0.0.9 / 2016-01-10
### 1. 增加部分对控件直接操作的API，可以直接通过driver操作控件

* clearText(CommonUIBean bean)
* getText(CommonUIBean bean)
* getProperty(CommonUIBean bean,String name)
* getRect(CommonUIBean bean)
* isDisplayed(CommonUIBean bean)
