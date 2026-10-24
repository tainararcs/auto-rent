package trcs.autorent.model;

import java.io.Serializable;
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
import trcs.autorent.enums.VehicleCategory;
import trcs.autorent.enums.VehicleStatus;

/**
 * Representa os veículos disponíveis na frota.
 */
@Entity
public class Vehicle implements Serializable {
	/**	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Placa. */
	@Column(nullable = false, unique = true, length = 10)
	private String plate;
	
	/** Marca. */
	@Column(nullable = false, length = 50)
	private String brand;
	
	/** Modelo. */
	@Column(nullable = false, length = 50)
	private String model;
	
	@Column(nullable = false)
	private Long year;
	
	/** Categoria. */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private VehicleCategory category; 
	
	/** Taxa diária. */
	@Column(nullable = false, precision = 10)
	private Double dailyRate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private VehicleStatus status; 
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
	private Agency agency;
	
	@ManyToMany
    @JoinTable(name = "vehicle_accessory", joinColumns = @JoinColumn(name = "vehicle_id"), inverseJoinColumns = @JoinColumn(name = "accessory_id"))
	private List<Accessory> accessoriesList;

	public Vehicle() {}

	public Vehicle(String plate, String brand, String model, Long year, VehicleCategory category, 
			Double dailyRate, Agency agency, List<Accessory> accessoriesList) {
		this.plate = plate;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.category = category;
		this.dailyRate = dailyRate;
		this.agency = agency;
		this.accessoriesList = accessoriesList;
		status = VehicleStatus.AVAILABLE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public VehicleCategory getCategory() {
		return category;
	}

	public void setCategory(VehicleCategory category) {
		this.category = category;
	}

	public Double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(Double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

	@Override
	public String toString() {
		return String.format("%d. Placa: %s | %s %s (%d) | Cat: %s | Diária: R$ %1.2f | Status: %s | Agência: %s",
				id, plate, brand, model, year, category, dailyRate, status, (agency != null ? agency.getName() : "N/A")
		);
	}
	
}
