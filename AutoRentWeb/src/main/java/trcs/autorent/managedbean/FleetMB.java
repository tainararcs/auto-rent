package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.sessionbean.IFleet;

/**
 * Managed Bean com escopo de requisição para gerir e exibir as estatísticas globais da frota no painel (Dashboard)[cite: 51].
 */
@RequestScoped
@Named("fleetMB")
public class FleetMB implements Serializable {
	
	/** Constante de serialização[cite: 51]. */
	private static final long serialVersionUID = 1L;

	@EJB
	private IFleet iFleet;

	/**
	 * Retorna o número total de veículos na frota[cite: 51].
	 * 
	 * @return Total de veículos[cite: 51]
	 */
	public long getTotalVehicles() {
		return iFleet.getTotalVehicles();
	}

	/**
	 * Retorna o total de veículos disponíveis[cite: 51].
	 * 
	 * @return Total disponíveis[cite: 51]
	 */
	public long getTotalAvailable() {
		return iFleet.getTotalAvailable();
	}

	/**
	 * Retorna o total de veículos alugados[cite: 51].
	 * 
	 * @return Total alugados[cite: 51]
	 */
	public long getTotalRented() {
		return iFleet.getTotalRented();
	}

	/**
	 * Retorna o total de veículos reservados[cite: 51].
	 * 
	 * @return Total reservados[cite: 51]
	 */
	public long getTotalReserved() {
		return iFleet.getTotalReserved();
	}

	/**
	 * Retorna o total de veículos em manutenção[cite: 51].
	 * 
	 * @return Total em manutenção[cite: 51]
	 */
	public long getTotalMaintenance() {
		return iFleet.getTotalMaintenance();
	}

	/**
	 * Retorna o mapa de veículos disponíveis agrupados por agência[cite: 51].
	 * 
	 * @return Mapa com ID da agência e respetiva quantidade disponível[cite: 51]
	 */
	public Map<Long, Long> getAvailableByAgency() {
		return iFleet.getAvailableByAgency();
	}
	
	/**
	 * Força a atualização manual das métricas da frota[cite: 51].
	 */
	public void refresh() {
		iFleet.refresh();
	}
}