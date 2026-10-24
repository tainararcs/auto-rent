package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.model.Agency;
import trcs.autorent.sessionbean.IAgency;

public class AgencyService {

	private IAgency iAgency;
	
    public AgencyService() {
		this.iAgency = new Lookup<IAgency>().lookup("AgencySB", "IAgency");
	}

	public boolean createAgency() {
		if (iAgency == null) return false;
       
		String name = InputOutput.readString("Nome da Agência:", Consts.CREATE_AGENCY);
		String address = InputOutput.readString("Endereço:", Consts.CREATE_AGENCY);
		String city = InputOutput.readString("Cidade:", Consts.CREATE_AGENCY);
		String phoneNumber = InputOutput.readString("Número do Telefone:", Consts.CREATE_AGENCY);
		
		Agency agency = new Agency(name, address, city, phoneNumber);
        if (!iAgency.create(agency)) {
        	InputOutput.showError("Erro ao cadastrar agência", Consts.CREATE_USER);
        	return false;
        }	
        
        InputOutput.showInfo("Agência cadastrado com sucesso!", Consts.CREATE_USER);
        return true;
    }
	
	public boolean listAgencies() {
		List<Agency> agenciesList = iAgency.listAll();
		if (agenciesList == null || agenciesList.isEmpty()) {
			InputOutput.showAlert("Nenhum cliente cadastrado", Consts.LIST_AGENCIES);
			return false;
		}
		
		InputOutput.writeTextArea(null);
		InputOutput.writeTextArea("--- Agências Cadastradas ---\n\n");
		for (Agency agency : agenciesList) 
			InputOutput.writeTextArea(String.format("%s\n", agency));
		
		InputOutput.showTextArea(InputOutput.getTextArea(), Consts.LIST_AGENCIES);
		return true;
	}
    
}
