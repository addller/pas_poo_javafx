
package tela;

import ambiente.Ambiente;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public final class Cenario {

    private Scene cena;
    private TipoController tipoCena;
    private FXMLLoader loader;
    private boolean resisable;

    /**
     * Observacao: a utilização deste construtor obriga que a classe controler,
     * e o arquivo fxml <strong>estejam no mesmo pacote</strong>
     * Ou seja está classe trabalha com um padrão
     * @param tipoCena enumeração para padronização
     * @param ambienteExecucao
     */
    public Cenario(TipoController tipoCena, Ambiente ambienteExecucao) {
        try {
            loader = new FXMLLoader(tipoCena.classe.getResource(tipoCena.nomeArquivoFxml));
            cena = new Scene(loader.load());
            this.tipoCena = tipoCena;
            getController().setAmbienteExecucao(ambienteExecucao);
        } catch (IOException ex) {
            Logger.getLogger(Cenario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Controller getController() {
        return loader.getController();
    }

    public TipoController getTipoCena() {
        return tipoCena;
    }

    public String getTitulo() {
        return tipoCena.titulo;
    }

    public Scene getCena() {
        return cena;
    }

    @Override
    public int hashCode() {
        return 43 * 5 + Objects.hashCode(this.tipoCena);
    }

    @Override
    public boolean equals(Object outroCenario) {
        if (this == outroCenario) {
            return true;
        }
        if (outroCenario == null) {
            return false;
        }
        if (getClass() != outroCenario.getClass()) {
            return false;
        }
        return this.tipoCena == ((Cenario) outroCenario).tipoCena;
    }

    public boolean isResisable() {
        return resisable;
    }

    public void setResisable(boolean resisable) {
        this.resisable = resisable;
    }

    
}
