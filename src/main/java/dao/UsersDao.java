package dao;

import entities.Users;

import java.math.BigDecimal;

public interface UsersDao extends GenericDAO<Users, BigDecimal> {

    BigDecimal getUserId(String username, String password);

}
