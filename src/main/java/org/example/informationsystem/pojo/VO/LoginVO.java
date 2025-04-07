package org.example.informationsystem.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private Long id;

    private String userName;

    private String role;

    private String token;

}
