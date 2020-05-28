package edu.scujcc.pircloud.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import edu.scujcc.pircloud.model.Oss;

import java.util.List;

/**
 * @author FSMG
 */
public class FilePrefix extends Oss {
    /**
     * 默认列举100个文件
     *
     * @param data 文件前缀
     */
    public void get(String data) {
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, accessKeyId, accessKeySecret);

// 指定前缀。

// 列举包含指定前缀的文件。默认列举100个文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withPrefix(data));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }

// 关闭OSSClient。
        ossClient.shutdown();

    }
}
