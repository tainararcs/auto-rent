package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.User;

@Remote
public interface IAuth {
	boolean login(String email, String password);
	void logout();
	User getLoggedUser();
}
