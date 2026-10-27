package trcs.autorent.sessionbean;

import java.util.List;

/**
 * Interface genérica para operações de CRUD (Create, Read, Update, Delete).
 * 
 * @param <T> O tipo da entidade a ser gerida
 */
public interface Crud<T> {
	
	/**
	 * Persiste uma nova entidade no sistema.
	 * 
	 * @param t A entidade a ser criada
	 * @return true se a operação for bem-sucedida, false caso contrário
	 */
	boolean create(T t);
	
	/**
	 * Atualiza os dados de uma entidade existente.
	 * 
	 * @param t A entidade com os dados atualizados
	 * @return true se a operação for bem-sucedida, false caso contrário
	 */
	boolean update(T t);
	
	/**
	 * Remove uma entidade do sistema com base no seu identificador único.
	 * 
	 * @param id O identificador único da entidade
	 * @return true se a remoção for bem-sucedida, false caso contrário
	 */
	boolean remove(Long id);
	
	/**
	 * Busca uma entidade pelo seu identificador único.
	 * 
	 * @param id O identificador único
	 * @return A entidade encontrada ou null se não existir
	 */
	T findById(Long id);
	
	/**
	 * Lista todas as entidades cadastradas.
	 * 
	 * @return Uma lista com todas as entidades
	 */
	List<T> listAll();
}