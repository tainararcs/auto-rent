package trcs.autorent.sessionbean;

import jakarta.ejb.Remote;
import trcs.autorent.model.Accessory;

/**
 * Interface remota para gerir as operações de negócio referentes aos acessórios dos veículos.
 */
@Remote
public interface IAccessory extends Crud<Accessory> {
}