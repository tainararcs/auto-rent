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
 */
@Stateful
@Remote({IUser.class, IAuth.class})
public class UserSB implements IUser {

	@PersistenceContext(name = "autorent")
	private EntityManager em;
	private User loggedUser;
	
	@Override
	public boolean create(User user) {
	    if (user == null) return false; 
	    em.persist(user);
	    return true;
	}

	@Override
	public boolean update(User user) {
		if (user == null) return false; 
		em.merge(user);
		return true;
	}
	
	@Override
	public boolean remove(Long id) {
		User user = findById(id);
		if (user == null) return false;
		em.remove(user);
		return true;
	}

//	@Override
//	public User findById(Long id) {
//		return em.find(User.class, id);
//	}
	
	@Override
	public User findById(Long id) {
	    try {
	        // Usa JPQL com query explícita em vez de em.find para evitar proxies do Hibernate
	        User user = em.find(User.class, id);
	        if (user != null) 
	            user.setRentalsList(null); // Evita o PersistentBag
	        return user;
	    } catch (Exception e) {
	        return null; // Retorna null se não encontrar
	    }
	}

	@Override
	public List<User> listAll() {
		List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
	    if (users != null) 
	        for (User user : users) 
	        	user.setRentalsList(null); 
	    return users;
	}

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
	
	@Override
    public boolean login(String email, String password) {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.passwordHash = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", Utils.getHashPassword(password));
            
            User user = query.getSingleResult();
            if (user == null) return false;
            
            user.setRentalsList(null); // Evita PersistentBag
            this.loggedUser = user;
            return true;
        } catch (Exception e) {
            // Retorna falso caso o usuário não seja encontrado ou ocorra erro de credencial
            return false;
        }
    }

    @Override
    public void logout() {
        this.loggedUser = null;
    }

    @Override
    public User getLoggedUser() {
		return loggedUser;
	}
}
