package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.User;

@Remote
public interface IAuth {
	User loginAndReturn(String email, String password);
	boolean login(String email, String password);
	void logout();
	User getLoggedUser();
}
