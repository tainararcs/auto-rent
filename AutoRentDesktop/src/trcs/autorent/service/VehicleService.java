package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.enums.VehicleCategory;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Vehicle;
import trcs.autorent.sessionbean.IAgency;
import trcs.autorent.sessionbean.IVehicle;

public class VehicleService {

	private IVehicle iVehicle;
    private IAgency iAgency;
    
    public VehicleService() {
        iVehicle = new Lookup<IVehicle>().lookup("VehicleSB", "IVehicle");
        iAgency = new Lookup<IAgency>().lookup("AgencySB", "IAgency");
    }
    
    public boolean createVehicle() {
    	if (iVehicle == null || iAgency == null) return false;
        
        String plate = InputOutput.readString("Placa do Veículo:", Consts.AUTO_RENT);
		String brand = InputOutput.readString("Marca:", Consts.AUTO_RENT);
		String model = InputOutput.readString("Modelo:", Consts.AUTO_RENT);
		Long year = Long.parseLong(InputOutput.readString("Ano:", Consts.AUTO_RENT));
		Double dailyRate = Double.parseDouble(InputOutput.readString("Taxa Diária:", Consts.AUTO_RENT));
		
		int categoryIndex = InputOutput.menu("Categoria", Consts.CREATE_VEHICLE, VehicleCategory.valuesString(), VehicleCategory.valuesString()[0]);
		VehicleCategory category = VehicleCategory.values()[categoryIndex];
				
        Long agencyId = Long.parseLong(InputOutput.readString("ID da Agência:", Consts.CREATE_VEHICLE));
        Agency agency = iAgency.findById(agencyId);
		
        Vehicle vehicle = new Vehicle(plate, brand, model, year, category, dailyRate, agency, null);
        if (!iVehicle.create(vehicle)) {
        	InputOutput.showError("Erro ao cadastrar veículos", Consts.CREATE_VEHICLE);
        	return false;
        }	
        
        InputOutput.showInfo("Veículo cadastrado com sucesso!", Consts.CREATE_VEHICLE);
        return true;
    }

    /**
     * Lista toda a frota de veículos de uma determinada agência pelo ID da agência.
     */
    public boolean listVehiclesByAgency() {
    	long agencyId = Long.parseLong(InputOutput.readString("Digite o ID da agência:", Consts.LIST_VEHICLES_BY_AGENCY));
		Agency agency = iAgency.findById(agencyId);
		
		if (agency == null) {
			InputOutput.showInfo("Agência não encontrada", Consts.LIST_VEHICLES_BY_AGENCY);
			return false;
		}
			
		List<Vehicle> vehiclesList = iVehicle.getByAgency(agency);
		if (vehiclesList == null || vehiclesList.isEmpty()) {
			InputOutput.showInfo("Nenhum veículo encontrado nesta agência", Consts.LIST_VEHICLES_BY_AGENCY);
			return false;
		}

		InputOutput.writeTextArea(null);
		InputOutput.writeTextArea(String.format("--- Veículos da Agência %s ---\n\n", agency.getName()));
		for (Vehicle vehicle : vehiclesList) 
			InputOutput.writeTextArea(String.format("%s\n\n", vehicle));
		
		InputOutput.showTextArea(InputOutput.getTextArea(), Consts.LIST_VEHICLES_BY_AGENCY);
		return true;
    }
    
    public boolean updateVehicleStatus() {
        if (iVehicle == null) return false;
        
        long vehicleId = Long.parseLong(InputOutput.readString("Digite o ID do Veículo:", Consts.UPDATE_STATUS));
        int statusIndex = InputOutput.menu("Novo Status do Veículo:", Consts.UPDATE_STATUS, VehicleStatus.valuesString(), VehicleStatus.valuesString()[0]);
        
        iVehicle.updateStatus(vehicleId, VehicleStatus.values()[statusIndex]);
		InputOutput.showInfo("Status do veículo alterado com sucesso!", Consts.UPDATE_STATUS);
		return true;
    }
    
}
