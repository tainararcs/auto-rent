package trcs.autorent.managedbean;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.model.Accessory;
import trcs.autorent.sessionbean.IAccessory;

/**
 * Managed Bean com escopo de requisição para gerir a listagem de acessórios na camada web (JSF)[cite: 49].
 */
@RequestScoped
@Named("accessoryMB")
public class AccessoryMB {
	
	@EJB
	private IAccessory iAccessory;
	
	/** Lista de acessórios disponíveis para exibição[cite: 49]. */
	private List<Accessory> accessoriesList;
	
	/**
	 * Inicializa o bean carregando todos os acessórios do sistema[cite: 49].
	 */
	@PostConstruct
	private void init() {
		accessoriesList = iAccessory.listAll();
	}

	/**
	 * Retorna a lista de acessórios[cite: 49].
	 * 
	 * @return Lista de acessórios[cite: 49]
	 */
	public List<Accessory> getAccessoriesList() {
		return accessoriesList;
	}

	/**
	 * Define a lista de acessórios[cite: 49].
	 * 
	 * @param accessoriesList Lista a ser configurada[cite: 49]
	 */
	public void setAccessoriesList(List<Accessory> accessoriesList) {
		this.accessoriesList = accessoriesList;
	}

}