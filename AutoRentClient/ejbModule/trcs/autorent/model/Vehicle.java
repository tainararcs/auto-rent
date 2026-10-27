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
	
	/** Constante de serialização para controle de versão da classe. */
	private static final long serialVersionUID = 1L;

	/** Identificador único do veículo gerado automaticamente. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Placa de identificação do veículo (única). */
	@Column(nullable = false, unique = true, length = 10)
	private String plate;
	
	/** Marca fabricante do veículo. */
	@Column(nullable = false, length = 50)
	private String brand;
	
	/** Modelo do veículo. */
	@Column(nullable = false, length = 50)
	private String model;
	
	/** Ano de fabricação do veículo. */
	@Column(nullable = false)
	private Long year;
	
	/** Categoria do veículo (Enum). */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private VehicleCategory category; 
	
	/** Valor da taxa diária de locação do veículo. */
	@Column(nullable = false, precision = 10)
	private Double dailyRate;
	
	/** Status operacional atual do veículo (Enum). */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private VehicleStatus status; 
	
	/** Agência física à qual o veículo está vinculado. */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agency_id", nullable = false)
	private Agency agency;
	
	/** Lista de acessórios opcionais associados ao veículo. */
	@ManyToMany
    @JoinTable(name = "vehicle_accessory", joinColumns = @JoinColumn(name = "vehicle_id"), inverseJoinColumns = @JoinColumn(name = "accessory_id"))
	private List<Accessory> accessoriesList;

	/**
     * Construtor padrão vazio da classe Vehicle.
     */
	public Vehicle() {}

	/**
     * Construtor com parâmetros para inicialização de um veículo e definição automática do status como disponível.
     * 
     * @param plate Placa do veículo
     * @param brand Marca
     * @param model Modelo
     * @param year Ano de fabricação
     * @param category Categoria (VehicleCategory)
     * @param dailyRate Taxa diária
     * @param agency Agência de alocação
     * @param accessoriesList Lista de acessórios opcionais
     */
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

	/**
     * Retorna o ID do veículo.
     * 
     * @return O ID
     */
	public Long getId() {
		return id;
	}

	/**
     * Define o ID do veículo.
     * 
     * @param id O ID a ser configurado
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Retorna a placa do veículo.
     * 
     * @return A placa
     */
	public String getPlate() {
		return plate;
	}

	/**
     * Define a placa do veículo.
     * 
     * @param plate A placa a ser configurada
     */
	public void setPlate(String plate) {
		this.plate = plate;
	}

	/**
     * Retorna a marca do veículo.
     * 
     * @return A marca
     */
	public String getBrand() {
		return brand;
	}

	/**
     * Define a marca do veículo.
     * 
     * @param brand A marca a ser configurada
     */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
     * Retorna o modelo do veículo.
     * 
     * @return O modelo
     */
	public String getModel() {
		return model;
	}

	/**
     * Define o modelo do veículo.
     * 
     * @param model O modelo a ser configurado
     */
	public void setModel(String model) {
		this.model = model;
	}

	/**
     * Retorna o ano de fabricação do veículo.
     * 
     * @return O ano
     */
	public Long getYear() {
		return year;
	}

	/**
     * Define o ano de fabricação do veículo.
     * 
     * @param year O ano a ser configurado
     */
	public void setYear(Long year) {
		this.year = year;
	}

	/**
     * Retorna a categoria do veículo.
     * 
     * @return A categoria (VehicleCategory)
     */
	public VehicleCategory getCategory() {
		return category;
	}

	/**
     * Define a categoria do veículo.
     * 
     * @param category A categoria a ser configurada
     */
	public void setCategory(VehicleCategory category) {
		this.category = category;
	}

	/**
     * Retorna a taxa diária do veículo.
     * 
     * @return O valor da diária
     */
	public Double getDailyRate() {
		return dailyRate;
	}

	/**
     * Define a taxa diária do veículo.
     * 
     * @param dailyRate O valor da diária a ser configurado
     */
	public void setDailyRate(Double dailyRate) {
		this.dailyRate = dailyRate;
	}

	/**
     * Retorna o status operacional atual do veículo.
     * 
     * @return O status (VehicleStatus)
     */
	public VehicleStatus getStatus() {
		return status;
	}

	/**
     * Define o status operacional do veículo.
     * 
     * @param status O status a ser configurado
     */
	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	/**
     * Retorna a agência associada ao veículo.
     * 
     * @return A agência
     */
	public Agency getAgency() {
		return agency;
	}

	/**
     * Define a agência associada ao veículo.
     * 
     * @param agency A agência a ser configurada
     */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	/**
     * Retorna a lista de acessórios do veículo.
     * 
     * @return Lista de acessórios
     */
	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	/**
     * Define a lista de acessórios do veículo.
     * 
     * @param accessoriesList Lista de acessórios a ser configurada
     */
	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

	/**
     * Retorna uma representação formatada em String dos dados do veículo.
     * 
     * @return String detalhada contendo placa, marca, modelo, ano, diária e agência
     */
	@Override
	public String toString() {
		return String.format("%d. Placa: %s | %s %s (%d) | Cat: %s | Diária: R$ %1.2f | Status: %s | Agência: %s",
				id, plate, brand, model, year, category.getCategory(), dailyRate, status.getStatus(), (agency != null ? agency.getName() : "N/A")
		);
	}
	
}