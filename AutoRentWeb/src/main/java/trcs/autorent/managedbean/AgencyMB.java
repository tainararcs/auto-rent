package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.model.Agency;
import trcs.autorent.sessionbean.IAgency;

/**
 * Managed Bean com escopo de requisição para gerir as agências na camada web (JSF)[cite: 50].
 */
@RequestScoped
@Named("agencyMB")
public class AgencyMB implements Serializable {
	
	/** Constante de serialização[cite: 50]. */
	private static final long serialVersionUID = 1L;

	@EJB
	private IAgency iAgency;
	
	/** Lista de agências cadastradas[cite: 50]. */
	private List<Agency> agenciesList;
	
	/**
	 * Inicializa o bean carregando todas as agências disponíveis[cite: 50].
	 */
	@PostConstruct
	private void init() {
		agenciesList = iAgency.listAll();
	}

	/**
	 * Retorna a lista de agências[cite: 50].
	 * 
	 * @return Lista de agências[cite: 50]
	 */
	public List<Agency> getAgenciesList() {
		return agenciesList;
	}

	/**
	 * Define a lista de agências[cite: 50].
	 * 
	 * @param agenciesList Lista a ser configurada[cite: 50]
	 */
	public void setAgenciesList(List<Agency> agenciesList) {
		this.agenciesList = agenciesList;
	}
	
}