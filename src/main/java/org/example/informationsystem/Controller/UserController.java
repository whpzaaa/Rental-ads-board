package org.example.informationsystem.Controller;


import org.example.informationsystem.Properties.JwtProperties;
import org.example.informationsystem.pojo.DTO.ChangePasswordDTO;
import org.example.informationsystem.pojo.DTO.LoginDTO;
import org.example.informationsystem.pojo.DTO.RegisterDTO;
import org.example.informationsystem.pojo.VO.LoginVO;
import org.example.informationsystem.pojo.VO.UserVO;
import org.example.informationsystem.Service.UserService;
import org.example.informationsystem.pojo.entity.User;
import org.example.informationsystem.utills.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //登录
    @PostMapping("/login")
    public ResponseEntity<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        // 调用业务层认证方法
        return userService.login(loginDTO);
    }

    //注册
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    //修改密码
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.changePassword(changePasswordDTO);
    }

    // 获取所有用户
    @GetMapping
    public ResponseEntity<List<UserVO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserVO> userVOList = new ArrayList<>(users.size());
        for (User user : users) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        return ResponseEntity.ok(userVOList);
    }

    // 根据ID获取用户
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // 创建新用户
    @PostMapping
    public ResponseEntity<UserVO> createUser(@RequestBody User user) {
        boolean success = userService.createUser(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return success ? ResponseEntity.status(HttpStatus.CREATED).body(userVO)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 更新用户
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean success = userService.updateUser(user);
        return success ? ResponseEntity.ok("更新成功")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return success ? ResponseEntity.ok("删除成功")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
    }
}
