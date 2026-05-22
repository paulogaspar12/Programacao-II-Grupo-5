package app.service;

import java.util.ArrayList;
import java.util.List;

import app.model.Cliente;
import app.model.Veiculo;
import app.model.OrdemServico;

public class OficinaSistema {
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Veiculo> veiculos = new ArrayList<>();
    private final List<OrdemServico> ordens = new ArrayList<>();

    private int proxCliente = 1;
    private int proxVeiculo = 1;
    private int proxOrdem = 1;

    public Cliente cadastrarCliente(String nome, String telefone) {
        Cliente c = new Cliente(proxCliente++, nome, telefone);
        clientes.add(c);
        return c;
    }

    public Veiculo cadastrarVeiculo(String placa, String marca, String modelo, int ano, Cliente dono) {
        Veiculo v = new Veiculo(proxVeiculo++, placa, marca, modelo, ano, dono);
        veiculos.add(v);
        return v;
    }

    public OrdemServico cadastrarOrdemServico(Veiculo veiculo, String descricao) {
        OrdemServico o = new OrdemServico(proxOrdem++, veiculo, descricao);
        ordens.add(o);
        return o;
    }

    public List<Cliente> listarClientes() { return clientes; }
    public List<Veiculo> listarVeiculos() { return veiculos; }
    public List<OrdemServico> listarOrdens() { return ordens; }

    public Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) if (c.getId() == id) return c;
        return null;
    }

    public Veiculo buscarVeiculoPorId(int id) {
        for (Veiculo v : veiculos) if (v.getId() == id) return v;
        return null;
    }

    public OrdemServico buscarOrdemPorId(int id) {
        for (OrdemServico o : ordens) if (o.getId() == id) return o;
        return null;
    }

    public boolean alterarStatusOrdem(int id, String status) {
        OrdemServico o = buscarOrdemPorId(id);
        if (o == null) return false;
        o.setStatus(status);
        return true;
    }
}