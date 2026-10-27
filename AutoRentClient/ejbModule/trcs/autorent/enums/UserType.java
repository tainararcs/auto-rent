package trcs.autorent.enums;

/**
 * Enumeração que define os papéis/tipos de utilizadores autorizados no sistema.
 */
public enum UserType {
	
	/** Utilizador comum que efetua locações de veículos. */
	CLIENT("Cliente"), 
	
	/** Utilizador interno com privilégios administrativos e de gestão de frota. */
	EMPLOYEE("Funcionário");
	
	/** Descrição textual legível do tipo de utilizador. */
	private final String type;

	/**
	 * Construtor do enum UserType.
	 * 
	 * @param type A descrição textual do tipo de utilizador
	 */
	private UserType(String type) {
		this.type = type;
	}

	/**
	 * Retorna a descrição textual legível do tipo de utilizador.
	 * 
	 * @return O tipo em formato de String
	 */
	public String getType() {
		return type;
	}

}