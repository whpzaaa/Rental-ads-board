package org.example.informationsystem.Entity.VO;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}