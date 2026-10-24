package trcs.autorent.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Representa as filiais físicas da locadora onde os veículos são alocados e as retiradas/devoluções ocorrem.   
 */
@Entity
public class Agency implements Serializable {
	/**	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 150)
	private String address;
	
	@Column(nullable = false, length = 50)
	private String city;
	
	@Column(nullable = false, unique = true, length = 20)
	private String phoneNumber;
	
	@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Vehicle> vehiclesList;
	
	public Agency() {}
	
	public Agency(String name, String address, String city, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.phoneNumber = phoneNumber;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public List<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	public void setVehiclesList(List<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}

	@Override
	public String toString() {
	      return String.format("%d. %s - %s (%s) | Tel: %s", id, name, address, city, phoneNumber);
	  }
	
}
