package edu.scujcc.pircloud.service;

import edu.scujcc.pircloud.dao.FileRepository;
import edu.scujcc.pircloud.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.FeatureDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author FSMG
 */
@Service
public class FileService {
    public static final Logger logger = LoggerFactory.getLogger(FileService.class);
    @Autowired
    private FileRepository repo;

    public static String[] getNullPropertyNames(File file) {
        final BeanWrapper wrapperSource = new BeanWrapperImpl(file);
        return Stream.of(wrapperSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrapperSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public File createFile(File file) {
        File file1 = repo.findOneByeTag(file.geteTag());
        if (file1 == null) {
            return repo.save(file);
        }
        return file1;
    }

    public List<File> createFiles(List<File> files) {
        List<File> files1 = new ArrayList<>();
        for (File s : files) {
            File file1 = repo.findOneByeTag(s.geteTag());
            if (file1 == null) {
                files1.add(repo.save(s));
            }
        }
        return files1;
    }
}
