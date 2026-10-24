package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Agency;

@Stateless
@Remote(IAgency.class)
public class AgencySB implements IAgency {

	@PersistenceContext(name = "autorent")
	private EntityManager em;
	
	@Override
	public boolean create(Agency agency) {
		if (agency == null) return false;
		em.persist(agency);
		return true;
	}

	@Override
	public boolean update(Agency agency) {
		if (agency == null) return false;
		em.merge(agency);
		return true;
	}

	@Override
	public boolean remove(Long id) {
		Agency agency = findById(id);
		if (agency == null) return false; 
		em.remove(agency);
		return true;
	}

//	@Override
//	public Agency findById(Long id) {
//	    return em.find(Agency.class, id);
//	}
	
	@Override
	public Agency findById(Long id) {
    try {
    	Agency agency = em.find(Agency.class, id);
        if (agency != null) 
        	agency.setVehiclesList(null);
        return agency;
    } catch (Exception e) {
        return null;
    }
}
	
//	@Override
//	public List<Agency> listAll() {
//		TypedQuery<Agency> query = em.createQuery("SELECT a FROM Agency a", Agency.class);
//		return query.getResultList();
//	}
	
	@Override
	public List<Agency> listAll() {
	    TypedQuery<Agency> query = em.createQuery("SELECT a FROM Agency a", Agency.class);
	    List<Agency> agencies = query.getResultList();
	    
	    // Evita o erro de PersistentBag limpando a lista de veículos associada a cada agência.
	    if (agencies != null) {
	        for (Agency agency : agencies) 
	            agency.setVehiclesList(null);
	    }
	    
	    return agencies;
	}
	
	/**
	 * @param category
	 * @return Lista com as agências que possuem veículos disponíveis de determinada categoria.
	 */
	public List<Agency> getAgenciesWithAvailableVehiclesByCategory(String category) {
		TypedQuery<Agency> query = em.createQuery("SELECT DISTINCT v.agency FROM Vehicle v WHERE v.category = :category AND v.status = :status", Agency.class);
		query.setParameter("category", category);
		query.setParameter("status", VehicleStatus.AVAILABLE);
	    return query.getResultList();
	}
}
