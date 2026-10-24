package trcs.autorent.enums;

public enum VehicleStatus {
	AVAILABLE("Disponível"),
	RENTED("Alugado"),
	RESERVED("Reservado"),
	MAINTENANCE("Em Manutenção");
	
	private final String status;

	private VehicleStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
