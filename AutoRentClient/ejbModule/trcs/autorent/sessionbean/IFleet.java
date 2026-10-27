package trcs.autorent.sessionbean;

import java.util.Map;

import jakarta.ejb.Remote;

/**
 * Interface remota para o catálogo e estatísticas globais da frota.
 * Mantém o estado compartilhado global da frota entre todas as agências.
 */
@Remote
public interface IFleet {
	
	/**
	 * Atualiza os dados e métricas globais da frota.
	 */
	void refresh();
	
	/**
	 * Retorna o número total de veículos registados na frota.
	 * 
	 * @return Total de veículos
	 */
	long getTotalVehicles();
	
	/**
	 * Retorna o número total de veículos atualmente disponíveis.
	 * 
	 * @return Total de veículos disponíveis
	 */
	long getTotalAvailable();
	
	/**
	 * Retorna o número total de veículos atualmente alugados.
	 * 
	 * @return Total de veículos alugados
	 */
	long getTotalRented();
	
	/**
	 * Retorna o número total de veículos atualmente reservados.
	 * 
	 * @return Total de veículos reservados
	 */
	long getTotalReserved();
	
	/**
	 * Retorna o número total de veículos em manutenção.
	 * 
	 * @return Total de veículos em manutenção
	 */
	long getTotalMaintenance();
	
	/**
	 * Retorna um mapa contendo a contagem de veículos disponíveis agrupados por ID da agência.
	 * 
	 * @return Um {@link Map} onde a chave é o ID da agência e o valor é a quantidade de veículos disponíveis
	 */
	Map<Long, Long> getAvailableByAgency();
}