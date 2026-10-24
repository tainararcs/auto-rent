package trcs.autorent.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

/**
 * Representa os itens opcionais de locação, compondo o relacionamento N:M com os veículos.   
 */
@Entity
public class Accessory implements Serializable {
	/**	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, precision = 10)
    private Double dailyRate;

    @ManyToMany(mappedBy = "accessoriesList")
    private List<Vehicle> vehiclesList;

    public Accessory() {}
    
	public Accessory(String description, Double dailyRate, List<Vehicle> vehiclesList) {
		this.description = description;
		this.dailyRate = dailyRate;
		this.vehiclesList = vehiclesList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(Double dailyRate) {
		this.dailyRate = dailyRate;
	}

	public List<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	public void setVehiclesList(List<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}

	@Override
	public String toString() {
		return String.format("%d. %s - Diária: R$ %1.2f", id, description, dailyRate);
	}
    
}
