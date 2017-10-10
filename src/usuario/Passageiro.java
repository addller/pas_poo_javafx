package usuario;

import java.io.Serializable;
import voo.Voo;

public class Passageiro implements Serializable {

    public static final long MIN_CODIGO = 1, MAX_CODIGO = 99999999999L;
    private static long controleCodigoCliente = 1000000;

    private final Voo voo;
    private final String nome, nomeLowerCase;
    private boolean emFilaEspera = true;
    private final long codigoCliente;

    public Passageiro(String nome, Voo voo) {
        this.voo = voo;
        this.nome = nome;
        this.nomeLowerCase = nome.toLowerCase();
        this.codigoCliente = controleCodigoCliente++;
    }

    public String getNome() {
        return nome;
    }

    public Voo.TipoOcorrenciaVoo cancelarReserva() {
        return Voo.cancelarReservaPassageiro(voo, this);
    }

    public Voo getVoo() {
        return voo;
    }

    public void setEmFilaEspera(boolean emFilaEspera) {
        this.emFilaEspera = emFilaEspera;
    }

    public boolean isEmFilaEspera() {
        return emFilaEspera;
    }

    public String getNomeLowerCase() {
        return nomeLowerCase;
    }
}
