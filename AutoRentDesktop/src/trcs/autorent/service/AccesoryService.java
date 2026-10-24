package trcs.autorent.service;

import java.util.List;

import mos.io.InputOutput;
import trcs.autorent.consts.Consts;
import trcs.autorent.model.Accessory;
import trcs.autorent.sessionbean.IAccessory;

public class AccesoryService {
	
	private IAccessory iAccessory;
	
    public AccesoryService() {
    	iAccessory = new Lookup<IAccessory>().lookup("AccessorySB", "IAccessory");
	}

	public boolean createAccesory() {
        if (iAccessory == null) return false;
        
        String description = InputOutput.readString("Descrição:", Consts.CREATE_ACCESSORY);
		Double dailyRate = Double.valueOf(InputOutput.readString("Taxa diária (R$):", Consts.CREATE_ACCESSORY));
		
		Accessory accessory = new Accessory(description, dailyRate, null);
        iAccessory.create(accessory);
        return true;
    }
    
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
