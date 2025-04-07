package org.example.informationsystem.Service;

import org.example.informationsystem.pojo.DTO.ChangePasswordDTO;
import org.example.informationsystem.pojo.DTO.LoginDTO;
import org.example.informationsystem.pojo.DTO.RegisterDTO;
import org.example.informationsystem.pojo.VO.LoginVO;
import org.example.informationsystem.pojo.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    boolean updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean createUser(User user);

    boolean deleteUser(Long id);

    ResponseEntity<LoginVO> login(LoginDTO loginDTO);

    ResponseEntity<String> register(RegisterDTO registerDTO);

    ResponseEntity<String> changePassword(ChangePasswordDTO changePasswordDTO);
}
