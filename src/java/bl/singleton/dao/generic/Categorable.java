package bl.singleton.dao.generic;

public interface Categorable<T extends CharSequence> {
    public T getCategory();
    public void setCategory(T t);
}
