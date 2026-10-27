package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.User;

/**
 * Interface remota responsável pelos serviços de autenticação e gestão de sessão de utilizadores.
 */
@Remote
public interface IAuth {
	
	/**
	 * Realiza a autenticação do utilizador com base no e-mail e password, retornando o objeto do utilizador autenticado.
	 * 
	 * @param email O e-mail do utilizador
	 * @param password A password do utilizador
	 * @return O objeto {@link User} correspondente se as credenciais forem válidas, ou null caso contrário
	 */
	User loginAndReturn(String email, String password);
	
	/**
	 * Verifica as credenciais de login e retorna um valor booleano indicando o sucesso da operação.
	 * 
	 * @param email O e-mail do utilizador
	 * @param password A password do utilizador
	 * @return true se a autenticação for bem-sucedida, false caso contrário
	 */
	boolean login(String email, String password);
	
	/**
	 * Encerra a sessão atual do utilizador autenticado.
	 */
	void logout();
	
	/**
	 * Retorna o utilizador que se encontra atualmente autenticado na sessão.
	 * 
	 * @return O {@link User} logado, ou null se não houver sessão ativa
	 */
	User getLoggedUser();
}