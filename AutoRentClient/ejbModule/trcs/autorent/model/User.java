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
	/**	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, unique = true, length = 14)
	private String cpf;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String passwordHash;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
	private UserType type;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Rental> rentalsList;
	
	public User() {}

	public User(String name, String cpf, String email, String passwordHash, UserType type) {
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.passwordHash = passwordHash;
		this.type = type;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
	
	public List<Rental> getRentalsList() {
		return rentalsList;
	}

	public void setRentalsList(List<Rental> rentalsList) {
		this.rentalsList = rentalsList;
	}

	@Override
	public String toString() {
	      return String.format("%d. %s | CPF: %s | E-mail: %s | Tipo: %s", id, name, cpf, email, type.getType());
	  }
	
}
