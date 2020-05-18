package macaca.java.biz;

import java.util.List;

/**
 * @author shixing & niaoshuai
 * @program: biz
 * @description: App Common function
 * @create: 2020-05-18 16:40
 **/
public class AppCommonPage extends BasePage{
    private double appWidth;
    private double appHeight;

    public AppCommonPage(String pageDesc) {
        super(pageDesc);
    }

    public double getAppHeight() {
        return appHeight;
    }

    public void setAppHeight(double appHeight) {
        this.appHeight = appHeight;
    }

    public double getAppWidth() {
        return appWidth;
    }

    public void setAppWidth(double appWidth) {
        this.appWidth = appWidth;
    }

    @Override
    public BaseMacacaClient getDriver() {
        return super.getDriver();
    }

    @Override
    public void setDriver(BaseMacacaClient driver) {
        super.setDriver(driver);
        if (driver.curPlatform == BaseMacacaClient.PlatformType.ANDROID) {
            getPhoneSize();
        }
    }

    /**
     * 通过adb命令获取耍手机的分辨率
     */
    public void getPhoneSize() {
//        Physical size: 1080x1920
        String adb = "adb shell wm size";
        List<String> logs = BaseUtils.exec2(adb);
        String falg = "Physical size: ";
        for(String log : logs){
            int index = log.indexOf(falg);
            if(index >= 0){
                String hvga = log.substring(falg.length());
                String hvgas[] = hvga.split("x");
                this.setAppWidth(Double.parseDouble(hvgas[0]));
                this.setAppHeight(Double.parseDouble(hvgas[1]));
            }
        }
    }

    /**
     * 使用坐标占比 通过adb进行拖动
     * @param starRatioX
     * @param starRatioY
     * @param fromRatioX
     * @param fromRatioY
     * @throws Exception
     */
    public void dragByRatio(double starRatioX, double starRatioY, double fromRatioX, double fromRatioY ) throws Exception {
        if (driver.curPlatform == BaseMacacaClient.PlatformType.ANDROID) {
            double x =  this.getAppWidth() * starRatioX;
            double y =  this.getAppHeight() * starRatioY;
            double fX =  this.getAppWidth() * fromRatioX;
            double fY =  this.getAppHeight() * fromRatioY;
            // adb 拖动 命令
            String cmdString = "adb shell input swipe"+ " " + x + " "+ y + " " + fX + " "+ fY;
            BaseUtils.exec2(cmdString);
            driver.sleep(500);
        }
    }

}
