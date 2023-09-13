package analytics.model;

import java.util.HashMap;

public interface DAO<T> {
    HashMap<String, T> retrieveAll();
    
    T retrieve(String str);
    
    void create(T t);
    
    void update(T t);
        
//    https://www.baeldung.com/java-dao-pattern
}
