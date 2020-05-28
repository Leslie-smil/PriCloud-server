package edu.scujcc.pircloud.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import edu.scujcc.pircloud.model.File;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FSMG
 */
public class FileList {
    List<File> fileList = new ArrayList<>();
    List<OSSObjectSummary> sums;

    public List<File> get() {
        OSS ossClient = new OSSClientBuilder().build(File.ENDPOINT, File.accessKeyId, File.accessKeySecret);
        final int maxKeys = 200;
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(File.BucketName).withMaxKeys(maxKeys));
        sums = objectListing.getObjectSummaries();
        ossClient.shutdown();

        for (OSSObjectSummary s : sums) {
//            System.out.println("Key : " + s.getKey());
//            System.out.println("LastModified : " + s.getLastModified());
//            System.out.println("Size : " + s.getSize());
//            System.out.println("StorageClass : " + s.getStorageClass());
//            System.out.println("Owner : " + s.getOwner());
//            System.out.println("BucketName" + s.getBucketName());
//            System.out.println("ETag: " + s.getETag());
//            System.out.println("\n");
            File f = new File();
            f.setKey(s.getKey());
            f.setSize(s.getSize());
            f.seteTag(s.getETag());
            f.setLastModified(s.getLastModified());
            f.setBucketName(s.getBucketName());
            fileList.add(f);
        }
        return fileList;
    }
}
