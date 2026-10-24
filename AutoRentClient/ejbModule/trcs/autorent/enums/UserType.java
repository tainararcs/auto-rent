package trcs.autorent.enums;

public enum UserType {
	CLIENT("Cliente"), 
	EMPLOYEE("Funcionário");
	
	private final String type;

	private UserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
