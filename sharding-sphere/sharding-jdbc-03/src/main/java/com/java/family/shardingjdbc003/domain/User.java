package com.java.family.shardingjdbc003.domain;

import lombok.Data;

@Data
public class User {
    private Long userId;

    private String fullName;

    private String userType;

    private String cipherPwd;

    private String mobile;

    private String idCard;
}
