package tela;

import tela.cadastroVoo.CadastroVooController;
import tela.cadastro_passageiro.CadastroPassageiroController;
import tela.dados_voo.DadosVooController;
import tela.inicial.InicialController;

public enum TipoController {
    INICIAL(InicialController.class, "Sertão Turismo", "inicial"),
    CADASTRO_PASSAGEIRO(CadastroPassageiroController.class, "Cadastro de Passageiros", "cadastroPassageiro"),
    CADASTRO_VOO(CadastroVooController.class, "Cadastro de Vôos", "cadastroVoo"),
    DADOS_VOO(DadosVooController.class, "Dados do Vôo", "dadosVoo");

    public final Class classe;
    public final String titulo, nomeArquivoFxml;

    private TipoController(Class classe, String titulo, String nomeArquivoFxml) {
        this.classe = classe;
        this.titulo = titulo;
        this.nomeArquivoFxml = nomeArquivoFxml + ".fxml";
    }

}
