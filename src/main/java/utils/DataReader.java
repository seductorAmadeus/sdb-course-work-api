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

    public static Bcomp readBcomp() {
        Bcomp bcomp = new Bcomp();
        // TODO: need fix
//        System.out.println(MenuInputType.);
        for (; ; ) {
            String memory = scanner.nextLine();
            try {
//                if (memory.length() > 20) {
//                    throw new Exception();
//                }
                bcomp.setMemory(memory);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.RS);
        for (; ; ) {
            String rs = scanner.nextLine();
            try {
                if (rs.length() > 9) {
                    throw new Exception();
                }
                bcomp.setMemory(rs);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.RA);
        for (; ; ) {
            String ra = scanner.nextLine();
            try {
                if (ra.length() > 11) {
                    throw new Exception();
                }
                bcomp.setRa(ra);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.RD);
        for (; ; ) {
            String rd = scanner.nextLine();
            try {
                if (rd.length() > 16) {
                    throw new Exception();
                }
                bcomp.setRd(rd);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.RC);
        for (; ; ) {
            String rc = scanner.nextLine();
            try {
                if (rc.length() > 16) {
                    throw new Exception();
                }
                bcomp.setRc(rc);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.CC);
        for (; ; ) {
            String cc = scanner.nextLine();
            try {
                if (cc.length() > 11) {
                    throw new Exception();
                }
                bcomp.setCc(cc);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.BR);
        for (; ; ) {
            String br = scanner.nextLine();
            try {
                if (br.length() > 11) {
                    throw new Exception();
                }
                bcomp.setBr(br);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }


        System.out.println(MenuInputType.AC);
        for (; ; ) {
            String ac = scanner.nextLine();
            try {
                if (ac.length() > 16) {
                    throw new Exception();
                }
                bcomp.setAc(ac);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.C);
        for (; ; ) {
            String c = scanner.nextLine();
            try {
                if (c.length() > 1) {
                    throw new Exception();
                }
                bcomp.setC(c);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.KR);
        for (; ; ) {
            String kr = scanner.nextLine();
            try {
                if (kr.length() > 11) {
                    throw new Exception();
                }
                bcomp.setKr(kr);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.BIT);
        for (; ; ) {
            String bit = scanner.nextLine();
            try {
                if (bit.length() > 1) {
                    throw new Exception();
                }
                bcomp.setBit(bit);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.INT_REQ_ED_1);
        for (; ; ) {
            String intReqEd1 = scanner.nextLine();
            try {
                if (!intReqEd1.equals("set") && !intReqEd1.equals("unset")) {
                    throw new NonComplianceWithConstraints("int_req_ed_1", "set", "unset");
                }
                bcomp.setIntReqEd1(intReqEd1);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }

        System.out.println(MenuInputType.INT_REQ_ED_2);
        for (; ; ) {
            String intReqEd2 = scanner.nextLine();
            try {
                if (!intReqEd2.equals("set") && !intReqEd2.equals("unset")) {
                    throw new NonComplianceWithConstraints("int_req_ed_2", "set", "unset");
                }
                bcomp.setIntReqEd2(intReqEd2);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }

        System.out.println(MenuInputType.INT_REQ_ED_3);
        for (; ; ) {
            String intReqEd3 = scanner.nextLine();
            try {
                if (!intReqEd3.equals("set") && !intReqEd3.equals("unset")) {
                    throw new NonComplianceWithConstraints("int_req_ed_3", "set", "unset");
                }
                bcomp.setIntReqEd3(intReqEd3);
                break;
            } catch (NonComplianceWithConstraints exp) {
                System.out.println(exp.getMessage());
            }
        }

        System.out.println(MenuInputType.RD_ED_1);
        for (; ; ) {
            String rdEd1 = scanner.nextLine();
            try {
                if (rdEd1.length() > 8) {
                    throw new Exception();
                }
                bcomp.setRdEd1(rdEd1);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.RD_ED_2);
        for (; ; ) {
            String rdEd2 = scanner.nextLine();
            try {
                if (rdEd2.length() > 8) {
                    throw new Exception();
                }
                bcomp.setRdEd2(rdEd2);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.RD_ED_3);
        for (; ; ) {
            String rdEd3 = scanner.nextLine();
            try {
                if (rdEd3.length() > 8) {
                    throw new Exception();
                }
                bcomp.setRdEd3(rdEd3);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.MEMORY_MC);
        for (; ; ) {
            String memoryMc = scanner.nextLine();
            try {
//                if (memory.length() > 20) {
//                    throw new Exception();
//                }
                bcomp.setMemoryMc(memoryMc);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.C_MC);
        for (; ; ) {
            String cMc = scanner.nextLine();
            try {
                if (cMc.length() > 8) {
                    throw new Exception();
                }
                bcomp.setcMc(cMc);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.R_MC);
        for (; ; ) {
            String rMc = scanner.nextLine();
            try {
                if (rMc.length() > 16) {
                    throw new Exception();
                }
                bcomp.setrMc(rMc);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.ASM);
        for (; ; ) {
            String asm = scanner.nextLine();
            try {
//                if (asm.length() > 16) {
//                    throw new Exception();
//                }
                bcomp.setAsm(asm);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        return bcomp;
    }

    public static String readNewStatusForRegistrationCode() {
        RegistrationCodes registrationCodes = new RegistrationCodes();
        System.out.println(MenuInputType.INVITE_CODE_STATUS);
        readStatus(registrationCodes);
        return registrationCodes.getInviteCodeStatus();
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

    public static UserPicture readUserPicture() {
        UserPicture userPicture = new UserPicture();
        System.out.println(MenuInputType.USER_PICTURE_PICNAME);
        for (; ; ) {
            String picname = scanner.nextLine();
            try {
                if (picname.length() > 20) {
                    throw new Exception();
                }
                userPicture.setPictureName(picname);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        return userPicture;
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

    public static String readString(Class aClass, String propertyName, MenuInputType inputType) {
        String value;
        for (; ; ) {
            try {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();

                System.out.println(inputType);
                value = scanner.nextLine();

                Set<ConstraintViolation<?>> errors = validator.validateValue(aClass, propertyName, value);

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

        System.out.println(MenuInputType.BCOMP_SETTINGS_TYPE);
        for (; ; ) {
            String bcompSettingsType = scanner.nextLine();
            try {
                if (bcompSettingsType.length() > 20) {
                    throw new Exception();
                }
                bcompSettings.setType(bcompSettingsType);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        System.out.println(MenuInputType.BCOMP_SETTINGS_VALUE);
        for (; ; ) {
            String bcompSettingsValue = scanner.nextLine();
            try {
                if (bcompSettingsValue.length() > 20) {
                    throw new Exception();
                }
                bcompSettings.setValue(bcompSettingsValue);
                break;
            } catch (Exception exp) {
                System.out.println("Repeat input: ");
            }
        }

        return bcompSettings;
    }

    public static void initUserProfile(UserProfile userProfile) {
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
    }

    public static void initBcomp(Bcomp bcomp) {

    }
}

