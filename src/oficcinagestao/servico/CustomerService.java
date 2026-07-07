/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.Customer;
import oficcinagestao.modelo.User;
import oficcinagestao.modelo.UserType;
import oficcinagestao.util.FileManager;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static final String FILE_NAME = "customers.txt";
    private List<Customer> customers = new ArrayList<>();
    private int nextId = 1;

    public CustomerService() {
        loadFromFile();
        if (!customers.isEmpty()) {
            nextId = customers.stream().mapToInt(Customer::getId).max().orElse(0) + 1;
        }
    }

    public void loadFromFile() {
        customers.clear();
        List<String> lines = FileManager.readLines(FILE_NAME);
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getUserType() == UserType.CLIENTE) {
                Customer customer = new Customer(user.getId(), user.getUsername(), 
                                               user.getPassword(), user.getName(), 
                                               user.getPhone(), user.getEmail());
                customers.add(customer);
            }
        }
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Customer c : customers) {
            lines.add(c.toString());
        }
        FileManager.writeLines(FILE_NAME, lines);
    }

    public boolean addCustomer(Customer customer) {
        customer.setId(nextId++);
        customers.add(customer);
        saveToFile();
        return true;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public Customer findById(int id) {
        for (Customer c : customers) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public Customer findByUsername(String username) {
        for (Customer c : customers) {
            if (c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == updatedCustomer.getId()) {
                customers.set(i, updatedCustomer);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteCustomer(int id) {
        boolean removed = customers.removeIf(c -> c.getId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
    }
}