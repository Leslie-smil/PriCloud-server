package edu.scujcc.pircloud.api;

import edu.scujcc.pircloud.model.File;
import edu.scujcc.pircloud.model.Result;
import edu.scujcc.pircloud.oss.FileList;
import edu.scujcc.pircloud.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    /**
     * @param prefix 可选，默认获取根目录
     * @return result 根目录列表
     */
    @GetMapping
    public Result<List<File>> getFileList(String... prefix) {
        Result<List<File>> result = new Result<>();
        logger.debug("获取" + Arrays.toString(prefix) + "目录");
        FileList fileList = new FileList();
        List<File> f = fileList.get();
        List<File> s = fileService.createFiles(f);
        result.setStatus(Result.SUCCESS);
        result.setMessage("Success");
        result.setData(s);
        return result;
    }

}
