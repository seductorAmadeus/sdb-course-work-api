package daos;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    PK create(T newInstance);

    T read(PK id);

    void update(T transientObject);

    void delete(PK id);

    boolean isExists(PK id);

    List<T> getList();
}
