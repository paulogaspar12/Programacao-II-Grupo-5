/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.modelo;

public class Vehicle {
    private String plate; // Placa
    private String brand;
    private String model;
    private int year;
    private int ownerId; // ID do dono (Customer)

    public Vehicle(String plate, String brand, String model, int year, int ownerId) {
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.ownerId = ownerId;
    }

    // Getters e Setters
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return plate + ";" + brand + ";" + model + ";" + year + ";" + ownerId;
    }

    public static Vehicle fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length != 5) return null;
        try {
            String plate = parts[0];
            String brand = parts[1];
            String model = parts[2];
            int year = Integer.parseInt(parts[3]);
            int ownerId = Integer.parseInt(parts[4]);
            return new Vehicle(plate, brand, model, year, ownerId);
        } catch (Exception e) {
            return null;
        }
    }
}
