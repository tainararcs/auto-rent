package trcs.autorent.gui;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.service.AccesoryService;
import trcs.autorent.service.AgencyService;
import trcs.autorent.service.RentalService;
import trcs.autorent.service.UserService;
import trcs.autorent.service.VehicleService;

public class AutoRent {
	// Instanciando os services para interagir com o servidor EJB.
	private final UserService userService = new UserService();
	private final AgencyService agencyService = new AgencyService();
	private final VehicleService vehicleService = new VehicleService();
	private final RentalService rentalService = new RentalService();
	private final AccesoryService accesoryService = new AccesoryService();
	
	private int login() {
		while (!userService.login())
			return login();
		
		return menu();
	}
	
	private int menu() {
		while (true)
			switch (InputOutput.menu("Selecione uma opção", Consts.AUTO_RENT, Consts.INITIAL_OPTIONS, Consts.INITIAL_OPTIONS[0])) {
				case 0 -> createMenu();
				case 1 -> listMenu();
				case 2 -> updateMenu();
				default -> System.exit(0);
			}
	}
	
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
	
	private void updateMenu() {
		switch (InputOutput.menu(Consts.UPDATE, Consts.AUTO_RENT, Consts.UPDATE_OPTIONS, Consts.UPDATE_OPTIONS[0])) {
			case 0 -> rentalService.registerPickup();
			case 1 -> rentalService.registerReturn();
			case 2 -> vehicleService.updateVehicleStatus();
			case 3 -> { return; }
			default -> System.exit(0);
		}
	}

	public static void main(String[] args) {
		new AutoRent().login();
	}
	
}
