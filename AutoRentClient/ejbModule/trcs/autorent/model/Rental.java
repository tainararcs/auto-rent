package trcs.autorent.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import trcs.autorent.enums.RentalStatus;

/**
 * Gerencia o processo de aluguel completo, associando o cliente, o veículo, além das agências distintas de retirada e devolução.   
 */
@Entity
public class Rental implements Serializable {
	/**	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
	private User client;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;
	
	/** Agência de retirada. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_agency_id", nullable = false)
	private Agency pickupAgency;
	
	/** Agência de devolução. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_agency_id", nullable = false)
	private Agency returnAgency;
	
	/** Data de retirada. */
	@Column(nullable = false)
	private LocalDate pickupDate;
	
	/** Data para devolução. */
	@Column(nullable = false)
	private LocalDate dueDate;
	
	/** Data de devolução. */
	private LocalDate returnDate;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private RentalStatus status;
	
	/** Valor total. */
	@Column(nullable = false, precision = 10)
	private Double totalValue;

	public Rental() {}

	public Rental(User client, Vehicle vehicle, Agency pickupAgency, Agency returnAgency) {
		this.client = client;
		this.vehicle = vehicle;
		this.pickupAgency = pickupAgency;
		this.returnAgency = returnAgency;
		
		// Definição das datas do período de locação.
		pickupDate = LocalDate.now();
		dueDate = LocalDate.now().plusDays(3); 	// 3 dias de aluguel previsto.
		returnDate = null; 						// Data de devolução real é nula.
		status = RentalStatus.RESERVED;
      
		// Cálculo do valor total com base na diária do veículo.
	    long days = ChronoUnit.DAYS.between(pickupDate, dueDate);
	    totalValue = ((days <= 0) ? days = 1 : days) * vehicle.getDailyRate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Agency getPickupAgency() {
		return pickupAgency;
	}

	public void setPickupAgency(Agency pickupAgency) {
		this.pickupAgency = pickupAgency;
	}

	public Agency getReturnAgency() {
		return returnAgency;
	}

	public void setReturnAgency(Agency returnAgency) {
		this.returnAgency = returnAgency;
	}

	public LocalDate getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	@Override
	public String toString() {
		String returnDateStr = (returnDate != null) ? returnDate.toString() : "Pendente";
		String vehicleInfo = (vehicle != null) ? String.format("%s (%s)", vehicle.getModel(), vehicle.getPlate()) : "N/A";
		String clientName = (client != null) ? client.getName() : "N/A";
		String agencyName = (pickupAgency != null) ? pickupAgency.getName() : "N/A";

		return String.format("%d. Cliente: %s | Veículo: %s | Agência: %s | Período: %s a %s (Devolução: %s) | Status: %s | Total: R$ %1.2f",
				id, clientName, vehicleInfo, agencyName, pickupDate, dueDate, returnDateStr, status, totalValue
		);
	}
	
}
