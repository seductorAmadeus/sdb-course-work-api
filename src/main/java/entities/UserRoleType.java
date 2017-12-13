package entities;

import org.hibernate.HibernateException;
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

    /**
     * Creates the custom object from the data returned by resultset
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
            throws HibernateException, SQLException {
        UserT phone = null;

        String nameVal = rs.getString(names[0]);
        if (nameVal != null) {
            phone = new UserT();

            StringTokenizer tokenizer = new StringTokenizer(nameVal, "-");
            phone.setTeacher(tokenizer.nextToken());
            phone.setStud(tokenizer.nextToken());
            phone.setRoot(tokenizer.nextToken());
            phone.setAdmin(tokenizer.nextToken());

        }
        return phone;
    }

    /**
     * Converts custom object into value which needs to be passed to prepared statement
     */
    public void nullSafeSet(PreparedStatement st, Object value, int index)
            throws HibernateException, SQLException {

        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            st.setString(index, ((UserT) value).toString());
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
