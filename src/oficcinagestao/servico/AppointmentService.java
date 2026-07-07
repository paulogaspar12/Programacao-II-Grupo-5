/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.Appointment;
import oficcinagestao.util.FileManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para Agendamentos/Serviços.
 */
public class AppointmentService {
    private static final String FILE_NAME = "appointments.txt";
    private List<Appointment> appointments = new ArrayList<>();
    private int nextId = 1;

    public AppointmentService() {
        loadFromFile();
        if (!appointments.isEmpty()) {
            nextId = appointments.stream().mapToInt(Appointment::getId).max().orElse(0) + 1;
        }
    }

    public void loadFromFile() {
        appointments.clear();
        List<String> lines = FileManager.readLines(FILE_NAME);
        for (String line : lines) {
            Appointment a = Appointment.fromString(line);
            if (a != null) {
                appointments.add(a);
            }
        }
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Appointment a : appointments) {
            lines.add(a.toString());
        }
        FileManager.writeLines(FILE_NAME, lines);
    }

    public boolean addAppointment(Appointment appointment) {
        appointment.setId(nextId++);
        appointments.add(appointment);
        saveToFile();
        return true;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> getPendingAppointments() {
        List<Appointment> pending = new ArrayList<>();
        for (Appointment a : appointments) {
            if ("PENDENTE".equals(a.getStatus()) || "EM_ANDAMENTO".equals(a.getStatus())) {
                pending.add(a);
            }
        }
        return pending;
    }

    public boolean updateStatus(int id, String newStatus) {
        for (Appointment a : appointments) {
            if (a.getId() == id) {
                a.setStatus(newStatus);
                saveToFile();
                return true;
            }
        }
        return false;
    }
           public boolean assignMechanic(int appointmentId, int mechanicId) {
        for (Appointment a : appointments) {
            if (a.getId() == appointmentId) {
                a.setMechanicId(mechanicId);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public void addPartsToAppointment(int appointmentId, List<Integer> partIds) {
        for (Appointment a : appointments) {
            if (a.getId() == appointmentId) {
                for (int pid : partIds) {
                    a.addUsedPart(pid);
                }
                saveToFile();
                return;
            }
        }
    
       
}
    
}