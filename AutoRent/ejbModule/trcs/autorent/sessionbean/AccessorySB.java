package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import trcs.autorent.model.Accessory;

/**
 * Implementação do Session Bean Stateless para a gestão de acessórios.
 */
@Stateless
@Remote(IAccessory.class)
public class AccessorySB implements IAccessory {
	
	@PersistenceContext(unitName = "autorent")
	private EntityManager em;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean create(Accessory accessory) {
		if (accessory == null) return false;
		em.persist(accessory);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Accessory accessory) {
		if (accessory == null) return false;
		em.merge(accessory);
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Long id) {
		Accessory accessory = findById(id);
		if (accessory == null) return false;
		em.remove(accessory);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Accessory findById(Long id) {
		return em.find(Accessory.class, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Accessory> listAll() {
		TypedQuery<Accessory> query = em.createQuery("SELECT a FROM Accessory a", Accessory.class);
	    List<Accessory> accessories = query.getResultList();
	    
	    // Evita o erro de PersistentBag limpando a lista de veículos associada a cada agência
	    if (accessories != null) 
	        for (Accessory accessory : accessories) 
	        	accessory.setVehiclesList(null);
	    
	    return accessories;
	}

}