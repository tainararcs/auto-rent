package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
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

/**
 * Managed Bean com escopo de sessão para gerir o fluxo de locações e formulários na camada web (JSF)[cite: 52].
 */
@RequestScoped
@Named("rentalMB")
public class RentalMB implements Serializable {
	
	/** Constante de serialização[cite: 52]. */
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
	
	private Long pickupAgencyId;
	private Long returnAgencyId;
	private Long vehicleId;
	private List<Long> selectedAccessoryIds;
	
	private List<Rental> rentalsUserList;
	private List<Vehicle> availableVehiclesList;
	private List<Agency> agenciesList;
	private List<Accessory> accessoriesList;
	
	/** Retorna o ID da agência de retirada[cite: 52]. */
	public Long getPickupAgencyId() {
		return pickupAgencyId;
	}

	/** Define o ID da agência de retirada[cite: 52]. */
	public void setPickupAgencyId(Long pickupAgencyId) {
		this.pickupAgencyId = pickupAgencyId;
	}

	/** Retorna o ID da agência de devolução[cite: 52]. */
	public Long getReturnAgencyId() {
		return returnAgencyId;
	}

	/** Define o ID da agência de devolução[cite: 52]. */
	public void setReturnAgencyId(Long returnAgencyId) {
		this.returnAgencyId = returnAgencyId;
	}

	/** Retorna o ID do veículo selecionado[cite: 52]. */
	public Long getVehicleId() {
		return vehicleId;
	}

	/** Define o ID do veículo selecionado[cite: 52]. */
	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}
	
	/** Retorna a lista de acessórios disponíveis[cite: 52]. */
	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	/** Define a lista de acessórios disponíveis[cite: 52]. */
	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

	/** Retorna a lista de locações do utilizador[cite: 52]. */
	public List<Rental> getRentalsUserList() {
		return rentalsUserList;
	}

	/** Define a lista de locações do utilizador[cite: 52]. */
	public void setRentalsUserList(List<Rental> rentalsUserList) {
		this.rentalsUserList = rentalsUserList;
	}
	
	/** Retorna a lista de veículos disponíveis[cite: 52]. */
	public List<Vehicle> getAvailableVehiclesList() {
		return availableVehiclesList;
	}

	/** Define a lista de veículos disponíveis[cite: 52]. */
	public void setAvailableVehiclesList(List<Vehicle> availableVehiclesList) {
		this.availableVehiclesList = availableVehiclesList;
	}
	
	/** Retorna a lista de agências[cite: 52]. */
	public List<Agency> getAgenciesList() {
		return agenciesList;
	}

	/** Define a lista de agências[cite: 52]. */
	public void setAgenciesList(List<Agency> agenciesList) {
		this.agenciesList = agenciesList;
	}
	
	/** Retorna os IDs dos acessórios selecionados[cite: 52]. */
	public List<Long> getSelectedAccessoryIds() {
		return selectedAccessoryIds;
	}

	/** Define os IDs dos acessórios selecionados[cite: 52]. */
	public void setSelectedAccessoryIds(List<Long> selectedAccessoryIds) {
		this.selectedAccessoryIds = selectedAccessoryIds;
	}

	/**
	 * Inicializa os dados e listas necessários para o painel de locações[cite: 52].
	 */
	@PostConstruct
	public void init() {
		try {
			agenciesList = iAgency.listAll();
			if (agenciesList == null) agenciesList = new ArrayList<>();

			availableVehiclesList = iVehicle.getByStatus(VehicleStatus.AVAILABLE);
			if (availableVehiclesList == null) availableVehiclesList = new ArrayList<>();

			accessoriesList = iAccessory.listAll();
			if (accessoriesList == null) accessoriesList = new ArrayList<>();

			if (userMB != null && userMB.getLoggedUser() != null) {
			    User loggedUser = userMB.getLoggedUser();
			    
			    // Imprime no console do WildFly para sabermos exatamente o que vem gravado
			    System.out.println("=== UTILIZADOR LOGADO: " + loggedUser.getEmail() + " | TIPO: " + loggedUser.getType() + " ===");
			    
			    // Validação flexível cobrindo variações comuns (EMPLOYEE ou ADMIN)
			    String userType = (loggedUser.getType() != null) ? loggedUser.getType().name().toUpperCase() : "";
			    boolean isAdmin = userType.contains("EMPLOYEE") || userType.contains("ADMIN");
			    
			    if (isAdmin) {
			        rentalsUserList = iRental.listAll();
			        System.out.println("=== ADMIN DETETADO: A carregar TODAS as locações. Total: " + (rentalsUserList != null ? rentalsUserList.size() : 0) + " ===");
			    } else {
			        rentalsUserList = iRental.getRentalsByUser(loggedUser.getId());
			        System.out.println("=== CLIENTE DETETADO: A carregar locações do ID: " + loggedUser.getId() + " ===");
			    }
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

	/**
	 * Processa a criação de uma nova locação a partir dos dados do formulário web.
	 * 
	 * @return O redirecionamento para a página de listagem ou null em caso de erro
	 */
	public String createRental() {
		if (userMB == null || userMB.getLoggedUser() == null) 
			return "login?faces-redirect=true";

		User client = userMB.getLoggedUser();
		Vehicle vehicle = (vehicleId != null) ? iVehicle.findById(vehicleId) : null;
		Agency pickupAgency = (pickupAgencyId != null) ? iAgency.findById(pickupAgencyId) : null;
		Agency returnAgency = (returnAgencyId != null) ? iAgency.findById(returnAgencyId) : null;

		if (client == null || vehicle == null || pickupAgency == null || returnAgency == null) 
			return null; 

		Rental rental = new Rental(client, vehicle, pickupAgency, returnAgency);

		// Busca os objetos Accessory reais através dos IDs selecionados e atribui à locação
		if (selectedAccessoryIds != null && !selectedAccessoryIds.isEmpty()) {
		    List<Accessory> chosenAccessories = new ArrayList<>();
		    double extraValue = 0.0;
		    
		    for (Long accId : selectedAccessoryIds) {
		        Accessory acc = iAccessory.findById(accId);
		        if (acc != null) {
		            chosenAccessories.add(acc);
		            if (acc.getDailyRate() != null) 
		                extraValue += acc.getDailyRate();
		        }
		    }
		    rental.setAccessoriesList(chosenAccessories);
		    rental.setTotalValue(rental.getTotalValue() + extraValue);
		}

		if (iRental.create(rental)) {
			pickupAgencyId = null;
			returnAgencyId = null;
			vehicleId = null;
			selectedAccessoryIds = null;
			
			return "listrental?faces-redirect=true";
		}
		
		return null;
	}

}