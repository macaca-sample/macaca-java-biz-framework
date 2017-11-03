package macaca.java.biz;

import java.util.List;

/**
 * Create User:chenbaowan
 * Create Date:17/11/3
 * Description:软键盘的相关方法
 */
public class SoftKeyboardUtil {

    /**
     * 通过adb命令模拟用户输入
     *
     * @param content 要输入的内容
     * 注意：建议输入英文，输入中文时由于系统问题会导致输入无效
     */
    public static void inputText(String content){

        if (content == null || content.equals("")){

            //输入内容为空，直接return，不作处理
            return;
        }
        BaseUtils.exec2("adb shell input text " + content);
    }

    /**
     *  通过adb命令获取输入软键盘的状态
     *  1、mInputShown=true 则判断软键盘弹出
     *  2、mInputShown=false 则判断软键盘隐藏
     *  3、执行命令失败的情况下，默认返回false
     *
     * @return boolean 软键盘显示：true，软键盘隐藏：false
     */
    public static boolean isSoftKeyboardDisplay(){

        List<String> result = BaseUtils.exec2("adb shell dumpsys input_method | grep mInputShown=");
        if (result == null || result.size() == 0){
            //执行命令失败，获取不到软键盘的输入状态关键字
            return false;
        }else {
            for (String line : result){
                if(line.contains("mInputShown=true")){
                    //执行结果包含"mInputShown=true"，则认为软键盘弹出
                    return true;
                }
            }
            return false;
        }
    }
}
