package entities;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class UserT implements SQLData {
    private String root;
    private String admin;
    private String teacher;
    private String stud;
    private String sql_type;

    public String getSQLTypeName() {
        return sql_type;
    }

    public void readSQL(SQLInput stream, String type)
            throws SQLException {
        sql_type = type;
        root = stream.readString();
        admin = stream.readString();
        teacher = stream.readString();
        stud = stream.readString();
    }

    public void writeSQL(SQLOutput stream)
            throws SQLException {
        stream.writeString(root);
        stream.writeString(admin);
        stream.writeString(teacher);
        stream.writeString(stud);
    }
}
