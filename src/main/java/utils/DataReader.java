package utils;

import entities.Bcomp;
import entities.RegistrationCodes;
import entities.UserProfile;
import entities.Users;
import exceptions.NonComplianceWithConstraints;
import exceptions.PatternException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataReader {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_USER_GROUP_REGEX =
            Pattern.compile("^[A-Z][0-9]{4}$", Pattern.CASE_INSENSITIVE);


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
                System.out.println("Repeat input: ");
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
                System.out.println("Repeat input: ");
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

    public static UserProfile readUserProfile() {
        UserProfile userProfile = new UserProfile();
        System.out.println(MenuInputType.STUDYING_STATUS);

        for (; ; ) {
            try {
                String studyingStatus = scanner.nextLine();
                if (!studyingStatus.equals("YES") && !studyingStatus.equals("NOT")) {
                    throw new NonComplianceWithConstraints("studying_status", "YES", "NOT");
                }
                userProfile.setStudyingStatus(studyingStatus);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }

        System.out.println(MenuInputType.FIRST_NAME);

        for (; ; ) {
            try {
                String firstName = scanner.nextLine();
                if (firstName.length() >= 20) throw new Exception();
                userProfile.setFirstName(firstName);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.LAST_NAME);

        for (; ; ) {
            try {
                String lastName = scanner.nextLine();
                if (lastName.length() >= 20) throw new Exception();
                userProfile.setLastName(lastName);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }


        System.out.println(MenuInputType.GENDER);

        for (; ; ) {
            try {
                String gender = scanner.nextLine();
                if (!gender.equals("M") && !gender.equals("F")) {
                    throw new NonComplianceWithConstraints("gender", "M", "F");
                }
                userProfile.setGender(gender);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }

        System.out.println(MenuInputType.DATE_OF_BIRTH);

        for (; ; ) {
            try {
                String dateOfBirth = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = dateFormat.parse(dateOfBirth);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                userProfile.setDateOfBirth(timestamp);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        return userProfile;
    }

    public static String readUserRole() {
        System.out.println(MenuInputType.USER_ROLE);
        String userRole;
        for (; ; ) {
            try {
                String type = scanner.nextLine();
                switch (type) {
                    case "admin":
                    case "root":
                    case "teacher":
                    case "stud":
                        userRole = type;
                        break;
                    default:
                        throw new NonComplianceWithConstraints("user_role", "admin", "root", "teacher", "stud");
                }
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }

        return userRole;
    }

    public static String readUserGroup() {
        System.out.println(MenuInputType.USER_GROUP);
        String userGroup;
        for (; ; ) {
            try {
                userGroup = scanner.nextLine().toUpperCase();
                Matcher matcher = VALID_USER_GROUP_REGEX.matcher(userGroup);
                if (!matcher.matches()) {
                    throw new PatternException("user_group", VALID_USER_GROUP_REGEX);
                }
                break;
            } catch (PatternException exp) {
                System.out.println(exp.getMessage());
            }
        }
        return userGroup;
    }

    public static Users readUser() {
        Users user = new Users();

        user.setUsername(readUsername());
        user.setPassword(readPassword());

        return user;
    }

    public static String readUsername() {
        System.out.println(MenuInputType.USERNAME);
        String username;
        for (; ; ) {
            try {
                username = scanner.nextLine();
                if (username.length() >= 20) throw new Exception();
                break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                System.out.println("Repeat input: ");
            }
        }
        return username;
    }

    public static String readPassword() {
        System.out.println(MenuInputType.PASSWORD);
        String password;
        for (; ; ) {
            try {
                password = scanner.nextLine();
                if (password.length() >= 25) throw new Exception();
                break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
                System.out.println("Repeat input: ");
            }
        }
        return password;
    }

    public static BigDecimal readUserId() {
        BigDecimal userId = null;
        System.out.println(MenuInputType.USER_ID);
        for (; ; ) {
            String tempUserId = scanner.nextLine();
            try {
                userId = new BigDecimal(tempUserId);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }
        return userId;
    }

    public static BigDecimal readUserSessionId() {
        BigDecimal userSessionId = null;
        System.out.println(MenuInputType.BCOMP);
        for (; ; ) {
            String tempUserSessionId = scanner.nextLine();
            try {
                if (tempUserSessionId.length() > 20) {
                    throw new Exception();
                }
                userSessionId = new BigDecimal(tempUserSessionId);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }
        return userSessionId;
    }

    public static Bcomp readBcomp() {
        Bcomp bcomp = new Bcomp();

        System.out.println(MenuInputType.BCOMP);
//        for (; ; ) {
//            String tempUserSessionId = scanner.nextLine();
//            try {
//                if (tempUserSessionId.length() > 20) {
//                    throw new Exception();
//                }
//                userSessionId = new BigDecimal(tempUserSessionId);
//                break;
//            } catch (Exception exp) {
//                System.out.println("Repeat input: ");
//            }
//        }
        return bcomp;
    }
}

