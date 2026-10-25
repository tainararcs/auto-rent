package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.enums.VehicleCategory;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Vehicle;
import trcs.autorent.sessionbean.IVehicle;

@RequestScoped
@Named("vehicleMB")
public class VehicleMB implements Serializable {
	/**	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IVehicle iVehicle;
	
	private List<Vehicle> vehiclesList;
    
    // Campos de Filtro.
    private VehicleStatus selectedStatus;
    private VehicleCategory selectedCategory;


    @PostConstruct
    public void init() {
        // Carrega todos os veículos por padrão ao abrir a página.
        vehiclesList = iVehicle.listAll();
    }
    
    /** Método de Filtragem. */
    public void filter() {
        if (selectedCategory != null) 
            vehiclesList = iVehicle.getByCategory(selectedCategory);
        else if (selectedStatus != null) 
            vehiclesList = iVehicle.getByStatus(selectedStatus);
        else 
            vehiclesList = iVehicle.listAll();
    }

    /** Limpar filtros. */
    public void cleanFilters() {
        selectedCategory = null;
        selectedStatus = null;
        vehiclesList = iVehicle.listAll();
    }

    public List<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    public VehicleCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(VehicleCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public VehicleStatus getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(VehicleStatus selectedStatus) {
        this.selectedStatus = selectedStatus;
    }
    
    public VehicleStatus[] getStatus() {
        return VehicleStatus.values();
    }

    public VehicleCategory[] getCategories() {
        return VehicleCategory.values();
    }
}
