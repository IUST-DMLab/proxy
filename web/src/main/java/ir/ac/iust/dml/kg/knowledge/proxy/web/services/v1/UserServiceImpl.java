package ir.ac.iust.dml.kg.knowledge.proxy.web.services.v1;

import ir.ac.iust.dml.kg.knowledge.commons.PagingList;
import ir.ac.iust.dml.kg.knowledge.proxy.access.dao.IPermissionDao;
import ir.ac.iust.dml.kg.knowledge.proxy.access.dao.IUserDao;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.Permission;
import ir.ac.iust.dml.kg.knowledge.proxy.access.entities.User;
import ir.ac.iust.dml.kg.knowledge.proxy.web.services.v1.data.PermissionData;
import ir.ac.iust.dml.kg.knowledge.proxy.web.services.v1.data.UserData;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.jws.WebService;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Impl {@link IUserServices}
 */
@WebService(endpointInterface = "ir.ac.iust.dml.kg.knowledge.proxy.web.services.v1.IUserServices")
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "Duplicates"})
public class UserServiceImpl implements IUserServices {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IPermissionDao permissionDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<String> login() {
        final Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        final List<String> result = new ArrayList<>();
        authorities.forEach(a -> result.add(a.getAuthority()));
        return result;
    }

    @Override
    public User edit(@Valid UserData data) {
        final User user = data.getIdentifier() == null ? new User() : userDao.read(new ObjectId(data.getIdentifier()));
        if (user == null) return null;
        final User oldUser = userDao.readByUsername(data.getUsername());
        if (oldUser != null && !oldUser.getIdentifier().equals(user.getIdentifier()))
            throw new RuntimeException("Username can not be repeated");
        if (data.getPassword() != null) data.setPassword(passwordEncoder.encode(data.getPassword()));
        data.fill(user);
        userDao.write(user);
        return user;
    }


    @Override
    public PagingList<User> search(String name, String username, int page, int pageSize) {
        return userDao.search(name, username, page, pageSize);
    }

    @Override
    public Permission permission(@Valid PermissionData data) {
        final Permission permission = data.getIdentifier() == null ? new Permission() : permissionDao.read(new ObjectId(data.getIdentifier()));
        if (permission == null) return null;
        final Permission oldPermission = permissionDao.readByTitle(data.getTitle());
        if (oldPermission != null && !oldPermission.getIdentifier().equals(permission.getIdentifier()))
            throw new RuntimeException("Title can not be repeated");
        data.fill(permission);
        permissionDao.write(permission);
        return permission;
    }

    @Override
    public List<Permission> permissions() {
        return permissionDao.readAll();
    }

}
