package edu.scujcc.pircloud.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import edu.scujcc.pircloud.model.File;

import java.net.URL;
import java.util.Date;

/**
 * @author FSMG
 */
public class GetOssFileUrl {

    public URL geturl(String objectName) {
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(File.INTERNALENDPOINT, File.accessKeyId, File.accessKeySecret);

// 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 360 * 1000);

        URL url = ossClient.generatePresignedUrl(File.BucketName, objectName, expiration);

// 关闭OSSClient。
        ossClient.shutdown();
        return url;
    }
}
