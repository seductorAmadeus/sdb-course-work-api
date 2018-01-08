package dao;

import entities.RegistrationCodes;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

import java.math.BigDecimal;

public interface RegistrationCodesDAO extends GenericDAO<RegistrationCodes, BigDecimal> {

    RegistrationCodes getAvailableCode();

}
