package daos;

import oracle.jdbc.internal.OracleTypes;
import utils.ConnectionJDBC;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PackageFunctions {

    public String getEmailFromFromSessionId(BigDecimal sessionId) {
        String email = null;
        Connection connection = null;
        ConnectionJDBC connectionHandler = new ConnectionJDBC();
        ResultSet resultSet;

        try {
            connection = connectionHandler.createConnection();

            CallableStatement callableStatement = connection.prepareCall("{? = call READPCKG.GETEMAILFROMSESS_ID(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.setBigDecimal(2, sessionId);
            callableStatement.execute();

            resultSet = (ResultSet) callableStatement.getObject(1);
            while (resultSet.next()) {
                email = resultSet.getString("email");
            }

        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            connectionHandler.close(connection);
        }

        return email;
    }
}
