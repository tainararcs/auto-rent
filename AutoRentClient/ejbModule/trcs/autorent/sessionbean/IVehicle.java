package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import trcs.autorent.enums.VehicleCategory;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Vehicle;

/**
 * Interface remota para a gestão da frota de veículos.
 */
@Remote
public interface IVehicle extends Crud<Vehicle> {
	
	/**
	 * Retorna todos os veículos alocados numa determinada agência.
	 * 
	 * @param agency A agência ({@link Agency}) de referência
	 * @return Lista de veículos pertencentes à agência
	 */
    List<Vehicle> getByAgency(Agency agency);
    
    /**
     * Retorna os veículos filtrados por categoria.
     * 
     * @param category A categoria do veículo ({@link VehicleCategory})
     * @return Lista de veículos da categoria especificada
     */
    List<Vehicle> getByCategory(VehicleCategory category);
    
    /**
     * Retorna os veículos filtrados pelo seu estado operacional atual.
     * 
     * @param status O status do veículo ({@link VehicleStatus})
     * @return Lista de veículos com o status correspondente
     */
    List<Vehicle> getByStatus(VehicleStatus status);
    
    /**
     * Atualiza o estado operacional de um veículo específico.
     * 
     * @param vehicleId O ID do veículo
     * @param status O novo status ({@link VehicleStatus}) a ser aplicado
     * @return true se a atualização for bem-sucedida, false caso contrário
     */
    boolean updateStatus(Long vehicleId, VehicleStatus status);
}