/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.Vehicle;
import oficcinagestao.util.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para Veículos.
 */
public class VehicleService {
    private static final String FILE_NAME = "vehicles.txt";
    private List<Vehicle> vehicles = new ArrayList<>();

    public VehicleService() {
        loadFromFile();
    }

    public void loadFromFile() {
        vehicles.clear();
        List<String> lines = FileManager.readLines(FILE_NAME);
        for (String line : lines) {
            Vehicle v = Vehicle.fromString(line);
            if (v != null) {
                vehicles.add(v);
            }
        }
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Vehicle v : vehicles) {
            lines.add(v.toString());
        }
        FileManager.writeLines(FILE_NAME, lines);
    }

    public boolean addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveToFile();
        return true;
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getVehiclesByOwner(int ownerId) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getOwnerId() == ownerId) {
                result.add(v);
            }
        }
        return result;
    }
    public boolean removeVehicle(String plate) {
    boolean removed = vehicles.removeIf(v -> v.getPlate().equalsIgnoreCase(plate));
    if (removed) {
        saveToFile();
    }
    return removed;
}
}
