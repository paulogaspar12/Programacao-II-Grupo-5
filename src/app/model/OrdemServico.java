package app.model;

import java.time.LocalDateTime;

public class OrdemServico {
    private final int id;
    private final Veiculo veiculo;
    private final String descricao;
    private final String dataAbertura;
    private String status;

    public OrdemServico(int id, Veiculo veiculo, String descricao) {
        this.id = id;
        this.veiculo = veiculo;
        this.descricao = descricao;
        this.dataAbertura = LocalDateTime.now().toString();
        this.status = "ABERTA";
    }

    public int getId() { return id; }
    public Veiculo getVeiculo() { return veiculo; }
    public String getDescricao() { return descricao; }
    public String getDataAbertura() { return dataAbertura; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "["+id+"] " + veiculo.getPlaca() + " - " + veiculo.getDono().getNome() + " - " + status + " - " + descricao;
    }
}