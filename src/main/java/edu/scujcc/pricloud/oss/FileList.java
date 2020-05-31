package edu.scujcc.pricloud.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import edu.scujcc.pricloud.model.File;

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
        objectListing.getCommonPrefixes();
        ossClient.shutdown();
        for (OSSObjectSummary s : sums) {
            File f = new File();
            f.setKey(s.getKey());
            f.setSize(s.getSize());
            f.seteTag(s.getETag());
            f.setLastModified(s.getLastModified());
            f.setBucketName(s.getBucketName());
            if (s.getKey().endsWith("/")) {
                f.setType(File.TPYEISFOLDER);
            } else {
                f.setType(File.TPYEISFILE);
            }
            fileList.add(f);
        }
        return fileList;
    }
}
