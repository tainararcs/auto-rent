package trcs.autorent.sessionbean;

import java.util.List;

public interface Crud<T> {
	boolean create(T t);
	boolean update(T t);
	boolean remove(Long id);
	T findById(Long id);
	List<T> listAll();
}
