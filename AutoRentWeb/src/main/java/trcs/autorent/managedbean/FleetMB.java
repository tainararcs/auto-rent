package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.Map;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.sessionbean.IFleet;

@RequestScoped
@Named("fleetMB")
public class FleetMB implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private IFleet iFleet;

	public long getTotalVehicles() {
		return iFleet.getTotalVehicles();
	}

	public long getTotalAvailable() {
		return iFleet.getTotalAvailable();
	}

	public long getTotalRented() {
		return iFleet.getTotalRented();
	}

	public long getTotalReserved() {
		return iFleet.getTotalReserved();
	}

	public long getTotalMaintenance() {
		return iFleet.getTotalMaintenance();
	}

	public Map<Long, Long> getAvailableByAgency() {
		return iFleet.getAvailableByAgency();
	}
	
	// Força a atualização manual do cache se necessário
	public void refresh() {
		iFleet.refresh();
	}
}
