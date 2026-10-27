package trcs.autorent.consts;

/**
 * Interface que armazena as constantes textuais, títulos de menus e opções da interface de consola.
 */
public interface Consts {
	
	/** Nome principal do sistema. */
	public final String AUTO_RENT = "Auto Rent";
	
	/** Ações principais do sistema. */
	public final String CREATE = "Cadastrar", LIST = "Listar", UPDATE = "Atualizar", BACK = "Voltar";
	
	/** Opções de cadastro específicas. */
	public final String CREATE_USER  = "Cadastrar Cliente", 
						CREATE_AGENCY = "Cadastrar Agência", 
						CREATE_VEHICLE = "Cadastrar Veículo", 
						CREATE_ACCESSORY = "Cadastrar Acessório", 
						CREATE_RENTAL = "Realizar Locação";
	
	/** Opções de listagem e consulta específicas. */
	public final String LIST_USERS = "Listar Clientes", 
						LIST_AGENCIES  = "Listar Agências", 
						LIST_VEHICLES_BY_AGENCY = "Consultar Frota da Agência", 
						LIST_ACTIVE_RENTALS = "Consultar Locações em Andamento",
						LIST_ACCESSORIES = "Listar Acessórios"; 
	
	/** Opções de atualização e processos específicos. */
	public final String REGISTER_PICKUP = "Registrar Retirada de Veículo",
						REGISTER_RETURN = "Registrar Devolução",
						UPDATE_STATUS = "Alterar Status do Veículo";
	
	/** Array de opções iniciais apresentadas no menu principal. */
	public final String[] INITIAL_OPTIONS = { CREATE, LIST, UPDATE };
	
	/** Array de opções para o menu de cadastros. */
	public final String[] CREATE_OPTIONS = { CREATE_USER, CREATE_AGENCY, CREATE_VEHICLE, CREATE_ACCESSORY, CREATE_RENTAL, BACK };
	
	/** Array de opções para o menu de listagens. */
	public final String[] LIST_OPTIONS = { LIST_USERS, LIST_AGENCIES, LIST_VEHICLES_BY_AGENCY, LIST_ACTIVE_RENTALS, LIST_ACCESSORIES, BACK };
	
	/** Array de opções para o menu de atualizações e operações. */
	public final String[] UPDATE_OPTIONS = { REGISTER_PICKUP, REGISTER_RETURN, UPDATE_STATUS, BACK };
	
}