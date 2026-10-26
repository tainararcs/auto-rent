package trcs.autorent.sessionbean;

import java.util.Map;

import jakarta.ejb.Remote;

/**
 * Status/Catálogo Global da Frota.
 * Mantém o estado compartilhado global da frota entre todas as agências.
 */
@Remote
public interface IFleet {
	void refresh();
	long getTotalVehicles();
	long getTotalAvailable() ;
	long getTotalRented();
	long getTotalReserved();
	long getTotalMaintenance();
	Map<Long, Long> getAvailableByAgency();
}