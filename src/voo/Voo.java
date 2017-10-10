package voo;

import excessao.Excessao;
import ferramentas.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import rota.Rota;
import usuario.Passageiro;

public class Voo implements Serializable {
    
    private static int contadorDeVoos = 1000;
    private final int codigoVoo;
    private Rota rota;
    private final int numeroMaximoPassageiros;
    private final List<Passageiro> listaPassageiro = new ArrayList<>(),
            filaEsperaPassageiro = new ArrayList<>();
    private String origemToLower, destinoToLower;
    
    public enum TipoOcorrenciaVoo {
        PASSAGEIRO_INEXISTE,
        PASSAGEIRO_ADICIONADO,
        PASSAGERIO_REMOVIDO,
        EXCLUI_PASSAGEIRO_INCLUI_1_DA_FILA,
        CANCELA_RESERVA;
    }
    
    public Voo(int codigoVoo, Rota rota, int numeroMaximoPassageiros) {
        Excessao.seVariavelNull(new Object[]{rota}, "rota");
        Excessao.seArgumentoIlegal("O número de passageiros deve ser maior que 0", numeroMaximoPassageiros < 1);
        setRota(rota);
        this.numeroMaximoPassageiros = numeroMaximoPassageiros;
        this.codigoVoo = codigoVoo;
        contadorDeVoos++;
    }
    
    public int getCodigoVoo() {
        return codigoVoo;
    }
    
    public String getOrigem() {
        return rota.getOrigem().getNomeLocal();
    }
    
    public String getDestino() {
        return rota.getDestino().getNomeLocal();
    }
    
    public Rota getRota() {
        return rota;
    }
    
    public int getNumeroMaximoPassageiros() {
        return numeroMaximoPassageiros;
    }
    
    public boolean adicionarPassageiro(Passageiro passageiro) {
        
        Excessao.seVariavelNull(new Object[]{passageiro}, "passageiro");
        if (listaPassageiro.size() < numeroMaximoPassageiros) {
            passageiro.setEmFilaEspera(false);
            return listaPassageiro.add(passageiro);
        } else {
            adicionarNaFila(passageiro);
            return false;
        }
    }
    
    public TipoOcorrenciaVoo cancelarReservaPassageiro(Passageiro passageiro) {
        excessaoVooPassageiro(passageiro);
        
        if (!contemPassageiro(passageiro)) {
            return TipoOcorrenciaVoo.PASSAGEIRO_INEXISTE;
        }
        
        if (passageiro.isEmFilaEspera()) {
            cancelarReserva(passageiro);
            return TipoOcorrenciaVoo.CANCELA_RESERVA;
            
        } else {
            removerPassageiro(passageiro);
            if (!filaEsperaPassageiro.isEmpty()) {
                Passageiro novoPassageiro = filaEsperaPassageiro.remove(0);
                adicionarPassageiro(novoPassageiro);
                return TipoOcorrenciaVoo.EXCLUI_PASSAGEIRO_INCLUI_1_DA_FILA;
            }
            
            return TipoOcorrenciaVoo.PASSAGERIO_REMOVIDO;
        }
    }
    
    private Passageiro cancelarReserva(Passageiro passageiro) {
        filaEsperaPassageiro.remove(passageiro);
        return passageiro;
    }
    
    private boolean adicionarNaFila(Passageiro passageiro) {
        return filaEsperaPassageiro.add(passageiro);
    }
    
    private boolean removerPassageiro(Passageiro passageiro) {
        return listaPassageiro.remove(passageiro);
    }
    
    public boolean contemPassageiro(Passageiro passageiro) {
        excessaoVooPassageiro(passageiro);
        return filaEsperaPassageiro.contains(passageiro) || listaPassageiro.contains(passageiro);
    }
    
    private static void excessaoVooPassageiro(Passageiro passageiro) {
        Excessao.seVariavelNull(new Object[]{passageiro}, "passageiro");
    }
    
    public List<Passageiro> listarPassageiros() {
        return listaPassageiro;
    }
    
    public List<Passageiro> listarFilaEspera() {
        return filaEsperaPassageiro;
    }
    
    public static int getContadorDeVoos() {
        return contadorDeVoos;
    }
    
    @Override
    public int hashCode() {
        return 59 * 3 + this.codigoVoo;
    }
    
    @Override
    public boolean equals(Object outroVoo) {
        if (this == outroVoo) {
            return true;
        }
        if (outroVoo == null) {
            return false;
        }
        if (getClass() != outroVoo.getClass()) {
            return false;
        }
        return this.codigoVoo == ((Voo) outroVoo).codigoVoo;
    }
    
    public String getDestinoToLower() {
        return destinoToLower;
    }
    
    public String getOrigemToLower() {
        return origemToLower;
    }
    
    public final void setRota(Rota rota) {
        this.rota = rota;
        this.origemToLower = rota.getOrigem().getNomeLocal().toLowerCase();
        this.destinoToLower = rota.getDestino().getNomeLocal().toLowerCase();
    }
}
