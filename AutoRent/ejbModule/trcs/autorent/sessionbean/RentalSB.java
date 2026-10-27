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

/**
 * Implementação do Session Bean Stateless para a gestão de locações.
 */
@Stateless
@Remote(IRental.class)
public class RentalSB implements IRental {
	
	@PersistenceContext(unitName = "autorent")
	private EntityManager em;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean create(Rental rental) {
		if (rental == null) return false; 
		
		Vehicle vehicle = rental.getVehicle();
	    if (vehicle != null) {
	        vehicle.setStatus(VehicleStatus.RESERVED);
	        em.merge(vehicle);
	    }
	    
		em.persist(rental);
		em.flush(); // Força o envio imediato para o banco de dados
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean update(Rental rental) {
		if (rental == null) return false; 
		em.merge(rental);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Long id) {
		Rental rental = findById(id);
		if (rental == null) return false; 
		em.remove(rental);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Rental findById(Long id) {
		return em.find(Rental.class, id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rental> listAll() {
		// Removido o 'v' após r.vehicle para evitar a violação de strict JPQL compliance
		TypedQuery<Rental> query = em.createQuery(
			"SELECT r FROM Rental r JOIN FETCH r.client JOIN FETCH r.vehicle JOIN FETCH r.vehicle.agency JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency", 
			Rental.class
		);

		List<Rental> rentalsList = query.getResultList();
		if (rentalsList != null) {
			for (Rental rental : rentalsList) {
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
		}
		
		return rentalsList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rental> getByStatus(RentalStatus status) {
		TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r WHERE status = :status", Rental.class);
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	/**
	 * Lista locações ativas/em andamento.
	 * 
	 * @return Lista de locações ativas ou reservadas
	 */
	@Override
	public List<Rental> getActiveRentals() {
        TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r WHERE r.status = :status1 OR r.status = :status2", Rental.class);
        query.setParameter("status1", RentalStatus.RESERVED);
        query.setParameter("status2", RentalStatus.ACTIVE);
        
        List<Rental> rentalsList = query.getResultList();
        if (rentalsList == null) return new ArrayList<>();
        
		for (Rental rental : rentalsList) {
			// Limpa a coleção N:M de acessórios da própria locação para evitar o PersistentBag remoto
			rental.setAccessoriesList(null);

			// Limpa as coleções filhas das entidades associadas
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rental> getRentalsByUser(Long userId) {
		TypedQuery<Rental> query = em.createQuery(
			"SELECT r FROM Rental r JOIN FETCH r.client JOIN FETCH r.vehicle JOIN FETCH r.pickupAgency JOIN FETCH r.returnAgency WHERE r.client.id = :userId", 
			Rental.class
		);
		query.setParameter("userId", userId);
		
		List<Rental> rentalsList = query.getResultList();
		
		// Limpeza opcional de PersistentBags para evitar problemas caso utilize em clientes remotos.
		if (rentalsList != null) {
			for (Rental rental : rentalsList) {
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
		}
		
		return rentalsList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Rental> getRentalsByPickupCity(String city) {
		TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r JOIN FETCH r.pickupAgency a JOIN FETCH r.client c JOIN FETCH r.vehicle v WHERE a.city = :city", Rental.class);
		query.setParameter("city", city);
		return query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
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
                
            vehicle.setStatus(VehicleStatus.RENTED);
            em.merge(vehicle);
        }
        
        return true; 
    }

    /**
     * {@inheritDoc}
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