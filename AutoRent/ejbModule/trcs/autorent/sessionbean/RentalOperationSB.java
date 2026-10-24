package trcs.autorent.sessionbean;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import trcs.autorent.enums.RentalStatus;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Rental;
import trcs.autorent.model.User;
import trcs.autorent.model.Vehicle;

/**
 * Gerenciamento do Fluxo de Locação.
 * Gerencia o estado de uma operação de locação em andamento (passo a passo antes de confirmar).   
 */
@Stateful
public class RentalOperationSB {

    @PersistenceContext(unitName = "autorent")
    private EntityManager em;

    private User client;
    private Agency pickupAgency;
    private Agency returnAgency;
    private Vehicle vehicle;
    private LocalDate pickupDate;
    private LocalDate dueDate;

    public void setClient(User client) { this.client = client; }
    public User getClienteLogado() { return this.client; }

    public void selecionarAgenciaRetirada(Agency agency) { this.pickupAgency = agency; }
    public void selecionarAgenciaDevolucao(Agency agency) { this.returnAgency = agency; }
    public void selecionarVeiculo(Vehicle vehicle) { this.vehicle = vehicle; }
    
    public void definirPeriodo(LocalDate pickupDate, LocalDate dueDate) {
        this.pickupDate = pickupDate;
        this.dueDate = dueDate;
    }

    public double calcularValor() {
        if (vehicle == null || pickupDate == null || dueDate == null) return 0.0;
        long days = ChronoUnit.DAYS.between(pickupDate, dueDate);
        if (days <= 0) days = 1;
        return days * vehicle.getDailyRate();
    }

    public boolean confirmarLocacao() {
        // Valida se o veículo está disponível antes de confirmar
        Vehicle managedVehicle = em.find(Vehicle.class, vehicle.getId());
        if (managedVehicle == null || managedVehicle.getStatus() != VehicleStatus.AVAILABLE) {
            return false; // Veículo indisponível
        }

        // Atualiza status do veículo para alugado/reservado
        managedVehicle.setStatus(VehicleStatus.RENTED);
        em.merge(managedVehicle);

        // Cria a locação
        Rental rental = new Rental();
        rental.setClient(client);
        rental.setVehicle(managedVehicle);
        rental.setPickupAgency(pickupAgency);
        rental.setReturnAgency(returnAgency);
        rental.setPickupDate(pickupDate);
        rental.setDueDate(dueDate);
        rental.setStatus(RentalStatus.RESERVED);
        rental.setTotalValue(calcularValor());

        em.persist(rental);
        return true;
    }
}
