package trcs.autorent.enums;

/**
 * Enumeração que define os diferentes estados possíveis de um contrato de locação.
 */
public enum RentalStatus {
	
	/** Locação efetuada e agendada, mas ainda não retirada. */
	RESERVED("Reservado"),
	
	/** Locação ativa no momento (veículo em posse do cliente). */
    ACTIVE("Ativo"),
    
    /** Locação concluída com sucesso após a devolução do veículo. */
    COMPLETED("Finalizado"),
    
    /** Locação cancelada antes ou durante o processo. */
    CANCELED("Cancledo");
	
	/** Descrição textual legível do status. */
	private final String status;

	/**
	 * Construtor do enum RentalStatus.
	 * 
	 * @param status A descrição textual do status
	 */
	private RentalStatus(String status) {
		this.status = status;
	}

	/**
	 * Retorna a descrição textual legível do status.
	 * 
	 * @return O status em formato de String
	 */
	public String getStatus() {
		return status;
	}

}