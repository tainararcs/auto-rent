package trcs.autorent.sessionbean;

import java.util.List;

import jakarta.ejb.Remote;
import trcs.autorent.enums.VehicleStatus;
import trcs.autorent.model.Agency;
import trcs.autorent.model.Vehicle;

@Remote
public interface IVehicle extends Crud<Vehicle> {
    List<Vehicle> getByAgency(Agency agency);
    List<Vehicle> getByCategory(String category);
    List<Vehicle> getByStatus(VehicleStatus status);
    public boolean updateStatus(Long vehicleId, VehicleStatus status);
}
