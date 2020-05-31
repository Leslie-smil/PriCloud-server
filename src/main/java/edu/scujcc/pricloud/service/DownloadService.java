package edu.scujcc.pricloud.service;

import edu.scujcc.pricloud.dao.FileRepository;
import edu.scujcc.pricloud.model.File;
import edu.scujcc.pricloud.oss.GetOssFileUrl;
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
