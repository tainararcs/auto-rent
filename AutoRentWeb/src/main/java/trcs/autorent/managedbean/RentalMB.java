package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Accessory;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Rental;
import trcs.autorent.model.User;
import trcs.autorent.model.Vehicle;
import trcs.autorent.sessionbean.IAccessory;
import trcs.autorent.sessionbean.IAgency;
import trcs.autorent.sessionbean.IRental;
import trcs.autorent.sessionbean.IUser;
import trcs.autorent.sessionbean.IVehicle;

@SessionScoped
@Named("rentalMB")
public class RentalMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IRental iRental;
	
	@EJB
	private IUser iUser;
	
	@EJB
	private IAgency iAgency; 
	
	@EJB
	private IVehicle iVehicle; 
	
	@EJB
	private IAccessory iAccessory;
	
	@Inject
	private UserMB userMB;
	
	// IDs selecionados no formulário
	private Long pickupAgencyId;
	private Long returnAgencyId;
	private Long vehicleId;
	private List<Long> selectedAccessoryIds; // IDs dos acessórios escolhidos
	
	private List<Rental> rentalsUserList;
	private List<Vehicle> availableVehiclesList;
	private List<Agency> agenciesList;
	private List<Accessory> accessoriesList;
	
	public Long getPickupAgencyId() {
		return pickupAgencyId;
	}

	public void setPickupAgencyId(Long pickupAgencyId) {
		this.pickupAgencyId = pickupAgencyId;
	}

	public Long getReturnAgencyId() {
		return returnAgencyId;
	}

	public void setReturnAgencyId(Long returnAgencyId) {
		this.returnAgencyId = returnAgencyId;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

	public List<Rental> getRentalsUserList() {
		return rentalsUserList;
	}

	public void setRentalsUserList(List<Rental> rentalsUserList) {
		this.rentalsUserList = rentalsUserList;
	}
	
	public List<Vehicle> getAvailableVehiclesList() {
		return availableVehiclesList;
	}

	public void setAvailableVehiclesList(List<Vehicle> availableVehiclesList) {
		this.availableVehiclesList = availableVehiclesList;
	}
	
	public List<Agency> getAgenciesList() {
		return agenciesList;
	}

	public void setAgenciesList(List<Agency> agenciesList) {
		this.agenciesList = agenciesList;
	}
	
	public List<Long> getSelectedAccessoryIds() {
		return selectedAccessoryIds;
	}

	public void setSelectedAccessoryIds(List<Long> selectedAccessoryIds) {
		this.selectedAccessoryIds = selectedAccessoryIds;
	}

	@PostConstruct
	public void init() {
		try {
			agenciesList = iAgency.listAll();
			availableVehiclesList = iVehicle.getByStatus(VehicleStatus.AVAILABLE);
			accessoriesList = iAccessory.listAll();

			if (userMB != null && userMB.getLoggedUser() != null) {
				User loggedUser = userMB.getLoggedUser();
				rentalsUserList = iRental.getRentalsByUser(loggedUser.getId());
			} else {
				rentalsUserList = new ArrayList<>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			rentalsUserList = new ArrayList<>();
			agenciesList = new ArrayList<>();
			availableVehiclesList = new ArrayList<>();
			accessoriesList = new ArrayList<>();
		}
	}

	public String createRental() {
		try {
			System.out.println("=== INICIANDO CRIAÇÃO DE LOCAÇÃO ===");
			System.out.println("PickupAgencyId selecionado: " + pickupAgencyId);
			System.out.println("ReturnAgencyId selecionado: " + returnAgencyId);
			System.out.println("VehicleId selecionado: " + vehicleId);
			
			if (userMB == null || userMB.getLoggedUser() == null) {
				System.out.println("Erro: Usuário não logado.");
				return "login?faces-redirect=true";
			}

			User client = userMB.getLoggedUser();
			Vehicle vehicle = iVehicle.findById(vehicleId);
			Agency pickupAgency = iAgency.findById(pickupAgencyId);
			Agency returnAgency = iAgency.findById(returnAgencyId);

			if (client == null || vehicle == null || pickupAgency == null || returnAgency == null) {
				System.out.println("Erro: Um dos objetos recuperados do banco é NULL!");
				return null; 
			}

			// Cria a locação base
			Rental rental = new Rental(client, vehicle, pickupAgency, returnAgency);
			
			// Soma o valor dos acessórios selecionados, se houver
			if (selectedAccessoryIds != null && !selectedAccessoryIds.isEmpty()) {
				double extraValue = 0.0;
				for (Long accId : selectedAccessoryIds) {
					Accessory acc = iAccessory.findById(accId);
					if (acc != null && acc.getDailyRate() != null) {
						extraValue += acc.getDailyRate();
					}
				}
				rental.setTotalValue(rental.getTotalValue() + extraValue);
			}
			
			boolean success = iRental.create(rental);
			System.out.println("Status do iRental.create(): " + success);
			
			if (success) {
				// Limpa os campos do formulário
				pickupAgencyId = null;
				returnAgencyId = null;
				vehicleId = null;
				selectedAccessoryIds = null;
				
				// Atualiza a lista do usuário
				init();
				return "listrental?faces-redirect=true";
			}
		} catch (Exception e) {
			System.err.println("EXCEÇÃO EM createRental:");
			e.printStackTrace();
		}
		return null;
	}

}