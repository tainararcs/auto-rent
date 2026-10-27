package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import trcs.autorent.enums.RentalStatus;
import trcs.autorent.model.Rental;

/**
 * Interface remota para gerir o ciclo de vida e operações contratuais das locações.
 */
@Remote
public interface IRental extends Crud<Rental> {
	
	/**
	 * Retorna uma lista de locações filtradas pelo seu status atual.
	 * 
	 * @param status O status da locação ({@link RentalStatus})
	 * @return Lista de locações correspondentes ao status
	 */
    List<Rental> getByStatus(RentalStatus status);
    
    /**
     * Retorna todas as locações que se encontram ativas no momento.
     * 
     * @return Lista de locações ativas
     */
    List<Rental> getActiveRentals();
    
    /**
     * Retorna todas as locações associadas a um utilizador específico.
     * 
     * @param userId O ID do utilizador/cliente
     * @return Lista de locações do utilizador
     */
    List<Rental> getRentalsByUser(Long userId);
    
    /**
     * Retorna as locações com base na cidade da agência de retirada.
     * 
     * @param city O nome da cidade
     * @return Lista de locações na respetiva cidade
     */
	List<Rental> getRentalsByPickupCity(String city);
	
	/**
	 * Regista a retirada efetiva do veículo para uma determinada locação.
	 * 
	 * @param rentalId O ID da locação
	 * @return true se o registo for bem-sucedido, false caso contrário
	 */
	boolean registerPickup(Long rentalId);
	
	/**
	 * Regista a devolução efetiva do veículo para uma determinada locação.
	 * 
	 * @param rentalId O ID da locação
	 * @return true se o registo for bem-sucedido, false caso contrário
	 */
    boolean registerReturn(Long rentalId);
}