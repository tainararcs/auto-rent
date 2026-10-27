package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.model.Accessory;
import trcs.autorent.sessionbean.IAccessory;

/**
 * Serviço de lógica de apresentação/cliente para a entidade Accessory na consola.
 */
public class AccesoryService {
	
	private IAccessory iAccessory;
	
	/**
     * Construtor que efetua a busca (lookup) remota do EJB AccessorySB.
     */
    public AccesoryService() {
    	iAccessory = new Lookup<IAccessory>().lookup("AccessorySB", "IAccessory");
	}

    /**
     * Solicita os dados ao utilizador via consola e cria um novo acessório.
     * 
     * @return true se o cadastro for efetuado com sucesso, false caso contrário
     */
	public boolean createAccesory() {
        if (iAccessory == null) return false;
        
        String description = InputOutput.readString("Descrição:", Consts.CREATE_ACCESSORY);
		Double dailyRate = Double.valueOf(InputOutput.readString("Taxa diária (R$):", Consts.CREATE_ACCESSORY));
		
		Accessory accessory = new Accessory(description, dailyRate, null);
        iAccessory.create(accessory);
        return true;
    }
    
	/**
	 * Lista todos os acessórios registados no sistema através da consola.
	 * 
	 * @return true se houver registos e forem exibidos, false caso contrário
	 */
	public boolean listAccesories() {
		List<Accessory> accessoriesList = iAccessory.listAll();
		if (accessoriesList == null || accessoriesList.isEmpty()) {
			InputOutput.showAlert("Nenhum cliente cadastrado", Consts.LIST_ACCESSORIES);
			return false;
		}
		
		InputOutput.writeTextArea(null);
		InputOutput.writeTextArea("--- Acessórios Cadastrados ---\n\n");
		for (Accessory accessory : accessoriesList) 
			InputOutput.writeTextArea(String.format("%s\n", accessory));
		
		InputOutput.showTextArea(InputOutput.getTextArea(), Consts.LIST_ACCESSORIES);
		return true;
    }
}