package app.model;

public class Veiculo {
    private final int id;
    private final String placa;
    private final String marca;
    private final String modelo;
    private final int ano;
    private final Cliente dono;

    public Veiculo(int id, String placa, String marca, String modelo, int ano, Cliente dono) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.dono = dono;
    }

    public int getId() { return id; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public Cliente getDono() { return dono; }

    @Override
    public String toString() {
        return "["+id+"] " + placa + " - " + marca + " " + modelo + " (" + ano + ") - " + dono.getNome();
    }
}