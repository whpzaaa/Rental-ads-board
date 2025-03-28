package org.example.informationsystem.Service;

import org.example.informationsystem.pojo.entity.User;

import java.util.List;

public interface UserService {

    boolean updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean createUser(User user);

    boolean deleteUser(Long id);
}
