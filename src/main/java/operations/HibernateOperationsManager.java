package operations;

import utils.HibernateUtil;
import utils.MenuInputType;

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

        int menuNumber = Integer.parseInt(scanner.nextLine());

        switch (menuNumber) {
            case 1:
                registrationCodesOperations.jAdd();
                break;
            case 2:
                registrationCodesOperations.printAll();
                break;
            case 3:
                registrationCodesOperations.jPrint();
                break;
            case 4:
                registrationCodesOperations.update();
                break;
            case 5:
                registrationCodesOperations.delete();
                break;
            case 6:
                usersOperations.addUser();
                break;
            case 7:
                System.out.println("Oops");
                break;
            case 8:
                System.out.println("Oops");
                break;
            case 9:
                System.out.println("Oops");
                break;
            case 10:
                usersOperations.deleteUser();
                break;
            case 11:
                userRoleOperations.generateAllUsersRoles();
                break;
            case 12:
                userStudyingOperations.addUserGroup();
                break;
            case 13:
                userSessionOperations.createUserSession();
                break;
            case 14:
                bcompOperations.jAdd();
                break;
            case 15:
                bcompOperations.jPrintAll();
                break;
            case 16:
                bcompOperations.jPrint();
                break;
            case 17:
                bcompOperations.jUpdate();
                break;
            case 18:
                bcompOperations.jDelete();
                break;
            case 19:
                bcompSettingsOperations.jAdd();
                break;
            case 20:
                bcompSettingsOperations.jPrintAll();
                break;
            case 21:
                bcompSettingsOperations.jPrint();
                break;
            case 22:
                bcompSettingsOperations.jUpdate();
                break;
            case 23:
                bcompSettingsOperations.jDelete();
                break;
            case 24:
                sessionSettingsOperations.assignUserSettings();
                break;
            case 25:
                specifiedOperations.getEmailFromSessionId();
                break;
            case 26:
                specifiedOperations.getSettingsIdForUser();
                break;
            case 27:
                // TODO: move to separate file
                usersOperations.updateUserProfile();
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
                .append("\n<<< User session>>>\n")
                .append(MenuInputType.CREATE_USER_SESSION)
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
                .append(MenuInputType.DELETE_BCOMP_SETING)
                .append("\n<<< Session settings >>>\n")
                .append(MenuInputType.ASSING_USER_SETTINGS)
                .append("\n<<< Separate functions >>>\n")
                .append(MenuInputType.GET_EMAIL_USING_SESSION_ID)
                .append(MenuInputType.GET_SETTINGS_ID_FOR_USER)
                .append("\n<<< User profile >>>\n")
                .append(MenuInputType.UPDATE_USER_PROFILE)
                .toString();
    }
}
