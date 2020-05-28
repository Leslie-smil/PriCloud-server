package edu.scujcc.pircloud.dao;

import edu.scujcc.pircloud.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FSMG
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * fetch data by rule id
     *
     * @param username 用户名
     * @return 用户
     */
    List<User> findByUsername(String username);

    /**
     * fetch data by rule id
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户信息
     */
    User findOneByUsernameAndPassword(String username, String password);

    /**
     * 根据token查询user
     *
     * @param token token
     * @return User
     */
    List<User> findByTaken(String token);
}
