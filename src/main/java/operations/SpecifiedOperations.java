package operations;

import daos.PackageFunctions;
import utils.DataReader;

import java.math.BigDecimal;

public class SpecifiedOperations {

    public void getEmailFromSessionId() {
        PackageFunctions packageFunctions = new PackageFunctions();
        BigDecimal userSessinId = DataReader.readUserSessionId();
        // TODO: check for null
        System.out.println(packageFunctions.getEmailFromFromSessionId(userSessinId));

    }
}
