package utils;

import daos.RegistrationCodesDAOImpl;
import entities.RegistrationCodes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomInviteCodesGenerator {

    private final static int INVITE_CODE_LENGTH = 38;
    // точность как гарантия рандома
    private final static int INVITE_CODES_COUNT = 10;
    private BigDecimal inviteCode;

    private BigDecimal getNonExistsInviteCode(List<BigDecimal> inviteCodeList) {
        BigDecimal inviteCode = null;
        RegistrationCodesDAOImpl registrationCodesDAO = new RegistrationCodesDAOImpl();

        // получаем список имеющихся инвайт-кодов
        List<RegistrationCodes> registrationCodesList = registrationCodesDAO.getList();

        for (BigDecimal decimal : inviteCodeList) {
            if (!registrationCodesList.contains(decimal)) {
                inviteCode = decimal;
            }
        }

        return inviteCode;
    }

    public BigDecimal getInviteCode() {
        List<BigDecimal> inviteCodesList = new ArrayList<>();
        for (int i = 0; i < INVITE_CODES_COUNT; i++) {
            long randomNum = ThreadLocalRandom.current().nextInt(INVITE_CODE_LENGTH, Integer.MAX_VALUE);
            inviteCodesList.add(BigDecimal.valueOf(randomNum));
        }
        try {
            inviteCode = getNonExistsInviteCode(inviteCodesList);
        } catch (NullPointerException exp) {
            exp.getMessage();
        }
        return inviteCode;
    }
}
