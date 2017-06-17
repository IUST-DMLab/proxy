package ir.ac.iust.dml.kg.knowledge.proxy.web.controller;

import ir.ac.iust.dml.kg.knowledge.proxy.access.dao.IUserDao;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Controller for user creation
 */
@Controller
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final IUserDao userDao;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, IUserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @PostConstruct
    public void setup() {
        if (userDao.readByUsername("superuser") == null)
            userDao.write(new User("superuser", passwordEncoder.encode("superuser"), "superuser"));
    }
}
