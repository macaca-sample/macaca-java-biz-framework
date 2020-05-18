package macaca.java.biz;

import macaca.client.commands.Element;
import macaca.client.common.GetElementWay;
import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * @author shixing & niaoshuiai
 * @program: biz
 * @description: web common function
 * @create: 2020-05-18 16:41
 **/
public class WebCommonPage extends BasePage {
    public WebCommonPage(String pageDesc) {
        super(pageDesc);
    }
    /**
     * 获取 List<Element>
     * @param bean
     * @return
     * @throws Exception
     */
    public List<Element> getElements(CommonUIBean bean) throws Exception {
        driver.sleep(1000);
        class FindElements {
            int waitTime = 15;
            List<Element> findAction(GetElementWay getElementWay, String value) throws Exception {
                List<Element> els = null;
                while (waitTime > 0) {
                    switch (getElementWay) {
                        case ID:
                            els = driver.elementsById(value);
                            break;
                        case CSS:
                            els = driver.elementsByCss(value);
                            break;
                        case NAME:
                            els = driver.elementsByName(value);
                            break;
                        case XPATH:
                            els = driver.elementsByXPath(value);
                            break;
                        case CLASS_NAME:
                            els = driver.elementsByClassName(value);
                            break;
                        case LINK_TEXT:
                            els = driver.elementsByLinkText(value);
                            break;
                        case PARTIAL_LINK_TEXT:
                            els = driver.elementsByPartialLinkText(value);
                            break;
                        case TAG_NAME:
                            els = driver.elementsByTagName(value);
                            break;
                        default:

                            break;
                    }

                    if (els == null) {
                        driver.sleep(1000);
                        waitTime--;
                    } else {
                        return els;
                    }
                }
                if (driver.curPlatform == BaseMacacaClient.PlatformType.ANDROID) {
                    ResultGenerator.customLog(bean.elementDesc, "没有找到该元素，元素 : " + bean.getAndroidValue());
                } else {
                    ResultGenerator.customLog(bean.elementDesc, "没有找到该元素，元素值: " + bean.getIosValue());
                }
                return null;
            }
        }
        FindElements findElements = new FindElements();

        //      根据对应类型，调用对应的方法
        return driver.curPlatform == BaseMacacaClient.PlatformType.ANDROID ? findElements.findAction(bean.getAndroidBy(), bean.getAndroidValue())
                :
                findElements.findAction(bean.getIosBy(), bean.getIosValue());
    }


    /**
     * 复选框 选择多个
     * @param checkboxByListBean
     * @throws Exception
     */
    public void checkboxSelect(CommonUIBean checkboxByListBean) throws Exception {
        driver.sleep(1000);
        List<Element> els = getElements(checkboxByListBean);
        int count = els.size();
        int random = randomInt(count - 1);
        for (int i = 0; i <= random; i++) {
            driver.onclickBeanAtIndex(checkboxByListBean, i);
            driver.sleep(800);
        }
        List<Element> newEls = getElements(checkboxByListBean);
        // 如果点击失败 ，则再次调用
        if (count == newEls.size()) {
            checkboxSelect(checkboxByListBean);
        }
    }

    /**
     * Radio 随机选择一个
     * @param radioByListBean
     * @throws Exception
     */
    public void radioSelect(CommonUIBean radioByListBean) throws Exception {
        //随机选择一个正确答案
        int i = getElements(radioByListBean).size() - 1;
        if (i <= 0) {
            //集合中包含单一元素处理
            driver.onclickBeanAtIndex(radioByListBean, randomInt(i + 1));
        }
        driver.onclickBeanAtIndex(radioByListBean, randomInt(i));
        driver.sleep(800);
    }


    /**
     * 拖动页面到底部
     *
     * @throws Exception
     */
    public void dragBottom() throws Exception {
        String js = "document.documentElement.scrollTop=100000";
        driver.execute(js);
    }

    /**
     * 拖动页面到底部
     *
     * @throws Exception
     */
    public void dragTop() throws Exception {
        String js = "document.documentElement.scrollTop=0";
        driver.execute(js);
    }

    /**
     * 拖动页面向右滑动,使用按键的方式。（点击，选中元素后，发送键盘指令进行滚动）
     * @param count 滚动次数
     * @param bean 以哪个元素为基础进行运动
     * @throws Exception
     */
    public void dragRight(int count, CommonUIBean bean) throws Exception {
        //首先定位到 页面的div 然后点击向右的方向按钮
        driver.onclickBean(bean);
        Robot robot = new Robot();
        driver.sleep(500);
        for (int i = 0; i <= count; i++) {
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
        }
    }
    /**
     *
     * @param uploadBtnBean 上传控件CSS
     * @param filePath    上传文件名称
     * @param waitTime  上传完毕后等待时间。
     * @throws Exception
     */
    public void uploadFile(CommonUIBean uploadBtnBean, String filePath, int waitTime) throws Exception {
        ResultGenerator.customLog(filePath, "上传文件路径");
        driver.onclickBean(uploadBtnBean);
        uploadFileByRobot(filePath);
        driver.sleep(waitTime);
    }

    /**
     * 非input类型上传控件:robot方式
     * @param filePath
     */
    private static void uploadFileByRobot(String filePath) {
        //传入文件路径
        try {
            Thread.sleep(1000);
            // 指定图片的路径 ：filepath
            StringSelection sel = new StringSelection(filePath);
            // 把图片文件路径复制到剪贴板
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
            ResultGenerator.customLog(sel.toString(), "selection");
            // 新建一个Robot类的对象
            Robot robot = new Robot();
            Thread.sleep(1000);
            // 按下回车
            robot.keyPress(KeyEvent.VK_ENTER);
            // 释放回车
            robot.keyRelease(KeyEvent.VK_ENTER);
            // 按下 CTRL+V
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            // 释放 CTRL+V
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            Thread.sleep(1000);
            // 点击回车 Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            ResultGenerator.customLog("执行上传文件通过", "文件上传结果");
        } catch (Exception e) {
            ResultGenerator.customLog("执行上传文件失败", "文件上传结果");
        }
    }



    /**
     * 生成随机数
     * @param length
     * @return
     */
    private int randomInt(Integer length) {
        Random rm = new Random();
        if (length == 0) {
            return 0;
        } else {
            int i = rm.nextInt(length);
            return i;
        }
    }
}
