package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.Agency;

/**
 * Interface remota para gerir as operações de negócio relativas às agências (filiais) da locadora.
 */
@Remote
public interface IAgency extends Crud<Agency> {
}