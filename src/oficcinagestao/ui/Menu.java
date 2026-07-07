/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Classe responsável pela interface de menus no console.
 * Versão completa com CRUD de Clientes e Veículos.
 */
package oficcinagestao.ui;

import oficcinagestao.modelo.*;
import oficcinagestao.servico.*;
import oficcinagestao.util.Utils;
import oficcinagestao.servico.PartsService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private User currentUser;
    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final PartsService partsService;
    private final AppointmentService appointmentService;

    public  Menu() {
        this.scanner = new Scanner(System.in);
        this.usuarioService = new UsuarioService();
        this.authService = new AuthService(usuarioService);
        this.customerService = new CustomerService();
        this.vehicleService = new VehicleService();
        this.partsService = new PartsService();
        this.appointmentService = new AppointmentService();
        usuarioService.initDefaultUsers();
    }

      public void start() {
        while (true) {
            System.out.println("\n=== SISTEMA DE GESTAO DE OFICINA MECANICA ===");
            System.out.println("1. Login");
            System.out.println("2. Criar Conta de Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String choice = scanner.nextLine().trim();

            if ("1".equals(choice)) {
                login();
            } else if ("2".equals(choice)) {
                createClientAccount();
            } else if ("0".equals(choice)) {
                System.out.println("Saindo do sistema...");
                break;
            } else {
                System.out.println("Opcao invalida!");
            }
        }
    }

     private void createClientAccount() {
    System.out.println("\n=== CRIAR CONTA DE CLIENTE ===");
    System.out.print("Username: ");
    String username = scanner.nextLine().trim();
    if (username.length() < 3) {
        System.out.println("Username deve ter no minimo 3 caracteres!");
        return;
    }
    if (usuarioService.findByUsername(username) != null) {
        System.out.println("Username ja existe!");
        return;
    }

    System.out.print("Senha (min 4 caracteres): ");
    String password = scanner.nextLine().trim();
    if (password.length() < 4) {
        System.out.println("Senha deve ter no minimo 4 caracteres!");
        return;
    }

    System.out.print("Nome completo: ");
    String name = scanner.nextLine().trim();
    if (name.isEmpty()) {
        System.out.println("Nome nao pode estar vazio!");
        return;
    }

    System.out.print("Telefone (9 digitos): ");
    String phone = scanner.nextLine().trim();
    if (!phone.matches("\\d{9}")) {
        System.out.println("Telefone deve ter 9 digitos numericos!");
        return;
    }

    System.out.print("Email: ");
    String email = scanner.nextLine().trim();
    if (!email.contains("@")) {
        System.out.println("Email invalido!");
        return;
    }

    String hashed = User.hashPassword(password);
    Customer newClient = new Customer(0, username, hashed, name, phone, email);
    User newUser = new User(0, username, hashed, UserType.CLIENTE, name, phone, email);

    if (usuarioService.addUser(newUser) && customerService.addCustomer(newClient)) {
        System.out.println("Conta criada com sucesso! Pode fazer login.");
    } else {
        System.out.println("Erro ao criar conta.");
    }
}
    private void login() {
        System.out.printf("Username no formato:\nPara administrador-admin\nPara cliente-username.cliente\nPara mecanico-username.mecanico \n Username:");
        String username = scanner.nextLine().trim();
        System.out.print("Senha: ");
        String password = scanner.nextLine().trim();

        currentUser = authService.login(username, password);
        if (currentUser != null) {
            System.out.println("Login bem sucedido! Bem-vindo, " + currentUser.getName());
            showMainMenu();
        } else {
            System.out.println("Credenciais invalidas!");
        }
    }

    private void showMainMenu() {
        boolean running = true;
        while (running) {
            if (currentUser.getUserType() == UserType.ADMIN) {
                adminMenu();
            } else if (currentUser.getUserType() == UserType.MECANICO) {
                mechanicMenu();
            } else if (currentUser.getUserType() == UserType.CLIENTE) {
                clientMenu();
            }

            System.out.println("\n0. Logout");
            System.out.print("Escolha: ");
            String choice = scanner.nextLine().trim();
            if ("0".equals(choice)) {
                System.out.println("Logout efetuado.");
                running = false;
                currentUser = null;
            }
        }
    }

    // ==================== MENU ADMIN ====================
    private void adminMenu() {
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Gerenciar Utilizadores");
        System.out.println("2. Gerenciar Clientes");
        System.out.println("3. Gerenciar Veiculos");
        System.out.println("4. Gerenciar Pecas");
        System.out.println("5. Ver Todos Agendamentos");
        System.out.println("6. Gerar Fatura");
        System.out.println("0. Voltar");

        System.out.print("Escolha: ");
        String op = scanner.nextLine().trim();

        switch (op) {
            case "1": manageUsers(); break;
            case "2": manageCustomers(); break;
            case "3": manageVehicles(); break;
            case "4": manageParts(); break;
            case "5": viewAllAppointments(); break;
            case "6": generateInvoice(); break;
            case "0": return;
            default: System.out.println("Opcao invalida!");
        }
    }

    private void manageUsers() {
        // (código original mantido - não alterado)
        int opcao;
        do {
            System.out.println("\n======Gerir usuarios=====");
            System.out.println("1. Criar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Procurar usuario");
            System.out.println("4. Atualizar usuario");
            System.out.println("5. Remover usuario");
            System.out.println("0. Sair");

            System.out.print("Opcao: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 : criarUsuario();break;
                case 2 : listarUsuarios();break;
                case 3 : procurarUsuario();break;
                case 4 : atualizarUsuario();break;
                case 5 : removerUsuario(); break;
                case 0 : System.out.println("Saindo..."); break;
                default : System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }
   
    private void manageCustomers() {
        int opcao;
        do {
            System.out.println("\n====== GERIR CLIENTES ======");
            System.out.println("1. Criar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Procurar cliente");
            System.out.println("4. Atualizar cliente");
            System.out.println("5. Remover cliente");
            System.out.println("0. Sair");

            System.out.print("Opcao: ");
            opcao = Integer.parseInt(scanner.nextLine().trim());

            switch (opcao) {
                case 1: criarCliente(); break;
                case 2: listarClientes(); break;
                case 3: procurarCliente(); break;
                case 4: atualizarCliente(); break;
                case 5: removerCliente(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private void manageVehicles() {
        int opcao;
        do {
            System.out.println("\n====== GERIR VEICULOS ======");
            System.out.println("1. Adicionar veiculo");
            System.out.println("2. Listar veiculos");
            System.out.println("3. Listar veiculos por proprietario");
            System.out.println("4. Remover veiculo");
            System.out.println("0. Sair");

            System.out.print("Opcao: ");
            opcao = Integer.parseInt(scanner.nextLine().trim());

            switch (opcao) {
                case 1: adicionarVeiculo(); break;
                case 2: listarVeiculos(); break;
                case 3: listarVeiculosPorProprietario(); break;
                case 4: removerVeiculo(); break;
                case 0: System.out.println("Saindo..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    // (o resto do código de manageParts, viewAllAppointments, mechanicMenu, clientMenu permanece o mesmo)

    private void manageParts(){
        // ... (código original mantido)
        int opcao;
        do {
            System.out.println("\n=================================");
            System.out.println("      GESTAO DE PECAS");
            System.out.println("=================================");
            System.out.println("1 - Adicionar peca");
            System.out.println("2 - Listar pecas");
            System.out.println("3 - Procurar peca por ID");
            System.out.println("4 - Editar peca");
            System.out.println("5 - Atualizar stock");
            System.out.println("6 - Remover peca");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");

            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1: adicionarPeca(); break;
                case 2: listarPecas(); break;
                case 3: procurarPeca(); break;
                case 4: editarPeca(); break;
                case 5: atualizarStock(); break;
                case 6: removerPeca(); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private void viewAllAppointments() {
        System.out.println("\n=== TODOS AGENDAMENTOS ===");
        List<Appointment> aps = appointmentService.getAllAppointments();
        if (aps.isEmpty()) {
            System.out.println("Nenhum agendamento encontrado.");
        } else {
            for (Appointment a : aps) {
                System.out.println(a);
            }
        }
    }

    // ==================== MENU MECANICO ====================
            private void mechanicMenu() {
        System.out.println("\n=== MENU MECANICO ===");
        System.out.println("1. Ver Trabalhos Pendentes");
        System.out.println("2. Marcar Servico como Concluido + Registrar Pecas");
        System.out.println("3. Historico Completo");
        System.out.println("0. Voltar");

        System.out.print("Escolha: ");
        String op = scanner.nextLine().trim();

        switch (op) {
            case "1":
                List<Appointment> pend = appointmentService.getPendingAppointments();
                if (pend.isEmpty()) {
                    System.out.println("Nenhum servico pendente.");
                } else {
                    for (Appointment a : pend) {
                        System.out.println(a.getId() + " | " + a.getServiceDescription() + " | Cliente: " + a.getCustomerId());
                    }
                }
                break;

            case "2":
                System.out.print("ID do agendamento: ");
                int id = Integer.parseInt(scanner.nextLine().trim());

                // Atribuir mecanico
                appointmentService.assignMechanic(id, currentUser.getId());

                // Registrar pecas usadas
                System.out.println("Digite os IDs das pecas usadas (separados por virgula, ex: 1,2): ");
                String input = scanner.nextLine().trim();
                List<Integer> partIds = new ArrayList<>();
                try {
                    for (String s : input.split(",")) {
                        partIds.add(Integer.parseInt(s.trim()));
                    }
                    appointmentService.addPartsToAppointment(id, partIds);
                } catch (Exception e) {
                    System.out.println("Erro ao registrar pecas.");
                }

                // Marcar como concluido
                if (appointmentService.updateStatus(id, "CONCLUIDO")) {
                    System.out.println("Servico concluido e pecas registradas com sucesso!");
                }
                break;

            case "3":
                appointmentService.getAllAppointments().forEach(System.out::println);
                break;

            case "0":
                return;
            default:
                System.out.println("Opcao invalida!");
        }
    }
    // ==================== MENU CLIENTE ====================
    private void clientMenu() {
    System.out.println("\n=== MENU CLIENTE ===");
    System.out.println("1. Atualizar Dados Pessoais");
    System.out.println("2. Ver Meus Servicos");
    System.out.println("3. Marcar Agendamento");
    System.out.println("4. Ver Meus Veiculos");
    System.out.println("5. Registrar Novo Veiculo");   // Nova opção
    System.out.println("6. Ver Pecas Disponiveis");
    System.out.println("0. Voltar");

    System.out.print("Escolha: ");
    String op = scanner.nextLine().trim();

    switch (op) {
        case "1": updatePersonalData(); break;
        case "2": viewMyServices(); break;
        case "3": markAppointment(); break;
        case "4": viewMyVehicles(); break;
        case "5": registerMyVehicle(); break;     // Nova chamada
        case "6": viewAvailableParts(); break;
        case "0": return;
        default: System.out.println("Opcao invalida!");
    }
}

    private void viewMyServices() {
        System.out.println("\n=== MEUS SERVICOS ===");
        List<Appointment> myServices = appointmentService.getAllAppointments().stream()
            .filter(a -> a.getCustomerId() == currentUser.getId())
            .toList();

        if (myServices.isEmpty()) {
            System.out.println("Voce ainda nao tem servicos.");
        } else {
            for (Appointment a : myServices) {
                System.out.println(a);
            }
        }
    }

    private void viewMyVehicles() {
    System.out.println("\n=== MEUS VEICULOS ===");
    List<Vehicle> myVehicles = vehicleService.getVehiclesByOwner(currentUser.getId());

    if (myVehicles.isEmpty()) {
        System.out.println("Voce ainda nao tem veiculos cadastrados.");
        return;
    }

    System.out.println("Matricula     | Marca          | Modelo           | Ano");
    System.out.println("-------------------------------------------------------");

    for (Vehicle v : myVehicles) {
        System.out.printf("%-12s | %-15s | %-15s | %d%n", 
            v.getPlate(),
            v.getBrand(),
            v.getModel(),
            v.getYear());
    }
}
    private void updatePersonalData() {
        if (currentUser.getUserType() != UserType.CLIENTE) {
            System.out.println("Apenas clientes podem atualizar dados pessoais aqui.");
            return;
        }
        System.out.println("Atualizar Dados Pessoais");
        System.out.print("Novo Nome: ");
        String name = scanner.nextLine().trim();
        System.out.print("Novo Telefone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Novo Email: ");
        String email = scanner.nextLine().trim();

        Customer cliente = customerService.findById(currentUser.getId());
        if (cliente != null) {
            cliente.setName(name);
            cliente.setPhone(phone);
            cliente.setEmail(email);
            customerService.updateCustomer(cliente);
            currentUser = cliente;
            System.out.println("Dados atualizados com sucesso!");
        } else {
            System.out.println("Erro ao encontrar cliente.");
        }
    }

    private void viewHistory() {
        System.out.println("\n=== SEU HISTORICO ===");
        List<Appointment> history = appointmentService.getAllAppointments();
        if (history.isEmpty()) {
            System.out.println("Nenhum servico encontrado.");
        } else {
            for (Appointment a : history) {
                System.out.println(a);
            }
        }
    }

  private void markAppointment() {
    System.out.println("\n=== MARCAR AGENDAMENTO ===");
    System.out.print("Descricao do Servico: ");
    String description = scanner.nextLine().trim();
    if (description.isEmpty()) {
        System.out.println("Descricao nao pode estar vazia!");
        return;
    }

    System.out.print("Data (yyyy-MM-dd): ");
    String dateStr = scanner.nextLine().trim();
    LocalDate date;
    try {
        date = LocalDate.parse(dateStr);
        if (date.isBefore(LocalDate.now())) {
            System.out.println("Data nao pode ser no passado!");
            return;
        }
    } catch (Exception e) {
        System.out.println("Formato de data invalido! Use yyyy-MM-dd");
        return;
    }

    System.out.print("Matricula do Veiculo (ex: AA-00-00): ");
    String vehiclePlate = scanner.nextLine().trim();
    if (vehiclePlate.isEmpty() || !isValidPlate(vehiclePlate)) {
        System.out.println("Placa invalida! Use formato AA-00-00 ou AA0000");
        return;
    }

    Appointment ap = new Appointment(0, date, currentUser.getId(), vehiclePlate, 
                                   description, "PENDENTE", 0);

    if (appointmentService.addAppointment(ap)) {
        System.out.println("Agendamento realizado com sucesso!");
    } else {
        System.out.println("Erro ao salvar agendamento.");
    }
}

    private void viewAvailableParts() {
        System.out.println("\n=== PEÇAS DISPONIVEIS ===");
        List<Parts> parts = partsService.getAllParts();
        if (parts.isEmpty()) {
            System.out.println("Nenhuma peca disponivel.");
        } else {
            for (Parts p : parts) {
                System.out.println(p);
            }
        }
    }

    // ==================== MÉTODOS DE PEÇAS (mantidos) ====================
   private void adicionarPeca() {
    System.out.println("\n--- ADICIONAR PECA ---");
    System.out.print("Nome: ");
    String nome = scanner.nextLine().trim();
    if (nome.isEmpty()) {
        System.out.println("Nome nao pode estar vazio!");
        return;
    }

    System.out.print("Preco: ");
    double preco;
    try {
        preco = Double.parseDouble(scanner.nextLine().trim());
        if (preco <= 0) {
            System.out.println("Preco deve ser maior que zero!");
            return;
        }
    } catch (Exception e) {
        System.out.println("Preco invalido!");
        return;
    }

    System.out.print("Quantidade em stock: ");
    int stock;
    try {
        stock = Integer.parseInt(scanner.nextLine().trim());
        if (stock < 0) {
            System.out.println("Stock nao pode ser negativo!");
            return;
        }
    } catch (Exception e) {
        System.out.println("Stock invalido!");
        return;
    }

    Parts part = new Parts(0, nome, preco, stock);
    if (partsService.addPart(part)) {
        System.out.println("Peca adicionada com sucesso.");
    }
}
       private void listarPecas() {
        System.out.println("\n======= LISTA DE PECAS =======");
        List<Parts> lista = partsService.getAllParts();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma peca cadastrada.");
            return;
        }

        for (Parts p : lista) {
            System.out.println("----------------------------");
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getName());
            System.out.println("Preco: " + p.getPrice() + " Kzs");
            System.out.println("Stock: " + p.getStock());
        }
    }

    private void procurarPeca() {
        System.out.print("Digite o ID da peca: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Parts p = partsService.findById(id);

        if (p == null) {
            System.out.println("Peca nao encontrada.");
            return;
        }

        System.out.println("-------------------------");
        System.out.println("ID: " + p.getId());
        System.out.println("Nome: " + p.getName());
        System.out.println("Preco: " + p.getPrice() + " Kzs");
        System.out.println("Stock: " + p.getStock());
    }

    private void editarPeca() {
        System.out.println("\n===== EDITAR PECA =====");
        System.out.print("Digite o ID da peca: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Parts part = partsService.findById(id);
        if (part == null) {
            System.out.println("Peca nao encontrada.");
            return;
        }

        System.out.println("Dados atuais:");
        System.out.println("Nome: " + part.getName());
        System.out.println("Preco: " + part.getPrice());
        System.out.println("Stock: " + part.getStock());

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Novo preco: ");
        double preco = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Novo stock: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());

        Parts novaPeca = new Parts(id, nome, preco, stock);

        if (partsService.updatePart(novaPeca)) {
            System.out.println("Peca atualizada com sucesso.");
        } else {
            System.out.println("Erro ao atualizar peca.");
        }
    }

    private void atualizarStock() {
        System.out.println("\n===== ATUALIZAR STOCK =====");
        System.out.print("Digite o ID da peca: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Parts part = partsService.findById(id);
        if (part == null) {
            System.out.println("Peca nao encontrada.");
            return;
        }

        System.out.println("Stock atual: " + part.getStock());
        System.out.print("Novo stock: ");
        int stock = Integer.parseInt(scanner.nextLine().trim());

        if (partsService.updateStock(id, stock)) {
            System.out.println("Stock atualizado com sucesso.");
        } else {
            System.out.println("Erro ao atualizar stock.");
        }
    }

    private void removerPeca() {
        System.out.println("\n===== REMOVER PECA =====");
        System.out.print("Digite o ID da peca: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        Parts part = partsService.findById(id);
        if (part == null) {
            System.out.println("Peca nao encontrada.");
            return;
        }

        System.out.println("Peca encontrada:");
        System.out.println("Nome: " + part.getName());

        System.out.print("Deseja realmente remover? (S/N): ");
        String resposta = scanner.nextLine().trim();

        if (resposta.equalsIgnoreCase("S")) {
            if (partsService.removePart(id)) {
                System.out.println("Peca removida com sucesso.");
            } else {
                System.out.println("Erro ao remover peca.");
            }
        } else {
            System.out.println("Operacao cancelada.");
        }
    }

    // ==================== MÉTODOS DE USUÁRIOS (mantidos) ====================
  private void criarUsuario() {
    System.out.println("\n===== CRIAR USUARIO =====");
    
    System.out.print("Username: ");
    String username = scanner.nextLine().trim();
    if (username.isEmpty() || username.length() < 3) {
        System.out.println("Username deve ter pelo menos 3 caracteres!");
        return;
    }
    if (usuarioService.findByUsername(username) != null) {
        System.out.println("Username ja existe!");
        return;
    }

    System.out.print("Password (min 4 caracteres): ");
    String password = scanner.nextLine().trim();
    if (password.length() < 4) {
        System.out.println("Senha deve ter no minimo 4 caracteres!");
        return;
    }

    System.out.print("Nome: ");
    String nome = scanner.nextLine().trim();
    if (nome.isEmpty()) {
        System.out.println("Nome nao pode estar vazio!");
        return;
    }

    System.out.print("Telefone: ");
    String telefone = scanner.nextLine().trim();
    if (!telefone.matches("\\d{9}")) {
        System.out.println("Telefone deve ter 9 digitos numericos!");
        return;
    }

    System.out.print("Email: ");
    String email = scanner.nextLine().trim();
    if (!email.contains("@") || !email.contains(".")) {
        System.out.println("Email invalido!");
        return;
    }

    System.out.print("Tipo (ADMIN/MECANICO/CLIENTE): ");
    String tipoStr = scanner.nextLine().trim().toUpperCase();
    UserType type;
    try {
        type = UserType.valueOf(tipoStr);
    } catch (Exception e) {
        System.out.println("Tipo invalido! Use ADMIN, MECANICO ou CLIENTE");
        return;
    }

    String hashed = User.hashPassword(password);
    User user = new User(0, username, hashed, type, nome, telefone, email);

    usuarioService.addUser(user);
    System.out.println("Usuario criado com sucesso!");
}
    private void listarUsuarios() {
    System.out.println("\n===== LISTA DE USUARIOS =====");
    for (User u : usuarioService.getAllUsers()) {
        System.out.println("========");
        System.out.println("ID: " + u.getId());
        System.out.println("Username: " + u.getUsername());
        System.out.println("Nome: " + u.getName());
        System.out.println("Tipo: " + u.getUserType());
        System.out.println("========");
    }
}
    private void atualizarUsuario() {
    System.out.print("Digite username: ");
    String username = scanner.nextLine();
    User user = usuarioService.findByUsername(username);

    if (user == null) {
        System.out.println("Usuario nao encontrado.");
        return;
    }

    System.out.print("Novo nome: ");
    user.setName(scanner.nextLine());
    System.out.print("Novo telefone: ");
    user.setPhone(scanner.nextLine());
    System.out.print("Novo email: ");
    user.setEmail(scanner.nextLine());

    usuarioService.updateUser(user);
    System.out.println("Usuario atualizado!");
}
    private void removerUsuario() {
    System.out.print("Digite ID do usuario: ");
    int id = Integer.parseInt(scanner.nextLine());

    User user = usuarioService.getAllUsers()
            .stream()
            .filter(u -> u.getId() == id)
            .findFirst()
            .orElse(null);

    if (user == null) {
        System.out.println("Usuario nao encontrado.");
        return;
    }

    if (user.getUserType() == UserType.ADMIN) {
        System.out.println("Nao pode remover um ADMIN!");
        return;
    }

    usuarioService.deleteUser(id);
    System.out.println("Usuario removido com sucesso!");
}

private void procurarUsuario() {

    System.out.print("Digite username: ");
    String username = scanner.nextLine();

    User user = usuarioService.findByUsername(username);

    if (user == null) {
        System.out.println("Usuario nao encontrado.");
        return;
    }

    System.out.println("\n===== USUARIO =====");
    System.out.println("ID: " + user.getId());
    System.out.println("Username: " + user.getUsername());
    System.out.println("Nome: " + user.getName());
    System.out.println("Contatos: " + user.getPhone() + " | " + user.getEmail());
    System.out.println("Tipo: " + user.getUserType());
}
    // ==================== NOVOS MÉTODOS DE CLIENTES E VEICULOS ====================
    private void criarCliente() {
        System.out.println("\n===== CRIAR CLIENTE =====");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        if (customerService.findByUsername(username) != null || usuarioService.findByUsername(username) != null) {
            System.out.println("Username ja existe!");
            return;
        }
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        String hashed = User.hashPassword(password);
        Customer cliente = new Customer(0, username, hashed, nome, telefone, email);

        if (customerService.addCustomer(cliente)) {
            System.out.println("Cliente criado com sucesso!");
        } else {
            System.out.println("Erro ao criar cliente.");
        }
    }

    private void listarClientes() {
        System.out.println("\n===== LISTA DE CLIENTES =====");
        List<Customer> lista = customerService.getAllCustomers();
        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Customer c : lista) {
            System.out.println("ID: " + c.getId() + " | " + c.getName() + " | " + c.getUsername());
        }
    }

    private void procurarCliente() {
        System.out.print("Digite ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Customer c = customerService.findById(id);
        if (c == null) {
            System.out.println("Cliente nao encontrado.");
            return;
        }
        System.out.println("ID: " + c.getId() + " | Nome: " + c.getName() + " | Email: " + c.getEmail());
    }

    private void atualizarCliente() {
        System.out.print("Digite ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Customer c = customerService.findById(id);
        if (c == null) {
            System.out.println("Cliente nao encontrado.");
            return;
        }
        System.out.print("Novo nome: ");
        c.setName(scanner.nextLine().trim());
        System.out.print("Novo telefone: ");
        c.setPhone(scanner.nextLine().trim());
        System.out.print("Novo email: ");
        c.setEmail(scanner.nextLine().trim());
        if (customerService.updateCustomer(c)) {
            System.out.println("Cliente atualizado com sucesso!");
        }
    }

    private void removerCliente() {
        System.out.print("Digite ID do cliente: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        if (customerService.deleteCustomer(id)) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente nao encontrado.");
        }
    }

  private void adicionarVeiculo() {
    System.out.println("\n===== ADICIONAR VEICULO =====");
    
    System.out.print("Matricula (placa ex: AA-00-00): ");
    String plate = scanner.nextLine().trim();
    
    if (plate.isEmpty()) {
        System.out.println("Placa nao pode estar vazia!");
        return;
    }
    
    if (!isValidPlate(plate)) {
        System.out.println("Formato de placa invalido! Use AA-00-00 ou AA0000");
        return;
    }

    System.out.print("Marca: ");
    String brand = scanner.nextLine().trim();
    if (brand.isEmpty()) {
        System.out.println("Marca nao pode estar vazia!");
        return;
    }

    System.out.print("Modelo: ");
    String model = scanner.nextLine().trim();
    if (model.isEmpty()) {
        System.out.println("Modelo nao pode estar vazio!");
        return;
    }

    System.out.print("Ano: ");
    int year;
    try {
        year = Integer.parseInt(scanner.nextLine().trim());
        if (year < 1950 || year > 2026) {
            System.out.println("Ano invalido! (1950-2026)");
            return;
        }
    } catch (Exception e) {
        System.out.println("Ano deve ser um numero!");
        return;
    }

    System.out.print("ID do proprietario: ");
    int ownerId;
    try {
        ownerId = Integer.parseInt(scanner.nextLine().trim());
    } catch (Exception e) {
        System.out.println("ID do proprietario deve ser um numero!");
        return;
    }

    Vehicle v = new Vehicle(plate, brand, model, year, ownerId);
    if (vehicleService.addVehicle(v)) {
        System.out.println("Veiculo adicionado com sucesso!");
    } else {
        System.out.println("Erro ao adicionar veiculo.");
    }
}
    private void listarVeiculos() {
    System.out.println("\n======= LISTA DE VEICULOS =======");
    List<Vehicle> lista = vehicleService.getAllVehicles();

    if (lista.isEmpty()) {
        System.out.println("Nenhum veiculo cadastrado.");
        return;
    }

    System.out.println("ID | Matricula     | Marca          | Modelo           | Ano  | Dono ID");
    System.out.println("--------------------------------------------------------------------");

    for (Vehicle v : lista) {
        System.out.printf("%-3d | %-12s | %-15s | %-15s | %-4d | %d%n", 
            // Como não temos ID no Vehicle, vamos usar hashCode ou índice
            lista.indexOf(v) + 1,
            v.getPlate(),
            v.getBrand(),
            v.getModel(),
            v.getYear(),
            v.getOwnerId());
    }
}
    private void listarVeiculosPorProprietario() {
        System.out.print("ID do proprietario: ");
        int ownerId = Integer.parseInt(scanner.nextLine().trim());
        List<Vehicle> lista = vehicleService.getVehiclesByOwner(ownerId);
        if (lista.isEmpty()) {
            System.out.println("Nenhum veiculo encontrado.");
            return;
        }
        for (Vehicle v : lista) {
            System.out.println(v.getPlate() + " - " + v.getBrand() + " " + v.getModel());
        }
    }

      private void generateInvoice() {
        System.out.print("ID do agendamento: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        List<Appointment> all = appointmentService.getAllAppointments();
        Appointment ap = all.stream()
                            .filter(a -> a.getId() == id)
                            .findFirst()
                            .orElse(null);

        if (ap == null) {
            System.out.println("Agendamento nao encontrado.");
            return;
        }

        BillingService billing = new BillingService(partsService);
        billing.generateInvoice(ap);
    }

   private void removerVeiculo() {
    System.out.println("\n===== REMOVER VEICULO =====");
    System.out.print("Digite a matricula (placa) do veiculo: ");
    String plate = scanner.nextLine().trim();

    // Precisamos adicionar um método findByPlate no VehicleService
    // Por enquanto, vamos fazer uma busca simples
    List<Vehicle> allVehicles = vehicleService.getAllVehicles();
    Vehicle vehicleToRemove = null;

    for (Vehicle v : allVehicles) {
        if (v.getPlate().equalsIgnoreCase(plate)) {
            vehicleToRemove = v;
            break;
        }
    }

    if (vehicleToRemove == null) {
        System.out.println("Veiculo com matricula " + plate + " nao encontrado.");
        return;
    }

    System.out.println("Veiculo encontrado:");
    System.out.println("Matricula: " + vehicleToRemove.getPlate());
    System.out.println("Marca: " + vehicleToRemove.getBrand());
    System.out.println("Modelo: " + vehicleToRemove.getModel());

    System.out.print("Deseja realmente remover este veiculo? (S/N): ");
    String resposta = scanner.nextLine().trim();

    if (resposta.equalsIgnoreCase("S")) {
        // Remover do service
        boolean removed = vehicleService.removeVehicle(vehicleToRemove.getPlate());
        if (removed) {
            System.out.println("Veiculo removido com sucesso!");
        } else {
            System.out.println("Erro ao remover veiculo.");
        }
    } else {
        System.out.println("Operacao cancelada.");
    }
}
   private void registerMyVehicle() {
    System.out.println("\n=== REGISTRAR NOVO VEICULO ====");
    System.out.print("Matricula (placa): ");
    String plate = scanner.nextLine().trim();
    System.out.print("Marca: ");
    String brand = scanner.nextLine().trim();
    System.out.print("Modelo: ");
    String model = scanner.nextLine().trim();
    System.out.print("Ano: ");
    int year = Integer.parseInt(scanner.nextLine().trim());

    Vehicle newVehicle = new Vehicle(plate, brand, model, year, currentUser.getId());

    if (vehicleService.addVehicle(newVehicle)) {
        System.out.println("Veiculo registrado com sucesso!");
    } else {
        System.out.println("Erro ao registrar veiculo.");
    }
}
   private boolean isValidPlate(String plate) {
    // Remove espaços e converte para maiúsculo
    plate = plate.trim().toUpperCase().replace("-", "");
    
    // Formatos aceitos: AA-00-00 ou AA0000
    return plate.matches("[A-Z]{2}\\d{4}") || 
           plate.matches("[A-Z]{2}-\\d{2}-\\d{2}");
}
     
}