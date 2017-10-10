
package tela.cadastro_passageiro;

import ambiente.Ambiente;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import rota.Local;
import tela.TipoController;
import tela.dados_voo.DadosVooController;
import voo.Voo;

public final class LinhaTabelaVoo {

    private final long codigo;
    private final String origem, destino;
    private final Label escala;
    private final Voo voo;
    private DadosVooController dadosVoo;

    public LinhaTabelaVoo(Ambiente ambienteExecucao, Voo voo) {
        this.codigo = voo.getCodigoVoo();
        this.origem = voo.getOrigem();
        this.destino = voo.getDestino();
        int totalEscala = voo.getRota().getEscala().getLocais().size();
        this.escala = new Label(totalEscala + (totalEscala > 0 ? " consultar" : ""));
        if (totalEscala > 0) {
            this.escala.setCursor(Cursor.HAND);
            escala.setStyle("-fx-text-fill: orange;");
            StringBuilder locais = new StringBuilder("\n ROTA DE " + origem + " A " + destino + "\n\n     ESCALAS: \n");

            voo.getRota()
                    .getEscala()
                    .getLocais()
                    .stream()
                    .map(Local::getNomeLocal)
                    .forEach(local -> locais.append("          ").append(local).append("\n"));

            escala.setOnMouseReleased(seClicar -> {
                ((DadosVooController) ambienteExecucao
                        .encontrarCenario(TipoController.DADOS_VOO)
                        .getController())
                        .popularFormulario(ambienteExecucao.getCenaAtual(), voo);
            });
        } else {
            escala.setStyle("-fx-text-fill: blue;");
        }
        this.voo = voo;
    }

    public long getCodigo() {
        return codigo;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public Label getEscala() {
        return escala;
    }

    public Voo getVoo() {
        return voo;
    }

}
