package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.Agency;

@Remote
public interface IAgency extends Crud<Agency> {
}
