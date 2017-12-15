package operations;

import utils.MenuInputType;

import java.util.Scanner;

public class HibernateOperationsManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(getMenu());

        RegistrationCodesOperations registrationCodesOperations = new RegistrationCodesOperations();
        UsersOperations usersOperations = new UsersOperations();

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
                usersOperations.addNewUser();
                break;
            default:
                break;
        }
    }

    private static String getMenu() {
        return new StringBuilder(MenuInputType.ENTER_REGISTRATION_CODES.toString())
                .append(MenuInputType.PRINT_REGISTRATION_CODES)
                .append(MenuInputType.UPDATE_REGISTRATION_CODES)
                .append(MenuInputType.DELETE_REGISTRATION_CODES)
                .append(MenuInputType.ENTER_USERS)
                .toString();
    }

}
