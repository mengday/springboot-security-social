package com.example.security.social;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * summary.
 * <p>
 * detailed description
 *
 * @author Mengday Zhang
 * @version 1.0
 * @since 2019-05-11
 */
@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class SysUser {
    private Long id;
    private String username;
    private String password;

    private List<SysPermission> sysPermissions;
}
