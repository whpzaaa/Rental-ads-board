package org.example.informationsystem.Service.ServiceImpl;



import org.example.informationsystem.Properties.JwtProperties;
import org.example.informationsystem.Repository.UserRepository;
import org.example.informationsystem.Service.UserService;
import org.example.informationsystem.pojo.DTO.ChangePasswordDTO;
import org.example.informationsystem.pojo.DTO.LoginDTO;
import org.example.informationsystem.pojo.DTO.RegisterDTO;
import org.example.informationsystem.pojo.VO.LoginVO;
import org.example.informationsystem.pojo.entity.User;
import org.example.informationsystem.utills.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

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
        if (user.getRole().equals("admin")) return false;
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

    /**
     * 用户登录方法：
     * 1. 根据用户名查询用户
     * 2. 校验密码（MD5 加密比对）
     * 3. 检查账号状态是否可用
     * 4. 返回用户实体
     */
    @Override
    public ResponseEntity<LoginVO> login(LoginDTO loginDTO) {
        // 1. 根据用户名查询数据库中的用户
        User user = userRepository.getByUsername(loginDTO.getUsername());
        if (user == null) {
            // 用户不存在
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // 2. 密码比对：先进行 MD5 加密，然后比对数据库中的密码
        String encodePwd = DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes(StandardCharsets.UTF_8));
        if (!encodePwd.equals(user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 3. 检查账号状态是否已被锁定，如：禁用状态
        if (!user.getIsActive()) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        }
        // 登录成功后生成 jwt 令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        LoginVO userLoginVO = LoginVO.builder()
                .id(user.getUserId())
                .userName(user.getUsername())
                .role(user.getRole())
                .token(token)
                .build();
        return ResponseEntity.ok(userLoginVO);
    }
    /**
     * 用户注册方法：
     * 1. 检查用户名是否已存在
     * 2. 密码 MD5 加密并保存
     * 3. 返回登录信息（自动登录生成 token）
     */
    public ResponseEntity<String> register(RegisterDTO registerDTO) {
        // 1. 检查用户名是否已存在
        User existNameUser = userRepository.getByUsername(registerDTO.getUsername());
        User existEmailUser = userRepository.getByEmail(registerDTO.getEmail());
        if (existNameUser != null) {
            // 用户已经存在，则返回 409 冲突状态
            return ResponseEntity.status(HttpStatus.CONFLICT).body("user already exists");
        }
        if (existEmailUser != null) {
            // 邮箱已注册，则返回 409 冲突状态
            return ResponseEntity.status(HttpStatus.CONFLICT).body("email is registered");
        }
        // 2. 密码加密（MD5）并组装 User 对象
        String encodePwd = DigestUtils.md5DigestAsHex(
                registerDTO.getPassword().getBytes(StandardCharsets.UTF_8)
        );
        User newUser = User.builder()
                .username(registerDTO.getUsername())
                .passwordHash(encodePwd)
                .email(registerDTO.getEmail())
                .role(registerDTO.getRole())
                .isActive(registerDTO.getIsActive())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        // 保存注册用户
        userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("注册成功，请前往登录页面");
    }
    @Override
    public ResponseEntity<String> changePassword(ChangePasswordDTO changePasswordDTO) {
        // 1. 根据用户ID获取用户
        Optional<User> optionalUser = userRepository.findById(changePasswordDTO.getUserId());
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        }
        User user = optionalUser.get();
        // 2. 对比旧密码，注意旧密码也需要 MD5 加密后比对
        String encodeOldPwd = DigestUtils.md5DigestAsHex(changePasswordDTO.getOldPassword().getBytes(StandardCharsets.UTF_8));
        if (!encodeOldPwd.equals(user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("旧密码错误");
        }

        // 3. 对新密码进行 MD5 加密，保存新密码
        String encodeNewPwd = DigestUtils.md5DigestAsHex(changePasswordDTO.getNewPassword().getBytes(StandardCharsets.UTF_8));
        // 更新用户信息，如密码及修改时间
        user.setPasswordHash(encodeNewPwd);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return ResponseEntity.ok("密码修改成功");
    }
}
