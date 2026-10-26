package trcs.autorent.managedbean;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;
import trcs.autorent.sessionbean.IUser;

@SessionScoped
@Named("userMB")
public class UserMB implements Serializable {
	/** */
	private static final long serialVersionUID = 1L;
	
    @EJB
    private IUser iUser; // Focado nas operações de cliente (cadastro)
	
	// Objeto ligado aos campos da tela.
    private User user;
    
    // Armazena o usuário autenticado na sessão HTTP do JSF
    private User loggedUser; 
    
    @PostConstruct
    public void init() {
        this.user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String login() {
		// Valida no EJB
		User loggedUser = iUser.loginAndReturn(user.getEmail(), user.getPasswordHash());
		
		if (loggedUser != null) {
			this.loggedUser = loggedUser; // Salva na sessão web
			this.user = new User(); // Limpa o formulário
			return "dashboard?faces-redirect=true"; // Redireciona para a tela correta
		}

		return null;
	}
	
	public String signin() {
		user.setType(UserType.CLIENT);
		iUser.create(user);
		this.user = new User();
		return "login?faces-redirect=true";
	}
	
	public String logout() {
		this.loggedUser = null;
		this.user = new User();
		return "login?faces-redirect=true";
	}
	
}
