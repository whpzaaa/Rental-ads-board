package org.example.informationsystem.Service.ServiceImpl;



import org.example.informationsystem.Repository.UserRepository;
import org.example.informationsystem.Service.UserService;
import org.example.informationsystem.Entity.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean updateUser(User user) {
        if (!userRepository.existsById(user.getUserId())) return false;
        userRepository.updateById(user);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean createUser(User user) {
        if(user.getRole().equals("admin")) return false;
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User saveUser = userRepository.save(user);
        return saveUser != null;
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
