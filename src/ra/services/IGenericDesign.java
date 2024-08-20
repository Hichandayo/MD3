package ra.services;

import java.util.List;

public interface IGenericDesign <T>{
    void create(T t);
    T read(String id);
    void update(T t);
    void delete(String id);
    List<T> getAll();
}