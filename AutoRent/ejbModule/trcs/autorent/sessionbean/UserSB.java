package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;
import trcs.autorent.util.Utils;

/**
 * Gerenciamento de Sessão do Usuário.
 * Implementa Stateful EJB para manter o estado do utilizador autenticado.
 */
@Stateful
@Remote({IUser.class, IAuth.class})
public class UserSB implements IUser {

	@PersistenceContext(unitName = "autorent")
	private EntityManager em;
	private User loggedUser;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean create(User user) {
	    if (user == null) return false; 
	    String passwordHash = Utils.getHashPassword(user.getPasswordHash());
	    user.setPasswordHash(passwordHash);
	    em.persist(user);
	    return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(User user) {
		if (user == null) return false; 
		em.merge(user);
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Long id) {
		User user = findById(id);
		if (user == null) return false;
		em.remove(user);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findById(Long id) {
		// Usa JPQL com query explícita em vez de em.find para evitar proxies do Hibernate.
	    User user = em.find(User.class, id);
	    if (user == null) return null;
	    
        user.setRentalsList(null); // Evita o PersistentBag.
        return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> listAll() {
		List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
	    if (users == null) return null; 
        
	    for (User user : users) 
        	user.setRentalsList(null); 
	    
	    return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getByType(UserType type) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.type = :type", User.class);
	    query.setParameter("type", type);
	    
	    List<User> usersList = query.getResultList();
	    
	    // Limpeza de segurança para evitar PersistentBag no cliente remoto.
	    if (usersList != null) 
	        for (User user : usersList) 
	            user.setRentalsList(null);
	    
	    return usersList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAdmin() {
		return loggedUser.getType() == UserType.EMPLOYEE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User loginAndReturn(String email, String password) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :password", User.class);
		query.setParameter("email", email);
		query.setParameter("password", Utils.getHashPassword(password));
		
		User user = query.getSingleResult();
		user.setRentalsList(null); // Evita PersistentBag
		
		this.loggedUser = user;
		return user;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
    public boolean login(String email, String password) {
		User user = loginAndReturn(email, password);
        if (user == null) return false;
        return true;
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public void logout() {
        this.loggedUser = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getLoggedUser() {
		return loggedUser;
	}

}