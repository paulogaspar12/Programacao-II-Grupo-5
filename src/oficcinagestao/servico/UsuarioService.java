/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.User;
import oficcinagestao.modelo.UserType;
import oficcinagestao.util.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para gestão de Utilizadores.
 */
public class UsuarioService {
    private static final String FILE_NAME = "users.txt";
    private List<User> users = new ArrayList<>();
    private int nextId = 1;

    public UsuarioService() {
        loadFromFile();
        if (!users.isEmpty()) {
            nextId = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
        }
    }

    public void loadFromFile() {
        users.clear();
        List<String> lines = FileManager.readLines(FILE_NAME);
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null) {
                users.add(user);
            }
        }
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (User user : users) {
            lines.add(user.toString());
        }
        FileManager.writeLines(FILE_NAME, lines);
    }

    public boolean addUser(User user) {
        user.setId(nextId++);
        users.add(user);
        saveToFile();
        return true;
    }

    public User findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == updatedUser.getId()) {
                users.set(i, updatedUser);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(int id) {

    boolean removed = users.removeIf(u -> u.getId() == id);

    if (removed) {
        saveToFile();
    }

    return removed;
}

    // Inicializa dados padrão se não existirem
    public void initDefaultUsers() {
        if (users.isEmpty()) {
            String hashed = User.hashPassword("admin123");
            addUser(new User(0, "admin", hashed, UserType.ADMIN, "Administrador", "123456789", "admin@oficina.com"));
            // Adicionar mais se necessário
        }
    }
}
