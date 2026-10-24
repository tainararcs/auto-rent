package trcs.autorent.sessionbean;

import java.util.ArrayList;
import java.util.List;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import trcs.autorent.enums.RentalStatus;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Rental;
import trcs.autorent.model.Vehicle;

@Stateless
@Remote(IRental.class)
public class RentalSB implements IRental {
	
	@PersistenceContext(name = "autorent")
	private EntityManager em;
	
	@Override
	public boolean create(Rental rental) {
		if (rental == null) return false; 
		em.persist(rental);
		return true;
	}

	@Override
	public boolean update(Rental rental) {
		if (rental == null) return false; 
		em.merge(rental);
		return true;
	}

	@Override
	public boolean remove(Long id) {
		Rental rental = findById(id);
		if (rental == null) return false; 
		em.remove(rental);
		return true;
	}

	@Override
	public Rental findById(Long id) {
		return em.find(Rental.class, id);
	}
	
	@Override
	public List<Rental> listAll() {
		TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r", Rental.class);
		return query.getResultList();
	}
	
	/**
	 * Lista locações ativas/em andamento.
	 */
	@Override
	public List<Rental> getActiveRentals() {
        TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r WHERE r.status = :status1 OR r.status = :status2", Rental.class);
        query.setParameter("status1", RentalStatus.RESERVED);
        query.setParameter("status2", RentalStatus.ACTIVE);
        
        List<Rental> rentalsList = query.getResultList();
        if (rentalsList == null) return new ArrayList<>();
        
        for (Rental rental : rentalsList) {
            // Limpa as coleções e listas das entidades associadas que geram PersistentBag.
            if (rental.getClient() != null) 
            	rental.getClient().setRentalsList(null);
            if (rental.getVehicle() != null) {
            	rental.getVehicle().setAccessoriesList(null);
                if (rental.getVehicle().getAgency() != null) 
                	rental.getVehicle().getAgency().setVehiclesList(null);
            }
            if (rental.getPickupAgency() != null) 
            	rental.getPickupAgency().setVehiclesList(null);
            if (rental.getReturnAgency() != null) 
            	rental.getReturnAgency().setVehiclesList(null);
        }
        
        return rentalsList;
	}

	@Override
	public List<Rental> getByStatus(RentalStatus status) {
		TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r WHERE status = :status", Rental.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	/**
	 * @param clientId
	 * @return Listar com as locações ativas de determinado cliente.
	 */
	@Override
	public List<Rental> getActiveRentalsByClient(Long clientId) {
		TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r JOIN FETCH r.client c JOIN FETCH r.vehicle v WHERE c.id = :clientId AND r.status = :status", Rental.class);
		query.setParameter("clientId", clientId);
		query.setParameter("status", RentalStatus.ACTIVE);
		return query.getResultList();
	}
	
	/**
	 * consulta envolvendo relacionamento entre duas ou more entidades (Ex: Detalhes completos de locações por cidade da agência de retirada).
	 * @param city
	 * @return Lista
	 */
	@Override
	public List<Rental> getRentalsByPickupCity(String city) {
		TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r JOIN FETCH r.pickupAgency a JOIN FETCH r.client c JOIN FETCH r.vehicle v WHERE a.city = :city", Rental.class);
		query.setParameter("city", city);
		return query.getResultList();
	}
	
	/**
	 * Registro de retirada do veículo.
	 */
    @Override
    public boolean registerPickup(Long rentalId) {
        Rental rental = findById(rentalId);
        if (rental != null && rental.getStatus() == RentalStatus.RESERVED) {
            rental.setStatus(RentalStatus.ACTIVE);
            em.merge(rental);

            // Confirma a saída física alterando o status do veículo para Alugado.
            Vehicle vehicle = rental.getVehicle();
            if (vehicle == null) return false;
                
            vehicle.setStatus(VehicleStatus.RESERVED);
            em.merge(vehicle);
        }
        
        return true; 
    }

    /**
     * Registro de devolução.
     */
    @Override
    public boolean registerReturn(Long rentalId) {
        Rental rental = findById(rentalId);
        if (rental != null && rental.getStatus() == RentalStatus.ACTIVE) {
            rental.setStatus(RentalStatus.COMPLETED);
            em.merge(rental);

            // Libera o veículo de volta para o status de Disponível.
            Vehicle vehicle = rental.getVehicle();
            if (vehicle == null) return false;
            
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            em.merge(vehicle);
        }
        
        return true; 
    }

}
