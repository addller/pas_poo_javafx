package ferramentas;

import ambiente.Terminal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import voo.Voo;

public abstract class Serializar {

    private static final String PASTA_USER = System.getProperty("user.home"),
            CAMINHO_PERSISTENCIA = "/modulo/pas_poo/jerre_a_santos/terminal.ser";

    private static final Path CAMINHO = Paths.get(PASTA_USER + CAMINHO_PERSISTENCIA);

    public static void gravar() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO.toFile()))) {

            oos.writeObject(Terminal.getInstance().listarVoos());
            oos.flush();

        } catch (IOException ex) {
            Logger.getLogger(Serializar.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.aviso("Não foi possível salvar o trabalho:\n " + ex);
        }
    }

    public static Set<Voo> lerArquivos() {
        try {

            if (!Files.exists(CAMINHO)) {
                Files.createDirectories(CAMINHO.getParent());
                return new HashSet<>();
            }

            try (ObjectInputStream leitor = new ObjectInputStream(new FileInputStream(CAMINHO.toFile()))) {
                return (Set<Voo>) leitor.readObject();
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Serializar.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.aviso("Não foi possivel ler os dados salvos:\n " + ex);
            return null;
        }
    }
}
