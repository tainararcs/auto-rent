package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import trcs.autorent.model.Accessory;

@Stateless
@Remote(IAccessory.class)
public class AccessorySB implements IAccessory {
	
	@PersistenceContext(name = "autorent")
	private EntityManager em;
	
	@Override
	public boolean create(Accessory accessory) {
		if (accessory == null) return false;
		em.persist(accessory);
		return true;
	}

	@Override
	public boolean update(Accessory accessory) {
		if (accessory == null) return false;
		em.merge(accessory);
		return true;
	}
	
	@Override
	public boolean remove(Long id) {
		Accessory accessory = findById(id);
		if (accessory == null) return false;
		em.remove(accessory);
		return true;
	}

	@Override
	public Accessory findById(Long id) {
		return em.find(Accessory.class, id);
	}

//	@Override
//	public List<Accessory> listAll() {
//		TypedQuery<Accessory> query = em.createQuery("SELECT a FROM Accessory a", Accessory.class);
//		return query.getResultList();
//	}
	
	@Override
	public List<Accessory> listAll() {
		TypedQuery<Accessory> query = em.createQuery("SELECT a FROM Accessory a", Accessory.class);
	    List<Accessory> accessories = query.getResultList();
	    
	    // Evita o erro de PersistentBag limpando a lista de veículos associada a cada agência
	    if (accessories != null) {
	        for (Accessory accessory : accessories) {
	        	accessory.setVehiclesList(null);
	        }
	    }
	    
	    return accessories;
	}

}
