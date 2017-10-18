# 1.1.4 / 2017-07-14

### 增加一个个API

   // BaseUtils.java
	* uninstallApp (String deviceType,String deviceId,String app) // 删除app Support: Android ios
各API详细使用规则可参考注释文档

# 1.1 / 2017-05-04

### 修改两个API

   // BaseMacacaClient.java
	* scrollToElementCustom (GetElementWay wayToFind, String value, int stepSize) // 去掉对横屏or竖屏的判断，底层wd.java已经自适应横屏or竖屏
	* scrollToBottomOrTop (boolean isToBottom) // 去掉对横屏or竖屏的判断，底层wd.java已经自适应横屏or竖屏
	* public void scrollToTop () // 调用更新后的scrollToBottomOrTop方法
	* scrollToBottom() // 调用更新后的scrollToBottomOrTop方法
	* scrollToElement (GetElementWay wayToFind, String value) // 调用更新后的scrollToElementCustom方法
各API详细使用规则可参考注释文档

# 1.0.9 / 2017-04-11

### 增加两个API

   // BaseUtils.java
	* deviceInstaller (String deviceType,String deviceId,String packagePath) // 安装app Support: Android ios
	* launchApp(String deviceType,String deviceId,String packageName,String activityName,String bundleId ) // 启动app Support: Android ios

各API详细使用规则可参考注释文档

# 1.0.5 / 2017-03-22

### 增加一个API

   // BaseMacacaClient.java
	* scrollToBottomOrTop (boolean isHorizontal,boolean isToBottom) // 横屏或竖屏滑动到最底部或最顶部

### 修改两个API

   // BaseMacacaClient.java
      * scrollToBottom () //注释掉了之前的方法体，在里面调用了scrollToBottomOrTop (boolean isHorizontal,boolean isToBottom)实现滑动到最底部
      * scrollToTop () //注释掉了之前的方法体，在里面调用了scrollToBottomOrTop (boolean isHorizontal,boolean isToBottom)实现滑动到最顶部

各API详细使用规则可参考注释文档

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
