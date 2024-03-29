package dao;

import entities.RegistrationCodes;

import java.math.BigDecimal;

public interface RegistrationCodesDAO extends GenericDAO<RegistrationCodes, BigDecimal> {

    RegistrationCodes getAvailableCode();

    BigDecimal createH(RegistrationCodes registrationCodes);
}
