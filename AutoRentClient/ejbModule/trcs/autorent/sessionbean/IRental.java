package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import trcs.autorent.enums.RentalStatus;
import trcs.autorent.model.Rental;

@Remote
public interface IRental extends Crud<Rental>{
    List<Rental> getByStatus(RentalStatus status);
    public List<Rental> getActiveRentals();
    public List<Rental> getRentalsByUser(Long userId);
	public List<Rental> getRentalsByPickupCity(String city);
	public boolean registerPickup(Long rentalId);
    public boolean registerReturn(Long rentalId);
}
