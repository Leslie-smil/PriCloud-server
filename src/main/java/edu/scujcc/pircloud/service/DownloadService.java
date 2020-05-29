package edu.scujcc.pircloud.service;

import edu.scujcc.pircloud.dao.FileRepository;
import edu.scujcc.pircloud.model.File;
import edu.scujcc.pircloud.oss.GetOssFileUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Optional;

/**
 * @author FSMG
 */
@Service
public class DownloadService {
    URL url;
    @Autowired
    private FileRepository fileRepository;

    public URL getFileLink(String id) {
        Optional<File> file = fileRepository.findById(id);
        if (file.isPresent()) {
            File file1 = file.get();
            GetOssFileUrl getOssFileUrl = new GetOssFileUrl();
            url = getOssFileUrl.geturl(file1.getKey());
        }
        return url;
    }
}
