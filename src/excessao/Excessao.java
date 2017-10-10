package excessao;

import filtro.StringFilter;
import javax.swing.JOptionPane;

public abstract class Excessao {

    /**
     * Função: obrigar a inicialização de uma variável
     *
     * @param objetos lista de objetos que não podem ser null
     * @param identificadorDoObjeto nome das variáveis a serem inicializadas
     * @throws NullPointerException se a variável não foi inicializada
     * @throws ArrayIndexOutOfBoundsException se o tamanho do array Objetos, for
     * diferente do tamanho do array identificadorDoObjeto
     */
    public static void seVariavelNull(Object[] objetos, String... identificadorDoObjeto) throws NullPointerException {
        for (int i = 0; i < objetos.length; i++) {
            variavelNull(objetos[i], identificadorDoObjeto[i]);
        }
    }

    /**
     * Função: obrigar a inicialização de uma variável
     *
     * @param objeto a ser verificado se foi inicializado
     * @param identificadorDoObjeto nome da variável
     * @throws NullPointerException se a variável não foi inicializada
     */
    public static void variavelNull(Object objeto, String identificadorDoObjeto) throws NullPointerException {
        if (objeto == null) {
            String causa = "Causa: o objeto " + identificadorDoObjeto + " não pode ser null.\n";
            JOptionPane.showMessageDialog(null, causa);
            throw new NullPointerException(causa);
        }
    }

    public static void seArgumentoIlegal(String causa, boolean lancarExcessao) throws IllegalArgumentException {
        if (lancarExcessao) {
            seArgumentoIlegal(causa, "", lancarExcessao);
        }
    }

    public static void seArgumentoIlegal(String causa, String sugestao, boolean lancarExcessao) throws IllegalArgumentException {
        if (lancarExcessao) {
            JOptionPane.showMessageDialog(null, causa + "\n" + sugestao);
            throw new IllegalArgumentException(causa + " " + sugestao != null ? "\n" + sugestao : "");
        }
    }

    /**
     * Definicao: String padrao não é null e ao remover todos os espaços deve
     * restar algum caractere
     *
     * @param string texto a ser verificado
     * @param nomeDoIdentificados nome do identificador na classe que chama este
     * método
     */
    public static void stringPadraoInvalida(String string, String nomeDoIdentificados) {
        if (!StringFilter.temAlgo(string)) {
            Excessao.seArgumentoIlegal("A string referente ao identificador: " + (string == null ? nomeDoIdentificados + " não pode ser null" : "não pode ser vazia"), true);
        }
    }
}
