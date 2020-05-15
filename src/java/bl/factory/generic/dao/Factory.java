package bl.factory.generic.dao;

import bl.singleton.dao.generic.Keyable;
import bl.singleton.dao.generic.Product;

public abstract class Factory<T extends Keyable> {
    public abstract Product<T> createDao();
}
