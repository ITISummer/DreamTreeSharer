package com.ITIS.DreamTreeSharer.utils.sendSMS;
import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;

/**
 * @ClassName SmsSDKDemo1
 * @Author LCX
 * @Date 2021 2021-04-26 12:17 p.m.
 * @Version 1.0
 * [模板短信业务接口](https://doc.yuntongxun.com/p/5a533de33b8496dd00dce07c)
 **/
public class SmsSDKDemo1 {
 public static boolean sendSms(String phone, String code) {
   //生产环境请求地址：app.cloopen.com
    String serverIp = "app.cloopen.com";
    //请求端口
    String serverPort = "8883";
    //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
    String accountSId = "8a216da878005a8001789fabde55395e";
    String accountToken = "3676b128f53c4340b756802ac99958df";
    //请使用管理控制台中已创建应用的APPID
    String appId = "8a216da878005a8001789fabe0963965";
    CCPRestSmsSDK sdk = new CCPRestSmsSDK();
    sdk.init(serverIp, serverPort);
    sdk.setAccount(accountSId, accountToken);
    sdk.setAppId(appId);
    sdk.setBodyType(BodyType.Type_JSON);
    // 测试的 templateId 是 1
    String templateId= "1";
    // 用于替换模板中的{序号}
    // 短信验证码
    String[] datas = {code,"1"};
    // 正式发送
    HashMap<String, Object> result = sdk.sendTemplateSMS(phone,templateId,datas);
    if("000000".equals(result.get("statusCode"))){
        //正常返回输出data包体信息（map）
        System.out.println("短信发送成功！");
        return true;
    }else{
    	//异常返回输出错误码和错误信息
    	System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
    	return false;
    }
 }
}
