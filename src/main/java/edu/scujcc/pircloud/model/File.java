package edu.scujcc.pircloud.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Objects;

/**
 * @author FSMG
 */
public class File {
    public static String INTERNALENDPOINT = "oss-cn-hongkong-internal.aliyuncs.com";
    public static String ENDPOINT = "oss-cn-hongkong.aliyuncs.com";
    public static String accessKeyId = "LTAI4G14NsAKu8BzBSGCGmHA";
    public static String accessKeySecret = "OG3EXeyA6w7cAlQxTk7A5riJV1xGS9";
    public static String BucketName = "fuyu-hk-test";
    @Id
    private String id;
    private String bucketName;
    private String key;
    private String eTag;
    private long size;
    private Date lastModified;

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", key='" + key + '\'' +
                ", eTag='" + eTag + '\'' +
                ", size=" + size +
                ", lastModified=" + lastModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return size == file.size &&
                Objects.equals(id, file.id) &&
                Objects.equals(bucketName, file.bucketName) &&
                Objects.equals(key, file.key) &&
                Objects.equals(eTag, file.eTag) &&
                Objects.equals(lastModified, file.lastModified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bucketName, key, eTag, size, lastModified);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
