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
public class FileSize extends Oss {
    private static long calculateFolderLength(OSS ossClient, String bucketName, String folder) {
        long size = 0L;
        ObjectListing objectListing = null;
        do {
            // MaxKey默认值为100，最大值为1000。
            ListObjectsRequest request = new ListObjectsRequest(bucketName).withPrefix(folder).withMaxKeys(1000);
            if (objectListing != null) {
                request.setMarker(objectListing.getNextMarker());
            }
            objectListing = ossClient.listObjects(request);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                size += s.getSize();
            }
        } while (objectListing.isTruncated());
        return size;
    }

    /**
     * 默认情况下，每次列举100个文件或目录。
     *
     * @param keyPrefix 指定前缀，若希望遍历主目录文件夹，则将该值置空。
     */
    public void calculate(String keyPrefix) {
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, accessKeyId, accessKeySecret);
        ObjectListing objectListing = null;
        do {

            ListObjectsRequest request = new ListObjectsRequest(bucketName).withDelimiter("/").withPrefix(keyPrefix);
            if (objectListing != null) {
                request.setMarker(objectListing.getNextMarker());
            }
            objectListing = ossClient.listObjects(request);
            List<String> folders = objectListing.getCommonPrefixes();
            for (String folder : folders) {
                System.out.println(folder + " : " + (calculateFolderLength(ossClient, bucketName, folder) / 1024) + "KB");
            }
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println(s.getKey() + " : " + (s.getSize() / 1024) + "KB");
            }
        } while (objectListing.isTruncated());
        ossClient.shutdown();
    }

}
