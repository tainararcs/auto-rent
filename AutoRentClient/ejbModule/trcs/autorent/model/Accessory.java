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
	
	/** Constante de serialização para controle de versão da classe. */
	private static final long serialVersionUID = 1L;
	
	/** Identificador único do acessório gerado automaticamente. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Descrição detalhada do acessório. */
	@Column(nullable = false, length = 100)
    private String description;

    /** Taxa diária de cobrança do acessório. */
    @Column(nullable = false, precision = 10)
    private Double dailyRate;

    /** Lista de veículos associados a este acessório (Relação N:M). */
    @ManyToMany(mappedBy = "accessoriesList")
    private List<Vehicle> vehiclesList;

    /**
     * Construtor padrão vazio da classe Accessory.
     */
    public Accessory() {}
    
    /**
     * Construtor instanciando um acessório com descrição, taxa diária e lista de veículos.
     * 
     * @param description Descrição do acessório
     * @param dailyRate Taxa diária do acessório
     * @param vehiclesList Lista de veículos associados
     */
	public Accessory(String description, Double dailyRate, List<Vehicle> vehiclesList) {
		this.description = description;
		this.dailyRate = dailyRate;
		this.vehiclesList = vehiclesList;
	}

	/**
	 * Retorna o identificador único do acessório.
	 * 
     * @return O ID do acessório
     */
	public Long getId() {
		return id;
	}

	/**
	 * Define o identificador único do acessório.
	 * 
     * @param id O ID a ser configurado
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retorna a descrição do acessório.
	 * 
     * @return A descrição
     */
	public String getDescription() {
		return description;
	}

	/**
	 * Define a descrição do acessório.
	 * 
     * @param description A descrição a ser configurada
     */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retorna a taxa diária do acessório.
     * 
     * @return O valor da diária
     */
	public Double getDailyRate() {
		return dailyRate;
	}

	/**
	 * Define a taxa diária do acessório.
     * 
     * @param dailyRate O valor da diária a ser configurado
     */
	public void setDailyRate(Double dailyRate) {
		this.dailyRate = dailyRate;
	}

	/**
	 * Retorna a lista de veículos vinculados ao acessório.
     * 
     * @return Lista de veículos
     */
	public List<Vehicle> getVehiclesList() {
		return vehiclesList;
	}

	/**
	 * Define a lista de veículos vinculados ao acessório.
     * 
     * @param vehiclesList Lista de veículos a ser configurada
     */
	public void setVehiclesList(List<Vehicle> vehiclesList) {
		this.vehiclesList = vehiclesList;
	}

	/**
	 * Retorna uma representação formatada em String do acessório.
     * 
     * @return String formatada contendo ID, descrição e valor da diária
     */
	@Override
	public String toString() {
		return String.format("%d. %s - Diária: R$ %1.2f", id, description, dailyRate);
	}
    
}