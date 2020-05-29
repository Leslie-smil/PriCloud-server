package edu.scujcc.pircloud.api;

import edu.scujcc.pircloud.model.File;
import edu.scujcc.pircloud.model.Result;
import edu.scujcc.pircloud.oss.FileList;
import edu.scujcc.pircloud.service.DownloadService;
import edu.scujcc.pircloud.service.FileService;
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
    @Autowired
    private DownloadService downloadService;

    /**
     * @return result 根目录列表
     */
    @GetMapping
    public Result<List<File>> getFileList() {
        Result<List<File>> result = new Result<>();
        List<File> files = fileService.getRootDirectory();
        FileList fileList = new FileList();
        fileService.createFiles(fileList.get());
        logger.debug("获取目录");
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(files);
        return result;
    }

    @GetMapping(value = "s/{subdirectory}")
    public Result<List<File>> getSubdirectoryList(@PathVariable String subdirectory) {
        subdirectory = subdirectory.replaceAll("\\.", "/");
        Result<List<File>> result = new Result<>();
        List<File> files = fileService.getFliesByPrefix(subdirectory);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(files);
        return result;
    }

    @GetMapping(value = "f/{name}")
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
        URL url = downloadService.getFileLink(id);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(url);
        return result;
    }

}
