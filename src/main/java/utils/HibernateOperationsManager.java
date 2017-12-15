package utils;

import daos.RegistrationCodesDAO;
import entities.RegistrationCodes;

import java.util.List;
import java.util.Scanner;

public class HibernateOperationsManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(getMenu());

        int menuNumber = Integer.parseInt(scanner.nextLine());

        switch (menuNumber) {
            case 1:
                addNewRegistrationCode();
                break;
            case 2:
                printAllRegistrationCodes();
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    private static void addNewRegistrationCode() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        RegistrationCodes registrationCodes = DataReader.readRegistrationCode();
        // TODO: Remove print action!
        System.out.println((dao.addRegistrationCode(registrationCodes)));
    }

    private static void printAllRegistrationCodes() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        List<RegistrationCodes> tempList = dao.listRegistrationCodes();
        System.out.println("invite_code" + " " + "invite_code_status" + " " + "email");
        for (RegistrationCodes registrationCodes : tempList) {
            System.out.println(registrationCodes.toString());
        }
    }

    private static String getMenu() {
        return new StringBuilder(MenuInputType.ENTER_REGISTRATION_CODES.toString())
                .append(MenuInputType.PRINT_REGISTRATION_CODES)
                .append(MenuInputType.PRINT_REGISTRATION_CODES)
                .toString();
    }

}
