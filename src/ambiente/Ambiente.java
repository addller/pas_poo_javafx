package ambiente;

import ferramentas.Serializar;
import tela.TipoController;
import tela.Cenario;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.stage.Stage;

public class Ambiente extends Application {

    private final Set<Cenario> CENARIOS = new HashSet();
    private Stage palco;
    private TipoController cenaAtual;

    @Override
    public void start(Stage primaryStage) {
        carregarCenarios();
        palco = primaryStage;
        atualizarScene(TipoController.INICIAL);
    }

    private void carregarCenarios() {
        CENARIOS.addAll(Arrays.asList(cenario(TipoController.INICIAL),
                cenario(TipoController.CADASTRO_PASSAGEIRO),
                cenario(TipoController.CADASTRO_VOO),
                cenario(TipoController.DADOS_VOO)
        ));
    }

    private Cenario cenario(TipoController tipoCena) {
        return new Cenario(tipoCena, this);
    }

    public Cenario encontrarCenario(TipoController tipoControler) {
        return CENARIOS.stream()
                .filter(cenario -> cenario.getTipoCena() == tipoControler)
                .findFirst()
                .orElse(null);
    }

    public void atualizarScene(TipoController tipoCena) {
        Cenario cenario = encontrarCenario(tipoCena);
        atualizarFechamento(cenario);
        palco.setScene(cenario.getCena());
        palco.setTitle(cenario.getTitulo());
        palco.setResizable(cenario.isResisable());
        cenaAtual = tipoCena;
        palco.show();
    }

    private void atualizarFechamento(Cenario cenario) {
        palco.setOnCloseRequest((event) -> {
            if (!cenario.getController().sair()) {
                event.consume();
                return;
            }
            Serializar.gravar();
        });
    }

    public TipoController getCenaAtual() {
        return cenaAtual;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
