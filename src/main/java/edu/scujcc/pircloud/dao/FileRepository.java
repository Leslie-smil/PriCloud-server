package edu.scujcc.pircloud.dao;

import edu.scujcc.pircloud.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author FSMG
 */
@Repository
public interface FileRepository extends MongoRepository<File, String> {
    /**
     * 查询 etag
     *
     * @param etag oss id
     * @return File
     */
    File findOneByeTag(String etag);
}
