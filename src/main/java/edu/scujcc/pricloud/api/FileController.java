package edu.scujcc.pricloud.api;

import edu.scujcc.pricloud.model.File;
import edu.scujcc.pricloud.model.Result;
import edu.scujcc.pricloud.oss.FileList;
import edu.scujcc.pricloud.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author FSMG
 */
@RestController
@RequestMapping(value = "/file")
public class FileController {
    public static final Logger logger = LoggerFactory.getLogger(FileController.class);
    String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    @Autowired
    private FileService fileService;

    @GetMapping
    public Result<List<File>> getFileList() {
        Result<List<File>> result = new Result<>();
        List<File> files = fileService.getRootDirectory();
        logger.debug("获取根目录" + time);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(files);
        return result;
    }

    @GetMapping(value = "/{subdirectory}")
    public Result<List<File>> getSubdirectoryList(@PathVariable String subdirectory) {
        subdirectory = subdirectory.replaceAll("\\.", "/");
        Result<List<File>> result = new Result<>();
        logger.debug("获取指定目录：" + subdirectory + time);
        List<File> files = fileService.getFliesByPrefix(subdirectory);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(files);
        return result;
    }

    @GetMapping(value = "s/{name}")
    public Result<List<File>> searchFilesByName(@PathVariable String name) {
        Result<List<File>> result = new Result<>();
        List<File> files = fileService.getFilesBySuffix(name);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(files);
        return result;
    }

    @GetMapping(value = "/download/{id}")
    public Result<URL> getDownloadLink(@PathVariable String id) {
        Result<URL> result = new Result<>();
        URL url = fileService.getFileUrl(id);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(url);
        return result;
    }

    @GetMapping(value = "/info")
    public Result<Long> getBucketInfo() {
        Result<Long> result = new Result<>();
        long s = fileService.getBucketSize();
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(s);
        return result;
    }

    @GetMapping(value = "/refresh")
    public Result<String> refresh() {
        Result<String> result = new Result<>();
        FileList fileList = new FileList();
        int size = fileService.createFiles(fileList.get());
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData("更新 " + size + " 个文件");
        return result;
    }

}
