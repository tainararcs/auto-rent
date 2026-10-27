package trcs.autorent.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import trcs.autorent.enums.UserType;

/**
 * Representa tanto os clientes quanto os funcionários da locadora, controlados pelo atributo type utilizando um enum dedicado.   
 */
@Entity
@Table(name = "users") 
public class User implements Serializable {
	
	/** Constante de serialização para controle de versão da classe. */
	private static final long serialVersionUID = 1L;
	
	/** Identificador único do usuário gerado automaticamente. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Nome completo do usuário. */
	@Column(nullable = false, length = 100)
	private String name;
	
	/** Número do CPF do usuário (único). */
	@Column(nullable = false, unique = true, length = 14)
	private String cpf;
	
	/** Endereço de e-mail do usuário utilizado no login (único). */
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	/** Hash criptográfico da senha do usuário. */
	@Column(nullable = false, length = 255)
	private String passwordHash;
	
	/** Tipo/Papel do usuário no sistema (Enum: CLIENT ou EMPLOYEE). */
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private UserType type;

	/** Histórico de locações associadas a este usuário (se cliente). */
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rental> rentalsList;
	
	/**
     * Construtor padrão vazio da classe User.
     */
	public User() {}

	/**
     * Construtor com parâmetros para criação de um usuário completo.
     * 
     * @param name Nome completo
     * @param cpf CPF do usuário
     * @param email E-mail de cadastro
     * @param passwordHash Hash da senha
     * @param type Tipo de usuário (UserType)
     */
	public User(String name, String cpf, String email, String passwordHash, UserType type) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.passwordHash = passwordHash;
		this.type = type;
	}

	/**
     * Retorna o ID do usuário.
     * 
     * @return O ID
     */
	public Long getId() {
		return id;
	}

	/**
     * Define o ID do usuário.
     * 
     * @param id O ID a ser configurado
     */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Retorna o nome do usuário.
     * 
     * @return O nome
     */
	public String getName() {
		return name;
	}

	/**
     * Define o nome do usuário.
     * 
     * @param name O nome a ser configurado
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Retorna o CPF do usuário.
     * 
     * @return O CPF
     */
	public String getCpf() {
		return cpf;
	}

	/**
     * Define o CPF do usuário.
     * 
     * @param cpf O CPF a ser configurado
     */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
     * Retorna o e-mail do usuário.
     * 
     * @return O e-mail
     */
	public String getEmail() {
		return email;
	}

	/**
     * Define o e-mail do usuário.
     * 
     * @param email O e-mail a ser configurado
     */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
     * Retorna o hash da senha do usuário.
     * 
     * @return O hash da senha
     */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
     * Define o hash da senha do usuário.
     * 
     * @param passwordHash O hash a ser configurado
     */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
     * Retorna o tipo do usuário.
     * 
     * @return O tipo (UserType)
     */
	public UserType getType() {
		return type;
	}

	/**
     * Define o tipo do usuário.
     * 
     * @param type O tipo a ser configurado
     */
	public void setType(UserType type) {
		this.type = type;
	}
	
	/**
     * Retorna a lista de locações do usuário.
     * 
     * @return Lista de locações
     */
	public List<Rental> getRentalsList() {
		return rentalsList;
	}

	/**
     * Define a lista de locações do usuário.
     * 
     * @param rentalsList Lista de locações a ser configurada
     */
	public void setRentalsList(List<Rental> rentalsList) {
		this.rentalsList = rentalsList;
	}

	/**
     * Retorna uma representação formatada em String dos dados do usuário.
     * 
     * @return String descritiva do usuário
     */
	@Override
	public String toString() {
	      return String.format("%d. %s - CPF: %s | E-mail: %s | Tipo: %s", id, name, cpf, email, type.getType());
	  }
	
}