package edu.scujcc.pircloud.service;

import edu.scujcc.pircloud.dao.UserRepository;
import edu.scujcc.pircloud.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.beans.FeatureDescriptor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author FSMG
 */
@Service
public class UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserService.class);
    String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    @Autowired
    private UserRepository repo;

    public static String[] getNullPropertyNames(User user) {
        final BeanWrapper wrapperSource = new BeanWrapperImpl(user);
        return Stream.of(wrapperSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrapperSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public User createUser(User user) {
        User user1 = new User();
        if (getUserByUsername(user.getUsername()) == 0) {
            user1 = repo.save(user);
            user1.setPassword(null);
        }
        return user1;
    }

    public User getUserById(String userId) {
        Optional<User> result = repo.findById(userId);
        return result.orElse(null);
    }

    public int getUserByUsername(String username) {
        return repo.findByUsername(username).size();
    }

    public User updateUser(User user) {
        User saved = getUserById(user.getId());
        BeanUtils.copyProperties(user, saved, getNullPropertyNames(user));
        System.out.println(saved);
        saved = repo.save(saved);
        saved.setPassword(null);
        return saved;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User result = new User();
        if (repo.findOneByUsernameAndPassword(username, password) != null) {
            result = repo.findOneByUsernameAndPassword(username, password);
            result.setPassword(null);
        }
        return result;
    }

    public void deleteUser(String id) {
        repo.deleteById(id);
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public User login(User user, String ip) {
        User user1 = getUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (user1.getId() != null) {
            int status = user1.getStatus();
            if (status >= 0) {
                status++;
                user1.setStatus(status);
                user1.setLastIp(ip);
                user1.setTaken(DigestUtils.md5DigestAsHex((user1.getId() + time).getBytes()));
                updateUser(user1);
            }
        }
        logger.debug("id :" + user1.getId());
        return user1;
    }

    public int getToken(String token) {
        return repo.findByTaken(token).size();
    }
}
