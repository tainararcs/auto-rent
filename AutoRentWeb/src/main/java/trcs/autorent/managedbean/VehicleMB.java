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

/**
 * Managed Bean com escopo de requisição para gerir a listagem e filtragem de veículos na camada web (JSF)[cite: 54].
 */
@RequestScoped
@Named("vehicleMB")
public class VehicleMB implements Serializable {
	
	/** Constante de serialização[cite: 54]. */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IVehicle iVehicle;
	
	private List<Vehicle> vehiclesList;
    private VehicleStatus selectedStatus;
    private VehicleCategory selectedCategory;

    /**
     * Inicializa carregando todos os veículos por padrão[cite: 54].
     */
    @PostConstruct
    public void init() {
        vehiclesList = iVehicle.listAll();
    }
    
    /** Aplica filtros por categoria ou status na lista de veículos[cite: 54]. */
    public void filter() {
        if (selectedCategory != null) 
            vehiclesList = iVehicle.getByCategory(selectedCategory);
        else if (selectedStatus != null) 
            vehiclesList = iVehicle.getByStatus(selectedStatus);
        else 
            vehiclesList = iVehicle.listAll();
    }

    /** Limpa os filtros aplicados e restaura a lista completa[cite: 54]. */
    public void cleanFilters() {
        selectedCategory = null;
        selectedStatus = null;
        vehiclesList = iVehicle.listAll();
    }

    /** Retorna a lista de veículos[cite: 54]. */
    public List<Vehicle> getVehiclesList() {
        return vehiclesList;
    }

    /** Retorna a categoria selecionada para filtro[cite: 54]. */
    public VehicleCategory getSelectedCategory() {
        return selectedCategory;
    }

    /** Define a categoria selecionada para filtro[cite: 54]. */
    public void setSelectedCategory(VehicleCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    /** Retorna o status selecionado para filtro[cite: 54]. */
    public VehicleStatus getSelectedStatus() {
        return selectedStatus;
    }

    /** Define o status selecionado para filtro[cite: 54]. */
    public void setSelectedStatus(VehicleStatus selectedStatus) {
        this.selectedStatus = selectedStatus;
    }
    
    /** Retorna os valores possíveis de status para componentes de seleção[cite: 54]. */
    public VehicleStatus[] getStatus() {
        return VehicleStatus.values();
    }

    /** Retorna os valores possíveis de categoria para componentes de seleção[cite: 54]. */
    public VehicleCategory[] getCategories() {
        return VehicleCategory.values();
    }
}