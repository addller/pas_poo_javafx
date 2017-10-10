
package ferramentas;

import javax.swing.JOptionPane;

public abstract class Mensagem {

    public static void aviso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public static boolean confirma(String mensagem, String titulo) {
        return JOptionPane.showConfirmDialog(null, mensagem, titulo, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
