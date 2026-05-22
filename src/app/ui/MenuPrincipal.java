package app.ui;

import java.util.Scanner;

import app.model.Cliente;
import app.model.Veiculo;
import app.model.OrdemServico;
import app.service.OficinaSistema;

public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);
    private final OficinaSistema sistema = new OficinaSistema();

    public void executar() {
        int opcao;

        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            if (opcao == 1) {
                cadastrarCliente();
            } else if (opcao == 2) {
                cadastrarVeiculo();
            } else if (opcao == 3) {
                abrirOrdem();
            } else if (opcao == 4) {
                listarTudo();
            } else if (opcao == 0) {
                System.out.println("Encerrando sistema...");
            } else {
                System.out.println("Opcao invalida.");
            }

            if (opcao != 0) {
                pausar();
            }
        } while (opcao != 0);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("==============================");
        System.out.println("   SISTEMA DE GESTAO DA OFICINA");
        System.out.println("==============================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar veiculo");
        System.out.println("3 - Abrir ordem de servico");
        System.out.println("4 - Listar dados");
        System.out.println("0 - Sair");
        System.out.println();
    }

    private void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();

        Cliente c = sistema.cadastrarCliente(nome, telefone);
        System.out.println("-> Cliente criado: " + c);
    }

    private void cadastrarVeiculo() {
        if (sistema.listarClientes().isEmpty()) {
            System.out.println("Cadastre um cliente antes.");
            return;
        }

        System.out.print("Placa: ");
        String placa = scanner.nextLine().trim();
        System.out.print("Marca: ");
        String marca = scanner.nextLine().trim();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();
        System.out.print("Ano: ");
        int ano = lerInteiro("");

        System.out.println("Clientes:");
        for (Cliente cl : sistema.listarClientes()) {
            System.out.println(cl);
        }
        System.out.print("ID do dono: ");
        int id = lerInteiro("");
        Cliente dono = sistema.buscarClientePorId(id);
        if (dono == null) {
            System.out.println("Cliente nao encontrado.");
            return;
        }

        Veiculo v = sistema.cadastrarVeiculo(placa, marca, modelo, ano, dono);
        System.out.println("-> Veiculo criado: " + v);
    }

    private void abrirOrdem() {
        if (sistema.listarVeiculos().isEmpty()) {
            System.out.println("Cadastre um veiculo antes.");
            return;
        }

        System.out.println("Veiculos:");
        for (Veiculo v : sistema.listarVeiculos()) {
            System.out.println(v);
        }
        System.out.print("ID do veiculo: ");
        int id = lerInteiro("");
        Veiculo veic = sistema.buscarVeiculoPorId(id);
        if (veic == null) {
            System.out.println("Veiculo nao encontrado.");
            return;
        }

        System.out.print("Descricao: ");
        String desc = scanner.nextLine().trim();
        OrdemServico o = sistema.cadastrarOrdemServico(veic, desc);
        System.out.println("-> Ordem criada: " + o);
    }

    private void listarTudo() {
        System.out.println("Clientes:");
        for (Cliente c : sistema.listarClientes()) System.out.println(c);
        System.out.println("\nVeiculos:");
        for (Veiculo v : sistema.listarVeiculos()) System.out.println(v);
        System.out.println("\nOrdens:");
        for (OrdemServico o : sistema.listarOrdens()) System.out.println(o);
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            if (!mensagem.isEmpty()) System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido.");
            }
        }
    }

    private void pausar() {
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}