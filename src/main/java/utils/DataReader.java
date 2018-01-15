package utils;

import entities.*;
import enums.MenuInputType;
import exceptions.NonComplianceWithConstraints;
import exceptions.PatternException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;
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
        // generate invite code
        RandomInviteCodesGenerator inviteCodesGenerator = new RandomInviteCodesGenerator();
        registrationCodes.setInviteCode(inviteCodesGenerator.getInviteCode());
        registrationCodes.setInviteCodeStatus(DataReader.readString(RegistrationCodes.class, "inviteCodeStatus", MenuInputType.INVITE_CODE_STATUS));
//        System.out.println(MenuInputType.EMAIL);
//
//        for (; ; ) {
//            try {
//                String email = scanner.nextLine();
//                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
//                if (!matcher.find()) {
//                    throw new PatternException("email", VALID_EMAIL_ADDRESS_REGEX);
//                }
//                registrationCodes.setEmail(email);
//                break;
//            } catch (PatternException exp) {
//                System.out.println(exp.getMessage());
//                System.out.println("Repeat input: ");
//            }
//        }
        registrationCodes.setEmail(DataReader.readString(RegistrationCodes.class, "email", MenuInputType.EMAIL));
        return registrationCodes;
    }

    public static Bcomp readBcomp() {
        Bcomp bcomp = new Bcomp();

        bcomp.setMemory(DataReader.readString(Bcomp.class, "memory", MenuInputType.MEMORY));
        bcomp.setRs(DataReader.readString(Bcomp.class, "rs", MenuInputType.RS));
        bcomp.setRa(DataReader.readString(Bcomp.class, "ra", MenuInputType.RA));
        bcomp.setRd(DataReader.readString(Bcomp.class, "rd", MenuInputType.RD));
        bcomp.setRc(DataReader.readString(Bcomp.class, "rc", MenuInputType.RC));
        bcomp.setCc(DataReader.readString(Bcomp.class, "cc", MenuInputType.CC));
        bcomp.setBr(DataReader.readString(Bcomp.class, "br", MenuInputType.BR));
        bcomp.setAc(DataReader.readString(Bcomp.class, "ac", MenuInputType.AC));
        bcomp.setC(DataReader.readString(Bcomp.class, "c", MenuInputType.C));
        bcomp.setKr(DataReader.readString(Bcomp.class, "kr", MenuInputType.KR));
        bcomp.setBit(DataReader.readString(Bcomp.class, "bit", MenuInputType.BIT));
        bcomp.setIntReqEd1(DataReader.readString(Bcomp.class, "intReqEd1", MenuInputType.INT_REQ_ED_1));
        bcomp.setIntReqEd2(DataReader.readString(Bcomp.class, "intReqEd2", MenuInputType.INT_REQ_ED_2));
        bcomp.setIntReqEd3(DataReader.readString(Bcomp.class, "intReqEd3", MenuInputType.INT_REQ_ED_3));
        bcomp.setRdEd1(DataReader.readString(Bcomp.class, "rdEd1", MenuInputType.RD_ED_1));
        bcomp.setRdEd2(DataReader.readString(Bcomp.class, "rdEd2", MenuInputType.RD_ED_2));
        bcomp.setRdEd3(DataReader.readString(Bcomp.class, "rdEd3", MenuInputType.RD_ED_3));
        bcomp.setMemoryMc(DataReader.readString(Bcomp.class, "memoryMc", MenuInputType.MEMORY_MC));
        bcomp.setcMc(DataReader.readString(Bcomp.class, "cMc", MenuInputType.C_MC));
        bcomp.setrMc(DataReader.readString(Bcomp.class, "rMc", MenuInputType.R_MC));
        bcomp.setAsm(DataReader.readString(Bcomp.class, "asm", MenuInputType.ASM));

        return bcomp;
    }

    public static String readUserSessionStatus() {
        UserSession userSession = new UserSession();
        System.out.println(MenuInputType.USER_SESSION_STATUS);
        for (; ; ) {
            try {
                String userSessionStatus = scanner.nextLine();
                if (!userSessionStatus.equals("active") && !userSessionStatus.equals("inactive")) {
                    throw new NonComplianceWithConstraints("status", "active", "inactive");
                }
                userSession.setStatus(userSessionStatus);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }
        return userSession.getStatus();
    }

    public static UserProfile readUserProfile() {
        UserProfile userProfile = new UserProfile();
        System.out.println(MenuInputType.STUDYING_STATUS);

        userProfile.setStudyingStatus(DataReader.readString(UserProfile.class, "studyingStatus", MenuInputType.STUDYING_STATUS));
        userProfile.setFirstName(DataReader.readString(UserProfile.class, "firstName", MenuInputType.FIRST_NAME));
        userProfile.setLastName(DataReader.readString(UserProfile.class, "lastName", MenuInputType.LAST_NAME));
        userProfile.setGender(DataReader.readString(UserProfile.class, "gender", MenuInputType.GENDER));

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

        user.setUsername(DataReader.readString(Users.class, "username", MenuInputType.USERNAME));
        user.setPassword(DataReader.readString(Users.class, "password", MenuInputType.PASSWORD));

        return user;
    }

    public static UserPicture readUserPicture() {
        UserPicture userPicture = new UserPicture();
        userPicture.setPictureName(DataReader.readString(UserPicture.class, "pictureName", MenuInputType.USER_PICTURE_PICNAME));
        return userPicture;
    }

    public static String readString(Class aClass, String fieldName, MenuInputType inputType) {
        String value;
        for (; ; ) {
            try {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();

                System.out.println(inputType);
                value = scanner.nextLine();

                Set<ConstraintViolation<?>> errors = validator.validateValue(aClass, fieldName, value);

                if (!errors.isEmpty()) {
                    for (ConstraintViolation<?> constraintViolation : errors) {
                        System.out.println(constraintViolation.getPropertyPath() + " -> " +
                                constraintViolation.getMessage());
                    }
                } else break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
        return value;
    }

    public static BigDecimal readId(Class aClass, String fieldName, MenuInputType inputType) {
        BigDecimal id = null;
        try {
            id = readBigDecimal(aClass, fieldName, inputType);
        } catch (NoSuchFieldException e) {
            //TODO: check @notNull if fieldName doesn't exists
            System.out.println(fieldName + " -> " + "field is missing");
        }
        return id;
    }

    public static BigDecimal readBigDecimal(Class aClass, String fieldName, MenuInputType inputType) throws NoSuchFieldException {
        BigDecimal bigDecimal = null;
        String tempInput;
        for (; ; ) {
            try {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();

                System.out.println(inputType);
                tempInput = scanner.nextLine();

                Set<ConstraintViolation<?>> errors = validator.validateValue(aClass, fieldName, new BigDecimal(tempInput));

                if (!errors.isEmpty()) {
                    for (ConstraintViolation<?> constraintViolation : errors) {
                        System.out.println(constraintViolation.getPropertyPath() + " -> " +
                                constraintViolation.getMessage());
                    }
                } else {
                    bigDecimal = new BigDecimal(tempInput);
                }
                break;
            } catch (NumberFormatException exp) {
                System.out.println(fieldName + " -> must be " + aClass.getDeclaredField(fieldName).getType());
            }
        }
        return bigDecimal;
    }

    public static BcompSettings readBcompSettings() {
        BcompSettings bcompSettings = new BcompSettings();
        bcompSettings.setType(DataReader.readString(BcompSettings.class, "type", MenuInputType.BCOMP_SETTINGS_TYPE));
        bcompSettings.setValue(DataReader.readString(BcompSettings.class, "value", MenuInputType.BCOMP_SETTINGS_VALUE));
        return bcompSettings;
    }

    public static void initUserProfile(UserProfile userProfile) {
        userProfile.setStudyingStatus(DataReader.readString(UserProfile.class, "studyingStatus", MenuInputType.STUDYING_STATUS));
        userProfile.setFirstName(DataReader.readString(UserProfile.class, "firstName", MenuInputType.FIRST_NAME));
        userProfile.setLastName(DataReader.readString(UserProfile.class, "lastName", MenuInputType.LAST_NAME));
        userProfile.setGender(DataReader.readString(UserProfile.class, "gender", MenuInputType.GENDER));

        for (; ; ) {
            try {
                System.out.println(MenuInputType.DATE_OF_BIRTH);
                String dateOfBirth = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                Date parsedDate = dateFormat.parse(dateOfBirth);
                Timestamp timestamp = new Timestamp(parsedDate.getTime());
                userProfile.setDateOfBirth(timestamp);
                break;
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    public static void initBcomp(Bcomp bcomp) {

    }
}

