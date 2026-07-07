/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.Appointment;
import oficcinagestao.modelo.Parts;

import java.util.List;

public class BillingService {

    private final PartsService partsService;

    public BillingService(PartsService partsService) {
        this.partsService = partsService;
    }

    public double calculateTotal(Appointment appointment) {
        double laborCost = 5000.0; // Mao de obra base
        double partsTotal = 0.0;

        // Somar valor das pecas usadas
        for (int partId : appointment.getUsedPartsIds()) {
            Parts part = partsService.findById(partId);
            if (part != null) {
                partsTotal += part.getPrice();
            }
        }

        return laborCost + partsTotal;
    }

    public void generateInvoice(Appointment appointment) {
        double total = calculateTotal(appointment);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  FATURA OFICINA");
        System.out.println("=".repeat(60));
        System.out.println("ID Agendamento : " + appointment.getId());
        System.out.println("Data           : " + appointment.getDate());
        System.out.println("Cliente ID     : " + appointment.getCustomerId());
        System.out.println("Descricao      : " + appointment.getServiceDescription());
        System.out.println("Mecanico ID    : " + appointment.getMechanicId());
        System.out.println("-".repeat(50));

        if (!appointment.getUsedPartsIds().isEmpty()) {
            System.out.println("PEÇAS UTILIZADAS:");
            for (int pid : appointment.getUsedPartsIds()) {
                Parts p = partsService.findById(pid);
                if (p != null) {
                    System.out.printf("  • %s → %.0f Kz%n", p.getName(), p.getPrice());
                }
            }
        } else {
            System.out.println("Nenhuma peca registrada.");
        }

        System.out.println("-".repeat(50));
        System.out.printf("Mao de Obra          :  5.000 Kz%n");
        System.out.printf("Total Pecas          : %7.0f Kz%n", 
                appointment.getUsedPartsIds().stream()
                    .mapToDouble(pid -> {
                        Parts p = partsService.findById(pid);
                        return p != null ? p.getPrice() : 0;
                    }).sum());
        System.out.printf("TOTAL A PAGAR        : %7.0f Kz%n", total);
        System.out.println("=".repeat(60));
    }
}