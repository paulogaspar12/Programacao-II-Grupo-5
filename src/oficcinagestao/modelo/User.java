/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.modelo;

public class User {
    private int id;
    private String username;
    private String password; // Armazenada com hash simples
    private UserType userType;
    private String name;
    private String phone;
    private String email;

    // Construtor completo
    public User(int id, String username, String password, UserType userType, String name, String phone, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método para hash simples de senha (pode ser melhorado).
     */
    public static String hashPassword(String password) {
        return String.valueOf(password.hashCode());
    }

    @Override
    public String toString() {
        return id + ";" + username + ";" + password + ";" + userType + ";" + name + ";" + phone + ";" + email;
    }

    /**
     * Cria User a partir de linha do arquivo.
     */
    public static User fromString(String line) {
        String[] parts = line.split(";");
        if (parts.length != 7) {
            return null;
        }
        try {
            int id = Integer.parseInt(parts[0]);
            String username = parts[1];
            String password = parts[2];
            UserType userType = UserType.valueOf(parts[3]);
            String name = parts[4];
            String phone = parts[5];
            String email = parts[6];
            return new User(id, username, password, userType, name, phone, email);
        } catch (Exception e) {
            return null;
        }
    }
}
