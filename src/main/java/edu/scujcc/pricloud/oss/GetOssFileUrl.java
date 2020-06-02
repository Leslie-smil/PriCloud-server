package edu.scujcc.pricloud.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import edu.scujcc.pricloud.model.File;

import java.net.URL;
import java.util.Date;

/**
 * @author FSMG
 */
public class GetOssFileUrl {
    public URL getUrl(String objectName) {
        OSS ossClient = new OSSClientBuilder().build(File.INTERNALENDPOINT, File.accessKeyId, File.accessKeySecret);
        Date expiration = new Date(System.currentTimeMillis() + 360 * 1000);
        URL url = ossClient.generatePresignedUrl(File.BucketName, objectName, expiration);
        ossClient.shutdown();
        return url;
    }
}
