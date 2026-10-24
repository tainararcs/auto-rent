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
	
	public static String[] valuesString() {
		String[] valuesArray = new String[values().length];
		
		short index = 0;
		for (VehicleCategory category : values()) 
			valuesArray[index++] = category.getCategory();
		
		return valuesArray;
	}
}
