package utils;

import daos.RegistrationCodesDAO;
import entities.RegistrationCodes;

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
                break;
        }
    }

    private static void addNewRegistrationCode() {
        RegistrationCodesDAO dao = new RegistrationCodesDAO();
        RegistrationCodes registrationCodes = DataReader.readRegistrationCode();
        // TODO: Remove print action!
        System.out.println((dao.addRegistrationCode(registrationCodes)));
    }

    private static String getMenu() {
        return new StringBuilder(MenuInputType.ENTER_REGISTRATION_CODES.toString())
                .append(MenuInputType.ENTER_SMTH_)
                .toString();
    }

}
