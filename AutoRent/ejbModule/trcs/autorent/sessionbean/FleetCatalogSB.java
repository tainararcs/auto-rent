package trcs.autorent.sessionbean;

import java.util.HashMap;
import java.util.Map;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import trcs.autorent.enums.VehicleStatus;

/**
 * Status/Catálogo Global da Frota.
 * Mantém o estado compartilhado global da frota entre todas as agências.
 */
@Singleton
@Startup
public class FleetCatalogSB {

    @PersistenceContext(unitName = "autorent") 
    private EntityManager em;

    private long totalVehicles;
    private long totalAvailable;
    private long totalRented;
    private long totalReserved;
    private long totalMaintenance;
    private Map<Long, Long> availableByAgency;

    public void refreshCache() {
        totalVehicles = em.createQuery("SELECT COUNT(v) FROM Vehicle v", Long.class).getSingleResult();
        totalAvailable = em.createQuery("SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", Long.class).setParameter("status", VehicleStatus.AVAILABLE).getSingleResult();
        totalRented = em.createQuery("SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", Long.class).setParameter("status", VehicleStatus.RENTED).getSingleResult();
        totalReserved = em.createQuery("SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", Long.class).setParameter("status", VehicleStatus.RESERVED).getSingleResult();
        totalMaintenance = em.createQuery("SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", Long.class).setParameter("status", VehicleStatus.MAINTENANCE).getSingleResult();

        // Carrega disponíveis por agência
        availableByAgency = new HashMap<>();
        var results = em.createQuery("SELECT v.agency.id, COUNT(v) FROM Vehicle v WHERE v.status = :status GROUP BY v.agency.id", Object[].class).setParameter("status", VehicleStatus.AVAILABLE).getResultList();

        for (Object[] row : results) 
            availableByAgency.put((Long) row[0], (Long) row[1]);
    }

    public long getTotalVehicles() { 
    	return totalVehicles; 
    }
    
    public long getTotalDisponiveis() { 
    	return totalAvailable; 
    }
    
    public long getTotalAlugados() { 
    	return totalRented; 
    }
    
    public long getTotalReservados() { 
    	return totalReserved; 
    }
    
    public long getTotalManutencao() { 
    	return totalMaintenance; 
    }
    
    public Map<Long, Long> getDisponiveisPorAgencia() {
        if (availableByAgency == null) refreshCache();
        return availableByAgency;
    }
}