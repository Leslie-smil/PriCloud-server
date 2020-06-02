package edu.scujcc.pricloud.service;

import edu.scujcc.pricloud.dao.FileRepository;
import edu.scujcc.pricloud.model.File;
import edu.scujcc.pricloud.oss.GetOssFileUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author FSMG
 */
@Service
public class FileService {
    public static final Logger logger = LoggerFactory.getLogger(FileService.class);
    @Autowired
    private FileRepository fileRepository;

    public static String[] getNullPropertyNames(File file) {
        final BeanWrapper wrapperSource = new BeanWrapperImpl(file);
        return Stream.of(wrapperSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrapperSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public File createFile(File file) {
        File file1 = fileRepository.findOneByeTag(file.geteTag());
        if (file1 == null) {
            return fileRepository.save(file);
        }
        return file1;
    }

    public Integer createFiles(List<File> files) {
        List<File> files1 = new ArrayList<>();
        for (File s : files) {
            if (fileRepository.findOneByeTag(s.geteTag()) == null) {
                files1.add(fileRepository.save(s));
            }
        }
        return files1.size();
    }

    public List<File> getRootDirectory() {
        List<File> files = getAllFiles();
        return getList(files);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public List<File> getFliesByPrefix(String prefix) {
        List<File> files = getAllFiles();
        List<File> files1 = new ArrayList<>();
        for (File f : files) {
            if (f.getKey().startsWith(prefix)) {
                f.setKey(f.getKey().replaceFirst(prefix, ""));
                files1.add(f);
            }
        }
        return getList(files1);
    }

    public List<File> getFilesBySuffix(String suffix) {
        List<File> files = getAllFiles();
        List<File> files1 = new ArrayList<>();
        for (File f : files) {
            String[] s = f.getKey().split("/");
            if (s[s.length - 1].contains(suffix)) {
                files1.add(f);
            }
        }
        return files1;
    }

    public List<File> getList(List<File> files) {
        List<File> files1 = new ArrayList<>();
        List<File> files2 = new ArrayList<>();
        for (File f : files) {
            if (f.getKey().contains("/")) {
                String[] s = f.getKey().split("/");
                files2.addAll(files1);
                for (File f1 : files2) {
                    if (f1.getKey().equals(s[0])) {
                        break;
                    } else {
                        File file = new File();
                        file.setType(File.TPYEISFOLDER);
                        file.setKey(s[0]);
                        file.setBucketName(f.getBucketName());
                        files1.add(file);
                    }
                }
            } else {
                files1.add(f);
            }
        }
        return files1.stream().distinct().collect(Collectors.toList());
    }

    public long getBucketSize() {
        List<File> files = getAllFiles();
        long size = 0;
        for (File f : files) {
            size += f.getSize();
        }
        return size;
    }

    public URL getFileUrl(String id) {
        URL url = null;
        Optional<File> file = fileRepository.findById(id);
        if (file.isPresent()) {
            File file1 = file.get();
            GetOssFileUrl getOssFileUrl = new GetOssFileUrl();
            url = getOssFileUrl.geturl(file1.getKey());
        }
        return url;
    }
}