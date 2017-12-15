package utils;

import entities.RegistrationCodes;
import entities.Users;
import exceptions.NonComplianceWithConstraints;
import exceptions.PatternException;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataReader {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // TODO: Move constraints in additional file
    // TODO: Add string size checking
    public static RegistrationCodes readRegistrationCode() {
        RegistrationCodes registrationCodes = new RegistrationCodes();
        System.out.println(MenuInputType.INVITE_CODE_STATUS);
        readStatus(registrationCodes);
        System.out.println(MenuInputType.EMAIL);
        for (; ; ) {
            try {
                String email = scanner.nextLine();
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                if (!matcher.find()) {
                    throw new PatternException("email", VALID_EMAIL_ADDRESS_REGEX);
                }
                registrationCodes.setEmail(email);
                break;
            } catch (PatternException exp) {
                System.out.println(exp.getMessage());
                System.out.println("Повторите ввод: ");
            }
        }
        return registrationCodes;
    }

    public static String readNewStatusForRegistrationCode() {
        RegistrationCodes registrationCodes = new RegistrationCodes();
        System.out.println(MenuInputType.INVITE_CODE_STATUS);
        readStatus(registrationCodes);
        return registrationCodes.getInviteCodeStatus();
    }

    public static BigDecimal readInviteCode() {
        BigDecimal inviteCode = null;
        System.out.println(MenuInputType.INVITE_CODE);
        for (; ; ) {
            String tempInviteCode = scanner.nextLine();
            try {
                inviteCode = new BigDecimal(tempInviteCode);
                break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                System.out.println("Повторите ввод: ");
            }
        }
        return inviteCode;
    }

    private static void readStatus(RegistrationCodes registrationCodes) {
        for (; ; ) {
            try {
                String inviteCodeStatus = scanner.nextLine();
                if (!inviteCodeStatus.equals("available") && !inviteCodeStatus.equals("not available")) {
                    throw new NonComplianceWithConstraints("invite_code_status", "available", "not available");
                }
                registrationCodes.setInviteCodeStatus(inviteCodeStatus);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    public static Users readUser() {
        Users user = new Users();
        System.out.println(MenuInputType.USERNAME);

        for (; ; ) {
            try {
                String username = scanner.nextLine();
                if (username.length() >= 20) throw new Exception();
                user.setUsername(username);
                break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                System.out.println("Повторите ввод: ");
            }
        }

        System.out.println(MenuInputType.PASSWORD);
        for (; ; ) {
            try {
                String password = scanner.nextLine();
                if (password.length() >= 25) throw new Exception();
                user.setPassword(password);
                break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                System.out.println("Повторите ввод: ");
            }
        }
        return user;
    }
}
