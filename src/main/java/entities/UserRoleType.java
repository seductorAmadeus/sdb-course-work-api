package entities;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.StringTokenizer;

public class UserRoleType implements UserType {
    /**
     * What column types to map,data type of the column
     */
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    /**
     * Class  details of object which is going to be used
     */
    public Class returnedClass() {
        return UserT.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }

    public int hashCode(Object x) throws HibernateException {
        if (x != null)
            return x.hashCode();
        else
            return 0;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        UserT userT = null;

        String nameVal = resultSet.getString(strings[0]);
        if (nameVal != null) {
            userT = new UserT();

            StringTokenizer tokenizer = new StringTokenizer(nameVal, "-");
            userT.setTeacher(tokenizer.nextToken());
            userT.setStud(tokenizer.nextToken());
            userT.setRoot(tokenizer.nextToken());
            userT.setAdmin(tokenizer.nextToken());

        }
        return userT;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.VARCHAR);
        } else {
            preparedStatement.setString(i, ((UserT) o).toString());
        }

    }

    /**
     * Returns deep copy of object
     */
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
        else {
            UserT newObj = new UserT();
            UserT existObj = (UserT) value;

            newObj.setAdmin(existObj.getAdmin());
            newObj.setRoot(existObj.getRoot());
            newObj.setStud(existObj.getStud());
            newObj.setTeacher(existObj.getTeacher());

            return newObj;
        }

    }

    public boolean isMutable() {
        return false;
    }

    /**
     *
     */
    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable))
            return (Serializable) deepCopy;

        return null;
    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return deepCopy(cached);
        //return cached;
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }
}
