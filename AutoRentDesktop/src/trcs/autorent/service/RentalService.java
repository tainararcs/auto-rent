package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Rental;
import trcs.autorent.model.User;
import trcs.autorent.model.Vehicle;
import trcs.autorent.sessionbean.IAgency;
import trcs.autorent.sessionbean.IRental;
import trcs.autorent.sessionbean.IUser;
import trcs.autorent.sessionbean.IVehicle; 

public class RentalService {

	private IRental iRental;
	
    public RentalService() {
        // Faz o lookup do Session Bean Stateless de locações.
        iRental = new Lookup<IRental>().lookup("RentalSB", "IRental");
    }

    public boolean createRental() {
    	if (iRental == null) return false;
    	
    	long clientId = Long.parseLong(InputOutput.readString("ID do Cliente:", Consts.CREATE_RENTAL));
		long vehicleId = Long.parseLong(InputOutput.readString("ID do Veículo:", Consts.CREATE_RENTAL));
		long agencyId = Long.parseLong(InputOutput.readString("ID da Agência:", Consts.CREATE_RENTAL));
		
		// Entidades básicas necessárias para a locação.
		User client = new Lookup<IUser>().lookup("UserSB", "IUser").findById(clientId);
		Vehicle vehicle = new Lookup<IVehicle>().lookup("VehicleSB", "IVehicle").findById(vehicleId);
		Agency agency = new Lookup<IAgency>().lookup("AgencySB", "IAgency").findById(agencyId);
		
		if (client == null || vehicle == null || agency == null) {
			InputOutput.showInfo("Cliente, Veículo ou Agência inválidos/não encontrados", Consts.CREATE_RENTAL);
		    return false;
		}
		
		Rental rental = new Rental(client, vehicle, agency, agency);
		if (!iRental.create(rental)) {
			InputOutput.showInfo("Erro ao realizar locação", Consts.CREATE_RENTAL);
			return false;
		}
		
		InputOutput.showInfo("Locação/Reserva realizada com sucesso!", Consts.CREATE_RENTAL);
		return true;
    }
    
    public boolean listActiveRentals() {
    	if (iRental == null) return false;
    	   
    	List<Rental> activeRentalsList = iRental.getActiveRentals();
		if (activeRentalsList == null || activeRentalsList.isEmpty()) {
			InputOutput.showInfo("Nenhuma locação ativa no momento", Consts.LIST_ACTIVE_RENTALS);
			return false;
		}
		
		InputOutput.writeTextArea(null);
		InputOutput.writeTextArea("--- Locações em Andamento ---\n\n");
		for (Rental rental : activeRentalsList) 
			InputOutput.writeTextArea(String.format("%s\n\n", rental));
		
		InputOutput.showTextArea(InputOutput.getTextArea(), Consts.LIST_ACTIVE_RENTALS);
		return true;
    }

    public boolean registerPickup() {
        if (iRental == null) return false;
        
        long rentalId = Long.parseLong(InputOutput.readString("Digite o ID da Locação para registrar a retirada:", Consts.REGISTER_PICKUP));
		if (!iRental.registerPickup(rentalId)) {
			InputOutput.showInfo("Erro ao processar retirada do veículo", Consts.CREATE_RENTAL);
			return false;
		}
		
		InputOutput.showInfo("Processo de retirada finalizado", Consts.CREATE_RENTAL);
        return true;
    }

    public boolean registerReturn() {
    	if (iRental == null) return false;
        
        long rentalId = Long.parseLong(InputOutput.readString("Digite o ID da Locação para registrar a devolução:", Consts.REGISTER_RETURN));
		if (!iRental.registerReturn(rentalId)) {
			InputOutput.showInfo("Erro ao processar devolução do veículo", Consts.REGISTER_RETURN);
			return false;
		}
		
		InputOutput.showInfo("Processo de devolução finalizado", Consts.CREATE_RENTAL);
        return true;
    }
    
}
