/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficcinagestao.servico;

import oficcinagestao.modelo.Parts;
import oficcinagestao.util.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para Peças (adicional, não listado mas necessário).
 */
public class PartsService {
    private static final String FILE_NAME = "parts.txt";
    private List<Parts> parts = new ArrayList<>();
    private int nextId = 1;

    public PartsService() {
        loadFromFile();
        if (!parts.isEmpty()) {
            nextId = parts.stream().mapToInt(Parts::getId).max().orElse(0) + 1;
        }
    }

    public void loadFromFile() {
        parts.clear();
        List<String> lines = FileManager.readLines(FILE_NAME);
        for (String line : lines) {
            Parts p = Parts.fromString(line);
            if (p != null) {
                parts.add(p);
            }
        }
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        for (Parts p : parts) {
            lines.add(p.toString());
        }
        FileManager.writeLines(FILE_NAME, lines);
    }

    public boolean addPart(Parts part) {
        part.setId(nextId++);
        parts.add(part);
        saveToFile();
        return true;
    }

    public List<Parts> getAllParts() {
        return new ArrayList<>(parts);
    }

    // alterado editar peça a partir daqui
    public Parts findById(int id) {
        for (Parts p : parts) {
            if (p.getId() == id) return p;
        }
        return null;
    }
    public boolean updatePart(Parts newPart){

    Parts oldPart = findById(newPart.getId());

    if(oldPart == null){
        return false;
    }

    oldPart.setName(newPart.getName());
    oldPart.setPrice(newPart.getPrice());
    oldPart.setStock(newPart.getStock());

    saveToFile();

    return true;

}
    // procurar por nome
    public Parts findByName(String name){

    for(Parts part : parts){

        if(part.getName().equalsIgnoreCase(name)){
            return part;
        }

    }
    return null;
}
    public boolean removePart(int id) {
    Parts part = findById(id);

    if (part == null) {
        return false;
    }

    parts.remove(part);
    return true;
}
    
    public boolean updateStock(int id,int newStock){

    Parts part = findById(id);

    if(part == null){
        return false;
    }

    part.setStock(newStock);

    saveToFile();

    return true;

}
  
}
