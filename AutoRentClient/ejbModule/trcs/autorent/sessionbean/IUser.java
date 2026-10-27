package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;

/**
 * Interface remota para a gestão de utilizadores, herdando operações de CRUD e autenticação.
 */
@Remote
public interface IUser extends Crud<User>, IAuth {
	
	/**
	 * Retorna uma lista de utilizadores filtrados pelo seu tipo (ex: Cliente ou Funcionário).
	 * 
	 * @param type O tipo de utilizador ({@link UserType})
	 * @return Lista de utilizadores do tipo especificado
	 */
    List<User> getByType(UserType type);
    
    /**
     * Verifica se o utilizador autenticado possui privilégios administrativos.
     * 
     * @return true se for administrador/funcionário, false caso contrário
     */
    boolean isAdmin();
}