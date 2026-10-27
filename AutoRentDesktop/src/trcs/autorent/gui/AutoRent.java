package trcs.autorent.gui;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.service.AccesoryService;
import trcs.autorent.service.AgencyService;
import trcs.autorent.service.RentalService;
import trcs.autorent.service.UserService;
import trcs.autorent.service.VehicleService;

/**
 * Classe principal da interface de consola (Client Java SE) da aplicação AutoRent.
 * Controla o fluxo de navegação pelos menus e interações com os serviços EJB.
 */
public class AutoRent {
	
	// Instanciação dos serviços para interagir com o servidor EJB
	private final UserService userService = new UserService();
	private final AgencyService agencyService = new AgencyService();
	private final VehicleService vehicleService = new VehicleService();
	private final RentalService rentalService = new RentalService();
	private final AccesoryService accesoryService = new AccesoryService();
	
	/**
	 * Gerencia o fluxo de autenticação inicial do utilizador na consola.
	 * 
	 * @return O código de saída do menu principal após o login bem-sucedido
	 */
	private int login() {
		while (!userService.login())
			return login();
		
		return menu();
	}
	
	/**
	 * Apresenta o menu principal da aplicação em loop contínuo.
	 * 
     * @return Código de término de execução
	 */
	private int menu() {
		while (true)
			switch (InputOutput.menu("Selecione uma opção", Consts.AUTO_RENT, Consts.INITIAL_OPTIONS, Consts.INITIAL_OPTIONS[0])) {
				case 0 -> createMenu();
				case 1 -> listMenu();
				case 2 -> updateMenu();
				default -> System.exit(0);
			}
	}
	
	/**
	 * Apresenta o sub-menu de cadastros.
	 */
	private void createMenu() {
		switch (InputOutput.menu(Consts.CREATE, Consts.AUTO_RENT, Consts.CREATE_OPTIONS, Consts.CREATE_OPTIONS[0])) {
			case 0 -> userService.createUser();
			case 1 -> agencyService.createAgency();
			case 2 -> vehicleService.createVehicle();
			case 3 -> accesoryService.createAccesory();
			case 4 -> rentalService.createRental();
			case 5 -> { return; }
			default -> System.exit(0);
		}
	}
	
	/**
	 * Apresenta o sub-menu de listagens e consultas.
	 */
	private void listMenu() {
		switch (InputOutput.menu(Consts.LIST, Consts.AUTO_RENT, Consts.LIST_OPTIONS, Consts.LIST_OPTIONS[0])) {
			case 0 -> userService.listUsers();
			case 1 -> agencyService.listAgencies();
			case 2 -> vehicleService.listVehiclesByAgency();
			case 3 -> rentalService.listActiveRentals();
			case 4 -> accesoryService.listAccesories();
			case 5 -> { return; }
			default -> System.exit(0);
		}
	}
	
	/**
	 * Apresenta o sub-menu de operações, devoluções e atualizações.
	 */
	private void updateMenu() {
		switch (InputOutput.menu(Consts.UPDATE, Consts.AUTO_RENT, Consts.UPDATE_OPTIONS, Consts.UPDATE_OPTIONS[0])) {
			case 0 -> rentalService.registerPickup();
			case 1 -> rentalService.registerReturn();
			case 2 -> vehicleService.updateVehicleStatus();
			case 3 -> { return; }
			default -> System.exit(0);
		}
	}

	/**
	 * Ponto de entrada (Main) da aplicação em consola.
	 * 
	 * @param args Argumentos da linha de comandos
	 */
	public static void main(String[] args) {
		new AutoRent().login();
	}
	
}