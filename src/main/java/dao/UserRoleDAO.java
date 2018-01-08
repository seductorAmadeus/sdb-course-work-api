package dao;

import entities.UserRole;

import java.math.BigDecimal;

public interface UserRoleDAO extends GenericDAO<UserRole, BigDecimal> {

    BigDecimal getRootRoleId();

    BigDecimal getAdminRoleId();

    BigDecimal getTeacherRoleId();

    BigDecimal getStudRoleId();

    void generateAllUsersRoles();

}
