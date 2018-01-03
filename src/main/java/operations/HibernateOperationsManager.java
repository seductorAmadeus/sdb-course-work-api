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
                registrationCodesOperations.addNewRegistrationCode();
                break;
            case 2:
                registrationCodesOperations.printAllRegistrationCodes();
                break;
            case 3:
                registrationCodesOperations.updateRegistrationCodeStatus();
                break;
            case 4:
                registrationCodesOperations.deleteRegistrationCode();
                break;
            case 5:
                usersOperations.addUser();
                break;
            case 6:
                System.out.println("Oops");
                break;
            case 7:
                System.out.println("Oops");
                break;
            case 8:
                usersOperations.deleteUser();
                break;
            case 9:
                userRoleOperations.generateAllUsersRoles();
                break;
            case 10:
                userStudyingOperations.addUserGroup();
                break;
            case 11:
                userSessionOperations.createUserSession();
                break;
            case 12:
                bcompOperations.jAddEmptyBcomp();
                break;
            case 13:
                bcompOperations.jPrintAllBcomp();
                break;
            case 14:
                bcompOperations.jUpdateBcomp();
                break;
            case 15:
                bcompOperations.jDeleteBcomp();
                break;
            case 16:
                bcompSettingsOperations.createBcompSettings();
                break;
            case 17:
                sessionSettingsOperations.assignUserSettings();
                break;
            case 18:
                specifiedOperations.getEmailFromSessionId();
                break;
            case 19:
                specifiedOperations.getSettingsIdForUser();
                break;
            case 20:
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
                .append(MenuInputType.UPDATE_REGISTRATION_CODES)
                .append(MenuInputType.DELETE_REGISTRATION_CODES)
                .append("\n<<< Users >>>\n")
                .append(MenuInputType.ADD_USER)
                .append(MenuInputType.PRINT_USERS)
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
                .append(MenuInputType.UPDATE_BCOMP)
                .append(MenuInputType.DELETE_BCOMP)
                .append("\n<<< Bcomp settings >>>\n")
                .append(MenuInputType.CREATE_BCOMP_SETTINGS)
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
