/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Classe que representa um Agendamento/Serviço.
 */
package oficcinagestao.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Appointment {
    private int id;
    private LocalDate date;
    private int customerId;
    private String vehiclePlate;
    private String serviceDescription;
    private String status; // PENDENTE, EM_ANDAMENTO, CONCLUIDO, CANCELADO
    private int mechanicId; 
    private List<Integer> usedPartsIds; // IDs das pecas usadas

    public Appointment(int id, LocalDate date, int customerId, String vehiclePlate, 
                       String serviceDescription, String status, int mechanicId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
        this.vehiclePlate = vehiclePlate;
        this.serviceDescription = serviceDescription;
        this.status = status;
        this.mechanicId = mechanicId;
        this.usedPartsIds = new ArrayList<>();
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }

    public String getServiceDescription() { return serviceDescription; }
    public void setServiceDescription(String serviceDescription) { this.serviceDescription = serviceDescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getMechanicId() { return mechanicId; }
    public void setMechanicId(int mechanicId) { this.mechanicId = mechanicId; }

    public List<Integer> getUsedPartsIds() { return usedPartsIds; }

    public void addUsedPart(int partId) {
        usedPartsIds.add(partId);
    }

    @Override
    public String toString() {
        return id + ";" + date + ";" + customerId + ";" + vehiclePlate + ";" 
               + serviceDescription + ";" + status + ";" + mechanicId;
    }

    public static Appointment fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length != 7) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            LocalDate date = LocalDate.parse(parts[1]);
            int customerId = Integer.parseInt(parts[2]);
            String vehiclePlate = parts[3];
            String serviceDescription = parts[4];
            String status = parts[5];
            int mechanicId = Integer.parseInt(parts[6]);
            return new Appointment(id, date, customerId, vehiclePlate, serviceDescription, status, mechanicId);
        } catch (Exception e) {
            return null;
        }
    }
}