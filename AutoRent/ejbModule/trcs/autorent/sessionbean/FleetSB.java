package trcs.autorent.sessionbean;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Remote;
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
@Remote(IFleet.class)
public class FleetSB implements IFleet {

    @PersistenceContext(unitName = "autorent") 
    private EntityManager em;

    private long totalVehicles;
    private long totalAvailable;
    private long totalRented;
    private long totalReserved;
    private long totalMaintenance;
    private Map<Long, Long> availableByAgency;

    /**
     * Inicializa o bean carregando as métricas iniciais da frota.
     */
    @PostConstruct
    public void init() {
        refresh();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void refresh() {
        totalVehicles = em.createQuery(
        		"SELECT COUNT(v) FROM Vehicle v", 
        		Long.class
        ).getSingleResult();
        totalAvailable = em.createQuery(
        		"SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", 
        		Long.class
		).setParameter("status", VehicleStatus.AVAILABLE).getSingleResult();
        totalRented = em.createQuery(
        		"SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", 
        		Long.class
		).setParameter("status", VehicleStatus.RENTED).getSingleResult();
        totalReserved = em.createQuery(
        		"SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", 
        		Long.class
        ).setParameter("status", VehicleStatus.RESERVED).getSingleResult();
        totalMaintenance = em.createQuery(
        		"SELECT COUNT(v) FROM Vehicle v WHERE v.status = :status", 
        		Long.class
		).setParameter("status", VehicleStatus.MAINTENANCE).getSingleResult();

        // Carrega disponíveis por agência
        availableByAgency = new HashMap<>();
        var results = em.createQuery(
        		"SELECT v.agency.id, COUNT(v) FROM Vehicle v WHERE v.status = :status GROUP BY v.agency.id", 
        		Object[].class
        ).setParameter("status", VehicleStatus.AVAILABLE).getResultList();

        for (Object[] row : results) 
            availableByAgency.put((Long) row[0], (Long) row[1]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalVehicles() { 
    	return totalVehicles; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalAvailable() { 
    	return totalAvailable; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalRented() { 
    	return totalRented; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalReserved() { 
    	return totalReserved; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public long getTotalMaintenance() { 
    	return totalMaintenance; 
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Long> getAvailableByAgency() {
        if (availableByAgency == null) refresh();
        return availableByAgency;
    }

}