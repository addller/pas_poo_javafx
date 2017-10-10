package tela;

import ambiente.Ambiente;
import javafx.fxml.Initializable;

public abstract class Controller implements Initializable {

    protected Ambiente ambienteExecucao;

    protected void setAmbienteExecucao(Ambiente ambienteExecucao) {
        this.ambienteExecucao = ambienteExecucao;
    }

    protected Controller findController(TipoController tipoController) {
        return ambienteExecucao.encontrarCenario(tipoController).getController();
    }

    public abstract boolean sair();
}
