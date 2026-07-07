/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.modelo;

public class Customer extends User {

    // Construtor
    public Customer(int id, String username, String password, String name, String phone, String email) {
        super(id, username, password, UserType.CLIENTE, name, phone, email);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
