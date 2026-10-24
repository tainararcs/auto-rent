package trcs.autorent.consts;

public interface Consts {
	public final String AUTO_RENT = "Auto Rent";
	
	public final String CREATE = "Cadastrar", LIST = "Listar", UPDATE = "Atualizar", BACK = "Voltar";
	
	public final String CREATE_USER  = "Cadastrar Cliente", 
						CREATE_AGENCY = "Cadastrar Agência", 
						CREATE_VEHICLE = "Cadastrar Veículo", 
						CREATE_ACCESSORY = "Cadastrar Acessório", 
						CREATE_RENTAL = "Realizar Locação";
	
	public final String LIST_USERS = "Listar Clientes", 
						LIST_AGENCIES  = "Listar Agências", 
						LIST_VEHICLES_BY_AGENCY = "Consultar Frota da Agência", 
						LIST_ACTIVE_RENTALS = "Consultar Locações em Andamento",
						LIST_ACCESSORIES = "Listar Acessórios"; 
	
	public final String REGISTER_PICKUP = "Registrar Retirada de Veículo",
						REGISTER_RETURN = "Registrar de Devolução",
						UPDATE_STATUS = "Alterar Status do Veículo";
	
	public final String[] INITIAL_OPTIONS = { CREATE, LIST, UPDATE };
	public final String[] CREATE_OPTIONS = { CREATE_USER, CREATE_AGENCY, CREATE_VEHICLE, CREATE_ACCESSORY, CREATE_RENTAL, BACK };
	public final String[] LIST_OPTIONS = { LIST_USERS, LIST_AGENCIES, LIST_VEHICLES_BY_AGENCY, LIST_ACTIVE_RENTALS, LIST_ACCESSORIES, BACK };
	public final String[] UPDATE_OPTIONS = { REGISTER_PICKUP, REGISTER_RETURN, UPDATE_STATUS, BACK };
	
}
