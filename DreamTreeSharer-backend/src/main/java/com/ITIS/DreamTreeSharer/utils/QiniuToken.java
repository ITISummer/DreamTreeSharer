package com.ITIS.DreamTreeSharer.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * [java调用qiniu七牛云空间](https://blog.csdn.net/visket2008/article/details/77164233)
 * [AccessKey = Access私钥
 * SecretKey = Secret私钥
 * bucket = 对象存储空间名
 * key = 你上传的文件名]
 * @ClassName 七牛云空间工具类
 * @Author LCX
 * @Date 2021 2021-04-16 3:53 p.m.
 * @Version 1.0
 **/
public class QiniuToken {
    /** Access 写自己的 */
    private static final String ACCESS_KEY = "7viju8jm47T3cIZFLPXPJpj28OUpjQzFPS5Fw80p";

    /** Secret 写自己的 */
    private static final String SECRET_KEY = "Q59neB4gG-qeciUTstqPUJXMLUiVKUh-mfX8EPbe";

    /** 基础图片空间 写自己的 */
    public static final String CLOUD_BUCKET = "itisummer-huanan-bucket";
    /** 基础空间域名 写自己的 */
    public static final String CLOUD_URL = "qrne6et6u.hn-bkt.clouddn.com";

//    /** 产品空间  写自己的 */
//    public static final String PRODUCT_BUCKET = "cloud-product";
//    /** 产品空间域名 写自己的 */
//    public static final String PRODUCT_URL = "xxxx.bkt.clouddn.com";

    /**
     * Description:简单token类型返回封装
     * @param key 文件名
     * @return String
     * @author around
     * @Date 2021年4月16日16点13分
     */
    public static String getBaseToken(String bucket, String key) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(bucket, key);
    }

    /**
     * Description:自定义token返回结果
     * @param key 文件名
     * @return
     * @author LCX
     * @Date 2021年4月16日16点13分
     */
    public static String getCustomToken(String bucket, String key) {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"com\":\"$(x:com)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        return auth.uploadToken(bucket, key, expireSeconds, putPolicy);
    }

    /**
     * Description:删除指定文件
     * @param key 文件名
     * @return boolean true:成功
     * @author LCX
     * @Date 2021年4月16日16点13分
     */
    public static boolean remove(String bucket, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
        return true;
    }

    /**
     * Description:批量删除文件
     * @param keyList 文件名数组
     * @return boolean
     * @author LCX
     * @Date 2021年4月16日16点13分
     */
    public static boolean remove(String bucket, String[] keyList) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            //单次批量请求的文件数量不得超过1000
            //String[] keyList = new String[]{"qiniu.jpg","qiniu.mp4","qiniu.png",};
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                System.out.print(key + "\t");
                if (status.code == 200) {
                    System.out.println("delete success");
                } else {
                    System.out.println(status.data.error);
                }
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        //7viju8jm47T3cIZFLPXPJpj28OUpjQzFPS5Fw80p:O3ujY6Ocqj46oRDSqldNq0cDnRQ=:eyJzY29wZSI6Iml0aXN1bW1lci1odWFuYW4tYnVja2V0OnRlc3QuanBnIiwicmV0dXJuQm9keSI6IntcImtleVwiOlwiJChrZXkpXCIsXCJjb21cIjpcIiQoeDpjb20pXCIsXCJoYXNoXCI6XCIkKGV0YWcpXCIsXCJidWNrZXRcIjpcIiQoYnVja2V0KVwiLFwiZnNpemVcIjokKGZzaXplKX0iLCJkZWFkbGluZSI6MTYxODU2NTQ1Mn0=
//        System.out.println(getCustomToken("itisummer-huanan-bucket","test.jpg"));

        //7viju8jm47T3cIZFLPXPJpj28OUpjQzFPS5Fw80p:-os046WHNR7zxhqDSo1GOwqYKFk=:eyJzY29wZSI6Iml0aXN1bW1lci1odWFuYW4tYnVja2V0OnRlc3QuanBnIiwiZGVhZGxpbmUiOjE2MTg1NzU5OTZ9
        System.out.println(getBaseToken("itisummer-huanan-bucket","test.jpg"));
        //remove("claim_guarantee.docx");
    }

}

