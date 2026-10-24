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

	public static String[] valuesString() {
		String[] valuesArray = new String[values().length];
		
		short index = 0;
		for (VehicleStatus status : values()) 
			valuesArray[index++] = status.getStatus();
		
		return valuesArray;
	}
}
