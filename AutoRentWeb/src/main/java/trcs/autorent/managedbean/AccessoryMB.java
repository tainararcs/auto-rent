package trcs.autorent.managedbean;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.model.Accessory;
import trcs.autorent.sessionbean.IAccessory;

@RequestScoped
@Named("accessoryMB")
public class AccessoryMB {
	
	@EJB
	private IAccessory iAccessory;
	
	private List<Accessory> accessoriesList;
	
	@PostConstruct
	private void init() {
		accessoriesList = iAccessory.listAll();
	}

	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

}
