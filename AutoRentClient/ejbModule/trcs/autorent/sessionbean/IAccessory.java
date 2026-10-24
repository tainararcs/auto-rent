package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.Accessory;

@Remote
public interface IAccessory extends Crud<Accessory>{
}
