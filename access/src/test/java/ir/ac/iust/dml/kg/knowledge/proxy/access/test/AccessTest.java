package ir.ac.iust.dml.kg.knowledge.proxy.access.test;

import ir.ac.iust.dml.kg.knowledge.proxy.access.dao.IForwardDao;
import ir.ac.iust.dml.kg.knowledge.proxy.access.dao.IPermissionDao;
import ir.ac.iust.dml.kg.knowledge.proxy.access.dao.IUserDao;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.Forward;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.Permission;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for access
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:persistence-context.xml")
public class AccessTest {
    @Autowired
    IUserDao userDao;
    @Autowired
    IPermissionDao permissionDao;
    @Autowired
    IForwardDao forwardDao;

    final User defUser1 = new User("#defUser1", "pass1", "name1", "expert");

    @Before
    public void setup() {
        userDao.write(defUser1);
    }

    @After
    public void cleanup() {
        userDao.delete(defUser1);
    }

    @Test
    public void testUserDao() {
        final User user1 = new User("user1", "pass1", "name1", "expert");
        final User user2 = new User("user2", "pass2", "name2", "expert");
        userDao.write(user1, user2);
        try {
            userDao.write(new User("user1", "pass1", "name1", "expert"));
            assert false;
        } catch (Throwable th) {
            assert true;
        }
        assert userDao.read(user1.getId()).getUsername().equals(user1.getUsername());
        userDao.delete(user1, user2);
    }

    @Test
    public void testPermissionDao() {
        final Permission per1 = new Permission("per1");
        final Permission per2 = new Permission("per2");
        permissionDao.write(per1, per2);
        try {
            permissionDao.write(new Permission("per1"));
            assert false;
        } catch (Throwable th) {
            assert true;
        }
        assert permissionDao.read(per1.getId()).getTitle().equals(per1.getTitle());
        assert permissionDao.readAll().contains(per2);
        permissionDao.delete(per1, per2);
    }

    @Test
    public void testForwardDao() {
        final Forward for1 = new Forward("c1", "f1");
        final Forward for2 = new Forward("c2", "f2");
        forwardDao.write(for1, for2);
        try {
            forwardDao.write(new Forward("c1", "f1"));
            assert false;
        } catch (Throwable th) {
            assert true;
        }
        assert forwardDao.readAll().contains(for1);
        forwardDao.delete(for1, for2);

    }
}
