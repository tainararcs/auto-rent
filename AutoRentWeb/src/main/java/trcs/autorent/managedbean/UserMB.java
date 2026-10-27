package trcs.autorent.managedbean;

import java.io.Serializable;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;
import trcs.autorent.sessionbean.IUser;

/**
 * Managed Bean com escopo de sessão para gerir a autenticação e o registo de utilizadores no JSF.
 */
@SessionScoped
@Named("userMB")
public class UserMB implements Serializable {
	
	/** Constante de serialização. */
	private static final long serialVersionUID = 1L;
	
    @EJB
    private IUser iUser;
	
    private User user;
    private User loggedUser; 
    
    /**
     * Inicializa uma nova instância de utilizador para os formulários.
     */
    @PostConstruct
    public void init() {
        this.user = new User();
    }

    /** Retorna o utilizador do formulário. */
    public User getUser() {
        return user;
    }

    /** Define o utilizador do formulário. */
    public void setUser(User user) {
        this.user = user;
    }
    
    /** Retorna o utilizador atualmente autenticado na sessão web. */
    public User getLoggedUser() {
		return loggedUser;
	}

    /** Define o utilizador autenticado na sessão web. */
	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * Executa o login na camada web através do EJB.
	 * 
	 * @return String de redirecionamento para o dashboard ou null em caso de falha
	 */
	public String login() {
		User loggedUser = iUser.loginAndReturn(user.getEmail(), user.getPasswordHash());
		
		if (loggedUser != null) {
			this.loggedUser = loggedUser; 
			this.user = new User(); 
			return "dashboard?faces-redirect=true"; 
		}

		return null;
	}
	
	/**
	 * Regista um novo cliente a partir do formulário web.
	 * 
	 * @return Redirecionamento para a página de login
	 */
	public String signin() {
		user.setType(UserType.CLIENT);
		iUser.create(user);
		this.user = new User();
		return "login?faces-redirect=true";
	}
	
	/**
	 * Termina a sessão web do utilizador.
	 * 
	 * @return Redirecionamento para a página de login
	 */
	public String logout() {
		this.loggedUser = null;
		this.user = new User();
		return "login?faces-redirect=true";
	}
	
}