package dao;

import entities.UserStudying;

import java.math.BigDecimal;

public interface UserStudyingDAO extends GenericDAO<UserStudying, BigDecimal> {

    BigDecimal getIdByUserGroup(String userGroup);

}
