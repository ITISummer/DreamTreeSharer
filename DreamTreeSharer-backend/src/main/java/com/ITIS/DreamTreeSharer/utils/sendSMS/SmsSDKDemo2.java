package com.ITIS.DreamTreeSharer.utils.sendSMS;

import com.ITIS.DreamTreeSharer.utils.SslUtil;
import com.ITIS.DreamTreeSharer.utils.sendSMS.KewailSDK.SmsSingleSender;
import com.ITIS.DreamTreeSharer.utils.sendSMS.KewailSDK.SmsSingleSenderResult;
import org.springframework.stereotype.Component;

@Component
public class SmsSDKDemo2 {

/*
https://www.kewail.com/plan_plan2.html?to=sms
注： 1、"msg"字段需要匹配审核通过的模板内容
如果您的模板是"尊敬的客户:你的验证码是{1}"，则"msg"字段可赋值为："尊敬的客户:你的验证码是xxxx"。（其中"xxxx"为下发的验证码） 如果您有多个短信签名，请将需要的短信签名放在短信内容前面 例如您有"【Kewail科技】"，"【Kewail】"两个签名，但是想以"【Kewail】"签名发送短信， 则"msg"字段可赋值为："【Kewail】尊敬的客户:你的验证码是xxxx"。（其中"xxxx"为下发的验证码）
2、"extend"字段的配置请联系Kewail短信技术支持
3、sendisms接口，"tel"字段为国际电话号码通用格式，如："+8613788888888"
4、"sig"字段根据公式sha256(secretkey=$accesskey&random=$random&time=$time&mobile=$mobile)生成。
 */
    public boolean sendSms(String phoneNumber, String verifyCode) {
        boolean flag = false;
        try {
            //请根据实际 accesskey 和 secretKey，保密，不能外传
            String accessKey = "5d7db0a987b65f1f37d68fb3";
            String secretKey ="1ea9857c47ff4eae85ddf78488e660e5";
            //初始化单发
            SmsSingleSender singleSender = new SmsSingleSender(accessKey, secretKey);
            //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
            SmsSingleSenderResult singleSenderResult;
            SslUtil.ignoreSsl();
            singleSenderResult = singleSender.send(0, "86", phoneNumber, "【ITIS】尊敬的用户：树蛙科技提醒您：您的验证码：" + verifyCode + "，工作人员不会索取，请勿泄漏。", "", "");
            System.out.println(singleSenderResult);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }//try...catch
        return flag;
    }//end sendSms()
}
