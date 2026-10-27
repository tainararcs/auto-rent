package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.model.Agency;
import trcs.autorent.sessionbean.IAgency;

/**
 * Serviço de lógica de apresentação/cliente para a entidade Agency na consola.
 */
public class AgencyService {

	private IAgency iAgency;
	
	/**
     * Construtor que efetua a busca (lookup) remota do EJB AgencySB.
     */
    public AgencyService() {
		this.iAgency = new Lookup<IAgency>().lookup("AgencySB", "IAgency");
	}

    /**
     * Solicita os dados ao utilizador e cria uma nova agência no sistema.
     * 
     * @return true se criada com sucesso, false caso contrário
     */
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
	
	/**
	 * Lista todas as agências cadastradas na consola.
	 * 
	 * @return true se listadas com sucesso, false caso contrário
	 */
	public boolean listAgencies() {
		List<Agency> agenciesList = iAgency.listAll();
		if (agenciesList == null || agenciesList.isEmpty()) {
			InputOutput.showAlert("Nenhum cliente cadastrado", Consts.LIST_AGENCIES);
			return false;
		}
		
		InputOutput.writeTextArea(null);
		InputOutput.writeTextArea("--- Agências Cadastradas ---\n\n");
		for (Agency agency : agenciesList) 
			InputOutput.writeTextArea(String.format("%s\n\n", agency));
		
		InputOutput.showTextArea(InputOutput.getTextArea(), Consts.LIST_AGENCIES);
		return true;
	}
    
}