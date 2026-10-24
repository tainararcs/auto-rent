package trcs.autorent.enums;

public enum RentalStatus {
	RESERVED("Reservado"),
    ACTIVE("Ativo"),
    COMPLETED("Finalizado"),
    CANCELED("Cancledo");
	
	private final String status;

	private RentalStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
