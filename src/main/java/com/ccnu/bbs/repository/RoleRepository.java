package com.ccnu.bbs.repository;


import com.ccnu.bbs.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    // 查看某用户的身份
    @Query("select r from Role r, com.ccnu.bbs.entity.Portray p where " +
            "r.roleId = p.portrayRoleId and p.portrayUserId = ?1")
    Role findUserRole(String userId);
}
