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
	
	/** Constante de serialização para controle de versão da classe. */
	private static final long serialVersionUID = 1L;
	
	/** Identificador único da agência gerado automaticamente. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Nome da agência/filial. */
	@Column(nullable = false, length = 100)
	private String name;
	
	/** Endereço físico da agência. */
	@Column(nullable = false, length = 150)
	private String address;
	
	/** Cidade onde a agência está localizada. */
	@Column(nullable = false, length = 50)
	private String city;
	
	/** Número de telefone de contato da agência. */
	@Column(nullable = false, unique = true, length = 20)
	private String phoneNumber;
	
	/** Lista de veículos vinculados a esta agência. */
	@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Vehicle> vehiclesList;
	
	/**
     * Construtor padrão vazio da classe Agency.
     */
	public Agency() {}
	
	/**
     * Construtor com parâmetros para criação rápida de uma agência.
     * 
     * @param name Nome da agência
     * @param address Endereço físico
     * @param city Cidade
     * @param phoneNumber Telefone de contato
     */
	public Agency(String name, String address, String city, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.phoneNumber = phoneNumber;
	}
	
	/**
     * Retorna o ID da agência.
     * 
     * @return O ID da agência
     */
	public Long getId() {
		return id;
	}
	
	/**
     * Define o ID da agência.
     * 
     * @param id O ID a ser configurado
     */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
     * Retorna o nome da agência.
     * 
     * @return O nome
     */
	public String getName() {
		return name;
	}
	
	/**
     * Define o nome da agência.
     * 
     * @param name O nome a ser configurado
     */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
     * Retorna o endereço da agência.
     * 
     * @return O endereço
     */
	public String getAddress() {
		return address;
	}
	
	/**
     * Define o endereço da agência.
     * 
     * @param address O endereço a ser configurado
     */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
     * Retorna a cidade da agência.
     * 
     * @return A cidade
     */
	public String getCity() {
		return city;
	}
	
	/**
     * Define a cidade da agência.
     * 
     * @param city A cidade a ser configurada
     */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
     * Retorna o número de telefone da agência.
     * 
     * @return O telefone
     */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
     * Define o número de telefone da agência.
     * 
     * @param phoneNumber O telefone a ser configurado
     */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
     * Retorna a lista de veículos da agência.
     * 
     * @return Lista de veículos
     */
	public List<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	/**
     * Define a lista de veículos da agência.
     * 
     * @param vehiclesList Lista de veículos a ser configurada
     */
	public void setVehiclesList(List<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}

	/**
     * Retorna uma representação formatada em String da agência.
     * 
     * @return String contendo informações detalhadas da agência
     */
	@Override
	public String toString() {
		return String.format("%d. %s - %s (%s) | Tel: %s", id, name, address, city, phoneNumber);
	}
	
}