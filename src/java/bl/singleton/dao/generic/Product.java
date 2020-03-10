package bl.singleton.dao.generic;

import java.sql.SQLException;
import java.util.Collection;

public interface Product<T extends Keyable> {

    public void create(T t) throws SQLException;

    public void delete(T t) throws SQLException;

    public void update(T t) throws SQLException;

    public T find(T t);

    public Collection<T> findAll();

}
