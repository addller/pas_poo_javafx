package rota;

import excessao.Excessao;
import java.io.Serializable;

public class Rota implements Serializable{

    private Local origem, destino;
    private Escala escala;

    public Rota(Local origem, Local destino, Escala escala) {
        setOrigem(origem);
        setDestino(destino);
        setEscala(escala);
    }

    private void setOrigem(Local origem) {
        Excessao.variavelNull(origem, "origem");
        this.origem = origem;
    }

    private void setDestino(Local destino) {
        Excessao.variavelNull(destino, "destino");
        this.destino = destino;
    }

    private void setEscala(Escala escala) {
        Excessao.variavelNull(escala, "escala");
        this.escala = escala;
    }
    
    public Local getOrigem() {
        return origem;
    }

    public Local getDestino() {
        return destino;
    }

    public Escala getEscala() {
        return escala;
    }
}
