package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import trcs.autorent.enums.VehicleCategory;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Vehicle;

@Stateless
@Remote(IVehicle.class)
public class VehicleSB implements IVehicle {

	@PersistenceContext(unitName = "autorent")
	private EntityManager em;
	
	@Override
	public boolean create(Vehicle vehicle) {
		if (vehicle == null) return false; 
		em.persist(vehicle);
		return true;
	}

	@Override
	public boolean update(Vehicle vehicle) {
		if (vehicle == null) return false; 
		em.merge(vehicle);
		return true;
	}
	
	@Override
	public boolean remove(Long id) {
		Vehicle vehicle = findById(id);
		if (vehicle == null) return false; 
		em.remove(vehicle);
		return true;
	}

	@Override
	public Vehicle findById(Long id) {
	    Vehicle vehicle = em.find(Vehicle.class, id);
        if (vehicle != null) {
            vehicle.setAccessoriesList(null);
            if (vehicle.getAgency() != null) 
                vehicle.getAgency().setVehiclesList(null);
        }
        return vehicle;
	}
	
	@Override
	public List<Vehicle> listAll() {
		TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v JOIN FETCH v.agency", Vehicle.class);
		List<Vehicle> vehicles = query.getResultList();
		if (vehicles != null) {
			for (Vehicle v : vehicles) {
				v.setAccessoriesList(null);
			}
		}
		return vehicles;
	}

	@Override
	public List<Vehicle> getByStatus(VehicleStatus status) {
		TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v JOIN FETCH v.agency WHERE v.status = :status", Vehicle.class);
		query.setParameter("status", status);
		List<Vehicle> vehicles = query.getResultList();
		if (vehicles != null) {
			for (Vehicle v : vehicles) {
				v.setAccessoriesList(null);
			}
		}
		return vehicles;
	}

	@Override
	public List<Vehicle> getByAgency(Agency agency) {
	    TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v WHERE v.agency = :agency", Vehicle.class);
	    query.setParameter("agency", agency);
	    
	    List<Vehicle> vehicles = query.getResultList();
	    
	    // Limpa as coleções para evitar o erro de PersistentBag no cliente Java SE remoto.
	    if (vehicles != null) {
	        for (Vehicle v : vehicles) {
	            v.setAccessoriesList(null); 			 // Limpa acessórios do veículo.
	            if (v.getAgency() != null) 
	                v.getAgency().setVehiclesList(null); // Limpa a lista de veículos da agência.
	        }
	    }
	    
	    return vehicles;
	}

	@Override
	public List<Vehicle> getByCategory(VehicleCategory category) { // Alterado para receber o Enum caso necessário, ou mantenha String
		TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v JOIN FETCH v.agency WHERE v.category = :category", Vehicle.class);
		query.setParameter("category", category);
		List<Vehicle> vehicles = query.getResultList();
		if (vehicles != null) {
			for (Vehicle v : vehicles) {
				v.setAccessoriesList(null);
			}
		}
		return vehicles;
	}
	
	/**
	 * @param agencyId
	 * @return Lista com os veículos disponíveis em determinada agência.
	 */
	public List<Vehicle> getAvailableVehiclesByAgency(Long agencyId) {
		TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v JOIN FETCH v.agency a WHERE a.id = :agencyId AND v.status = :status", Vehicle.class);
	    query.setParameter("agencyId", agencyId);
	    query.setParameter("status", VehicleStatus.AVAILABLE);
	    return query.getResultList();
	}

	/**
	 * @return Lista com os veículos que estão alugados.
	 */
	public List<Vehicle> getRentedVehicles() {
		TypedQuery<Vehicle> query = em.createQuery("SELECT v FROM Vehicle v JOIN FETCH v.agency WHERE v.status = :status", Vehicle.class);
	    query.setParameter("status", VehicleStatus.RENTED);
	    return query.getResultList();
	}
	
	@Override
	public boolean updateStatus(Long vehicleId, VehicleStatus status) {
	    Vehicle vehicle = em.find(Vehicle.class, vehicleId);
	    if (vehicle == null) return false;
	    
	    vehicle.setStatus(status);
	    em.merge(vehicle);
	    return true;
	}
}
