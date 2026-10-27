package trcs.autorent.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import trcs.autorent.enums.RentalStatus;

/**
 * Gerencia o processo de aluguel completo, associando o cliente, o veículo, além das agências distintas de retirada e devolução.   
 */
@Entity
public class Rental implements Serializable {
	
	/** Constante de serialização para controle de versão da classe. */
	private static final long serialVersionUID = 1L;

	/** Identificador único da locação gerado automaticamente. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Cliente associado à locação. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
	private User client;
	
	/** Veículo alugado. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
	private Vehicle vehicle;
	
	/** Agência onde o veículo foi retirado. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_agency_id", nullable = false)
	private Agency pickupAgency;
	
	/** Agência onde o veículo deverá ser devolvido. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_agency_id", nullable = false)
	private Agency returnAgency;
	
	/** Data de retirada do veículo. */
	@Column(nullable = false)
	private LocalDate pickupDate;
	
	/** Data prevista para a devolução do veículo. */
	@Column(nullable = false)
	private LocalDate dueDate;
	
	/** Data em que ocorreu a devolução real do veículo. */
	private LocalDate returnDate;
	
	/** Estado atual da locação (Enum). */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private RentalStatus status;
	
	/** Valor financeiro total calculado para a locação. */
	@Column(nullable = false, precision = 10)
	private Double totalValue;

	@ManyToMany
	@JoinTable(name = "rental_accessory", joinColumns = @JoinColumn(name = "rental_id"), inverseJoinColumns = @JoinColumn(name = "accessory_id"))
	private List<Accessory> accessoriesList;
	
	/**
     * Construtor padrão vazio da classe Rental.
     */
	public Rental() {}

	/**
     * Construtor que inicializa a locação definindo o cliente, veículo e as agências envolvidas, 
     * calculando automaticamente as datas iniciais padrão e o valor total estimado.
     * 
     * @param client Cliente que está efetuando a locação
     * @param vehicle Veículo a ser alugado
     * @param pickupAgency Agência de retirada
     * @param returnAgency Agência de devolução
     */
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

	/**
     * Retorna o ID da locação.
     * 
     * @return O ID da locação
     */
	public Long getId() {
		return id;
	}

	/**
     * Define o ID da locação.
     * 
     * @param id O ID a ser configurado
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Retorna o cliente da locação.
     * 
     * @return O objeto User representando o cliente
     */
	public User getClient() {
		return client;
	}

	/**
     * Define o cliente da locação.
     * 
     * @param client O cliente a ser configurado
     */
	public void setClient(User client) {
		this.client = client;
	}

	/**
     * Retorna o veículo alugado.
     * 
     * @return O veículo
     */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
     * Define o veículo alugado.
     * 
     * @param vehicle O veículo a ser configurado
     */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
     * Retorna a agência de retirada.
     * 
     * @return A agência de retirada
     */
	public Agency getPickupAgency() {
		return pickupAgency;
	}

	/**
     * Define a agência de retirada.
     * 
     * @param pickupAgency A agência de retirada a ser configurada
     */
	public void setPickupAgency(Agency pickupAgency) {
		this.pickupAgency = pickupAgency;
	}

	/**
     * Retorna a agência de devolução.
     * 
     * @return A agência de devolução
     */
	public Agency getReturnAgency() {
		return returnAgency;
	}

	/**
     * Define a agência de devolução.
     * 
     * @param returnAgency A agência de devolução a ser configurada
     */
	public void setReturnAgency(Agency returnAgency) {
		this.returnAgency = returnAgency;
	}

	/**
     * Retorna a data de retirada.
     * 
     * @return Data de retirada
     */
	public LocalDate getPickupDate() {
		return pickupDate;
	}

	/**
     * Define a data de retirada.
     * 
     * @param pickupDate Data de retirada a ser configurada
     */
	public void setPickupDate(LocalDate pickupDate) {
		this.pickupDate = pickupDate;
	}

	/**
     * Retorna a data prevista para devolução.
     * 
     * @return Data prevista (due date)
     */
	public LocalDate getDueDate() {
		return dueDate;
	}

	/**
     * Define a data prevista para devolução.
     * 
     * @param dueDate Data prevista a ser configurada
     */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	/**
     * Retorna a data real de devolução.
     * 
     * @return Data de devolução real
     */
	public LocalDate getReturnDate() {
		return returnDate;
	}

	/**
     * Define a data real de devolução.
     * 
     * @param returnDate Data de devolução a ser configurada
     */
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	/**
     * Retorna o status atual da locação.
     * 
     * @return O status (RentalStatus)
     */
	public RentalStatus getStatus() {
		return status;
	}

	/**
     * Define o status atual da locação.
     * 
     * @param status O status a ser configurado
     */
	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	/**
     * Retorna o valor total financeiro da locação.
     * 
     * @return Valor total
     */
	public Double getTotalValue() {
		return totalValue;
	}

	/**
     * Define o valor total financeiro da locação.
     * 
     * @param totalValue O valor total a ser configurado
     */
	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}
	
	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

	/**
     * Retorna uma representação formatada em String dos dados da locação.
     * 
     * @return String detalhada da locação
     */
	@Override
	public String toString() {
		String returnDateStr = (returnDate != null) ? returnDate.toString() : "Pendente";
		String vehicleInfo = (vehicle != null) ? String.format("%s (%s)", vehicle.getModel(), vehicle.getPlate()) : "N/A";
		String clientName = (client != null) ? client.getName() : "N/A";
		String agencyName = (pickupAgency != null) ? pickupAgency.getName() : "N/A";

		return String.format("%d. Cliente: %s | Veículo: %s | Agência: %s | Período: %s a %s (Devolução: %s) | Status: %s | Total: R$ %1.2f",
				id, clientName, vehicleInfo, agencyName, pickupDate, dueDate, returnDateStr, status.getStatus(), totalValue
		);
	}
	
}