package operations;

import enums.MenuInputType;
import utils.HibernateUtil;

import java.util.Scanner;

public class HibernateOperationsManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(getMenu());

        RegistrationCodesOperations registrationCodesOperations = new RegistrationCodesOperations();
        UsersOperations usersOperations = new UsersOperations();
        UserRoleOperations userRoleOperations = new UserRoleOperations();
        UserStudyingOperations userStudyingOperations = new UserStudyingOperations();
        UserSessionOperations userSessionOperations = new UserSessionOperations();
        BcompOperations bcompOperations = new BcompOperations();
        BcompSettingsOperations bcompSettingsOperations = new BcompSettingsOperations();
        SessionSettingsOperations sessionSettingsOperations = new SessionSettingsOperations();
        SpecifiedOperations specifiedOperations = new SpecifiedOperations();
        UserProfileOperations userProfileOperations = new UserProfileOperations();


        int menuNumber = Integer.parseInt(scanner.nextLine());

        switch (menuNumber) {
            case 1:
                registrationCodesOperations.jCreate();
                break;
            case 2:
                registrationCodesOperations.jPrintAll();
                break;
            case 3:
                registrationCodesOperations.jPrint();
                break;
            case 4:
                registrationCodesOperations.jUpdate();
                break;
            case 5:
                registrationCodesOperations.jDelete();
//TODO: check NumberFormatException from synchronize
                //                registrationCodesOperations.synchronize();
                break;
            case 6:
                usersOperations.jCreate();
                break;
            case 7:
                usersOperations.jPrintAll();
                break;
            case 8:
                usersOperations.jPrint();
                break;
            case 9:
                usersOperations.jUpdate();
                break;
            case 10:
                usersOperations.jDelete();
                usersOperations.synchronize();
                break;
            case 11:
                userRoleOperations.generateAllUsersRoles();
                break;
            case 12:
                userStudyingOperations.jCreate();
                break;
            case 13:
                userStudyingOperations.jPrintAll();
                break;
            case 14:
                userStudyingOperations.jPrint();
                break;
            case 15:
                userStudyingOperations.jDelete();
                userStudyingOperations.synchronize();
                break;
            case 16:
                userSessionOperations.jCreate();
                break;
            case 17:
                userSessionOperations.jPrintAll();
                break;
            case 18:
                userSessionOperations.jPrint();
                break;
            case 19:
                userSessionOperations.jUpdate();
                break;
            case 20:
                userSessionOperations.jDelete();
                userSessionOperations.synchronize();
                break;
            case 21:
                bcompOperations.jCreate();
                break;
            case 22:
                bcompOperations.jPrintAll();
                break;
            case 23:
                bcompOperations.jPrint();
                break;
            case 24:
                bcompOperations.jUpdate();
                break;
            case 25:
                bcompOperations.jDelete();
                bcompOperations.synchronize();
                break;
            case 26:
                bcompSettingsOperations.jCreate();
                break;
            case 27:
                bcompSettingsOperations.jPrintAll();
                break;
            case 28:
                bcompSettingsOperations.jPrint();
                break;
            case 29:
                bcompSettingsOperations.jUpdate();
                break;
            case 30:
                bcompSettingsOperations.jDelete();
                bcompSettingsOperations.synchronize();
                break;
            case 31:
                sessionSettingsOperations.assignUserSettings();
                break;
            case 32:
                specifiedOperations.getEmailFromSessionId();
                break;
            case 33:
                specifiedOperations.getSettingsIdForUser();
                break;
            case 34:
                userProfileOperations.jCreate();
                break;
            case 35:
                userProfileOperations.jPrintAll();
                break;
            case 36:
                userProfileOperations.jPrint();
                break;
            case 37:
                userProfileOperations.jUpdate();
                break;
            case 38:
                userProfileOperations.jDelete();
                userProfileOperations.synchronize();
                usersOperations.synchronize();
                break;
            default:
                break;
        }
        HibernateUtil.getSessionFactory().close();
    }

    private static String getMenu() {
        return new StringBuilder()
                .append("<<< Registration codes >>>\n")
                .append(MenuInputType.ADD_REGISTRATION_CODES)
                .append(MenuInputType.PRINT_REGISTRATION_CODES)
                .append(MenuInputType.GET_REGISTRATION_CODE_BY_ID)
                .append(MenuInputType.UPDATE_REGISTRATION_CODES)
                .append(MenuInputType.DELETE_REGISTRATION_CODES)
                .append("\n<<< Users >>>\n")
                .append(MenuInputType.ADD_USER)
                .append(MenuInputType.PRINT_USERS)
                .append(MenuInputType.GET_USER_BY_ID)
                .append(MenuInputType.UPDATE_USER)
                .append(MenuInputType.DELETE_USER)
                .append("\n<<< User roles >>>\n")
                .append(MenuInputType.GENERATE_USER_ROLE)
                .append("\n<<< User studying>>>\n")
                .append(MenuInputType.ADD_USER_GROUP)
                .append(MenuInputType.PRINT_USER_GROUP)
                .append(MenuInputType.GET_USER_GROUP_BY_ID)
                .append(MenuInputType.DELETE_USER_GROUP)
                .append("\n<<< User session>>>\n")
                .append(MenuInputType.CREATE_USER_SESSION)
                .append(MenuInputType.PRINT_USER_SESSION)
                .append(MenuInputType.GET_USER_SESSION_BY_ID)
                .append(MenuInputType.UPDATE_USER_SESSION)
                .append(MenuInputType.DELETE_USER_SESSION)
                .append("\n<<< Bcomp >>>\n")
                .append(MenuInputType.ADD_BCOMP)
                .append(MenuInputType.PRINT_BCOMPS)
                .append(MenuInputType.GET_BCOMP_BY_ID)
                .append(MenuInputType.UPDATE_BCOMP)
                .append(MenuInputType.DELETE_BCOMP)
                .append("\n<<< Bcomp settings >>>\n")
                .append(MenuInputType.CREATE_BCOMP_SETTINGS)
                .append(MenuInputType.PRINT_BCOMP_SETTINGS)
                .append(MenuInputType.GET_BCOMP_SETTING_BY_ID)
                .append(MenuInputType.UPDATE_BCOMP_SETTING)
                .append(MenuInputType.DELETE_BCOMP_SETTING)
                .append("\n<<< Session settings >>>\n")
                .append(MenuInputType.ASSIGN_USER_SETTINGS)
                .append("\n<<< Additional functions >>>\n")
                .append(MenuInputType.GET_EMAIL_USING_SESSION_ID)
                .append(MenuInputType.GET_SETTINGS_ID_FOR_USER)
                .append("\n<<< User profile >>>\n")
                .append(MenuInputType.ADD_USER_PROFILE)
                .append(MenuInputType.PRINT_USER_PROFILE)
                .append(MenuInputType.GET_USER_PROFILE_BY_ID)
                .append(MenuInputType.UPDATE_USER_PROFILE)
                .append(MenuInputType.DELETE_USER_PROFILE)
                .toString();
    }
}
