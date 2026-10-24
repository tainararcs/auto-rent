package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;
import trcs.autorent.sessionbean.IUser;
import trcs.autorent.util.Utils;

public class UserService {
	/** */
	private IUser iUser;
	
	public UserService() {
		iUser = new Lookup<IUser>().lookup("UserSB", "IUser");
	}
	
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
	
    public boolean createUser() {
        if (iUser == null) return false;
        
    	String name = InputOutput.readString("Nome do cliente:", Consts.CREATE_USER);
		String cpf = InputOutput.readString("CPF:", Consts.CREATE_USER);
		String email = InputOutput.readString("E-mail:", Consts.CREATE_USER);
		String password = InputOutput.readString("Senha:", Consts.CREATE_USER);
		
        User user = new User(name, cpf, email, Utils.getHashPassword(password), UserType.CLIENT);
        if (!iUser.create(user)) {
        	InputOutput.showError("Erro ao cadastrar cliente", Consts.CREATE_USER);
        	return false;
        }	
        
        InputOutput.showInfo("Cliente cadastrado com sucesso!", Consts.CREATE_USER);
        return true;
    }
    
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
