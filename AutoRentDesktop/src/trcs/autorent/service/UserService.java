package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;
import trcs.autorent.sessionbean.IUser;

/**
 * Serviço de lógica de apresentação/cliente para a entidade User na consola.
 */
public class UserService {
	
	private IUser iUser;
	
	/**
     * Construtor que efetua a busca (lookup) remota do EJB UserSB.
     */
	public UserService() {
		iUser = new Lookup<IUser>().lookup("UserSB", "IUser");
	}
	
	/**
	 * Executa o processo de login solicitando credenciais ao utilizador via consola, 
	 * garantindo que apenas funcionários (EMPLOYEE) acedam ao sistema por esta via.
	 * 
	 * @return true se o login for efetuado com sucesso por um funcionário, false caso contrário
	 */
	public boolean login() {
        if (iUser == null) return false;
        
        String email = InputOutput.readString("E-mail:", Consts.AUTO_RENT);
		String password = InputOutput.readString("Senha:", Consts.AUTO_RENT);
		
        if (!iUser.login(email, password)) {
        	InputOutput.showInfo("Erro ao realizar login", Consts.AUTO_RENT);
        	return false;
        }
        
        if (iUser.getLoggedUser().getType() == UserType.CLIENT) {
        	InputOutput.showError("Permissão Negada", Consts.CREATE_USER);
        	return false;
        }	
       
        return true;
    }
	
	/**
	 * Solicita os dados ao utilizador e cria um novo cliente na consola.
	 * 
	 * @return true se cadastrado com sucesso, false caso contrário
	 */
    public boolean createUser() {
        if (iUser == null) return false;
        
    	String name = InputOutput.readString("Nome do cliente:", Consts.CREATE_USER);
		String cpf = InputOutput.readString("CPF:", Consts.CREATE_USER);
		String email = InputOutput.readString("E-mail:", Consts.CREATE_USER);
		String password = InputOutput.readString("Senha:", Consts.CREATE_USER);
		
        User user = new User(name, cpf, email, password, UserType.CLIENT);
        if (!iUser.create(user)) {
        	InputOutput.showError("Erro ao cadastrar cliente", Consts.CREATE_USER);
        	return false;
        }	
        
        InputOutput.showInfo("Cliente cadastrado com sucesso!", Consts.CREATE_USER);
        return true;
    }
    
    /**
     * Lista todos os utilizadores do tipo cliente na consola.
     * 
     * @return true se listados com sucesso, false caso contrário
     */
    public boolean listUsers() {
		List<User> usersList = iUser.getByType(UserType.CLIENT);
		if (usersList == null || usersList.isEmpty()) {
			InputOutput.showAlert("Nenhum cliente cadastrado", Consts.LIST_USERS);
			return false;
		}
		
		InputOutput.writeTextArea(null);
		InputOutput.writeTextArea("--- Usuários Cadastrados ---\n\n");
		for (User user : usersList) 
			InputOutput.writeTextArea(String.format("%s\n", user));
		
		InputOutput.showTextArea(InputOutput.getTextArea(), Consts.LIST_USERS);
		return true;
	}

}