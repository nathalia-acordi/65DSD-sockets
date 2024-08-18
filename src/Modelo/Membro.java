package modelo;
import java.time.LocalDate;
import java.time.LocalTime;

public class Membro extends Pessoa {
    private LocalDate dataEntrada;
    private LocalTime horaEntrada;

    public Membro(String cpf, String nome, String endereco, LocalDate dataEntrada, LocalTime horaEntrada) {
        super(cpf, nome, endereco);
        this.dataEntrada = dataEntrada;
        this.horaEntrada = horaEntrada;
    }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalTime horaEntrada) { this.horaEntrada = horaEntrada; }
}
