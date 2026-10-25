package trcs.autorent.managedbean;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import trcs.autorent.model.Agency;
import trcs.autorent.sessionbean.IAgency;

@RequestScoped
@Named("agencyMB")
public class AgencyMB implements Serializable {
	/**	*/
	private static final long serialVersionUID = 1L;

	@EJB
	private IAgency iAgency;
	private List<Agency> agenciesList;
	
	@PostConstruct
	private void init() {
		agenciesList = iAgency.listAll();
	}

	public List<Agency> getAgenciesList() {
		return agenciesList;
	}

	public void setAgenciesList(List<Agency> agenciesList) {
		this.agenciesList = agenciesList;
	}
	
}
