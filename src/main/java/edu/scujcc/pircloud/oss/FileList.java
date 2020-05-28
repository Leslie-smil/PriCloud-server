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

public class FileList extends Oss {

    public void get() {
// 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, accessKeyId, accessKeySecret);

// 设置最大个数。
        final int maxKeys = 200;
// 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(bucketName).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("Key : " + s.getKey());
            System.out.println("LastModified : " + s.getLastModified());
            System.out.println("Size : " + s.getSize());
            System.out.println("StorageClass : " + s.getStorageClass());
            System.out.println("Owner : " + s.getOwner());
            System.out.println("BucketName" + s.getBucketName());
            System.out.println("\n");
        }
// 关闭OSSClient。
        ossClient.shutdown();

    }
}
