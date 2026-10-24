package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import trcs.autorent.enums.UserType;
import trcs.autorent.model.User;

@Remote
public interface IUser extends Crud<User>, IAuth {
    List<User> getByType(UserType type);
}
