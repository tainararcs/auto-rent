package trcs.autorent.enums;

public enum VehicleCategory {
	CAR("Carro"),
    MOTORCYCLE("Moto"),
    UTILITY("Utilitário");
    
	private final String category;

	private VehicleCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

}
