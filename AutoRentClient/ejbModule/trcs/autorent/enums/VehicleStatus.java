package trcs.autorent.enums;

/**
 * Enumeração que define os estados operacionais em que um veículo da frota pode se encontrar.
 * 
 * @author AutoRent
 * @version 1.0
 */
public enum VehicleStatus {
	
	/** Veículo livre e pronto para ser alugado. */
	AVAILABLE("Disponível"),
	
	/** Veículo atualmente alugado por um cliente. */
	RENTED("Alugado"),
	
	/** Veículo temporariamente reservado para uma locação pendente. */
	RESERVED("Reservado"),
	
	/** Veículo imobilizado na oficina para reparos ou revisões. */
	MAINTENANCE("Em Manutenção");
	
	/** Descrição textual legível do status operacional. */
	private final String status;

	/**
	 * Construtor do enum VehicleStatus.
	 * 
	 * @param status A descrição textual do status operacional
	 */
	private VehicleStatus(String status) {
		this.status = status;
	}

	/**
	 * Retorna a descrição textual legível do status operacional.
	 * 
	 * @return O status em formato de String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Retorna um array contendo todas as descrições textuais dos status operacionais disponíveis.
	 * Útil para preenchimento de componentes visuais, como menus de seleção (selects).
	 * 
	 * @return Array de Strings com os nomes dos status
	 */
	public static String[] valuesString() {
		String[] valuesArray = new String[values().length];
		
		short index = 0;
		for (VehicleStatus status : values()) 
			valuesArray[index++] = status.getStatus();
		
		return valuesArray;
	}
}