package ambiente;

import excessao.Excessao;
import ferramentas.Serializar;
import filtro.StringFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import usuario.Passageiro;
import voo.Voo;

public class Terminal {

    private Set<Voo> listaVoos;

    private static Terminal instance;

    private Terminal() {
    }

    public static Terminal getInstance() {
        synchronized (Terminal.class) {
            if (instance == null) {
                instance = new Terminal();
                instance.listaVoos = Serializar.lerArquivos();
            }
        }
        return instance;
    }

    public boolean cadastrarPassageiro(Voo voo, Passageiro passageiro) {
        return voo.adicionarPassageiro(passageiro);
    }

    public void cadastrarVoo(Voo voo) {
        excessaoVoo(voo);
        listaVoos.add(voo);
    }

    public void cancelarVoo(Voo voo) {
        excessaoVoo(voo);
        listaVoos.remove(voo);
    }
    
    public void cancelarReserva(Voo voo, Passageiro passageiro) {
        voo.cancelarReservaPassageiro(passageiro);
    }


    private void excessaoVoo(Voo voo) {
        Excessao.variavelNull(voo, "voo");
    }

    private Stream<Voo> streamVoo() {
        return listaVoos.stream();
    }

    public List<Voo> findVooPorOrigem(String origem) {
        return streamVoo()
                .filter(voo -> voo.getOrigem().equals(origem))
                .collect(Collectors.toList());
    }

    public List<Voo> findVooPorDestino(String destino) {
        return streamVoo()
                .filter(voo -> voo.getDestino().equals(destino))
                .collect(Collectors.toList());
    }

    public Voo findVooPorCodigo(int codigo) {
        return streamVoo()
                .filter(voo -> voo.getCodigoVoo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Passageiro> findPassageirosPorNome(String nome) {
        String nomeLowerCase = nome.toLowerCase();
        return listarPassageiros().stream()
                .filter(passageiro -> passageiro.getNomeLowerCase().contains(nomeLowerCase))
                .collect(Collectors.toList());
    }

    public Set<Voo> listarVoos() {
        return listaVoos;
    }

    public List<Passageiro> listarPassageiros() {
        return streamVoo()
                .map(Terminal::listarPassageiros)
                .reduce(new ArrayList<>(), (acumulaPassageiros, proximaLista) -> {
                    acumulaPassageiros.addAll(proximaLista);
                    return acumulaPassageiros;
                });
    }

    public List<Passageiro> listarFilaEsperaPassageiros() {
        return streamVoo()
                .map(Terminal::listarFilaEsperaPassageiros)
                .reduce(new ArrayList<>(), (acumulaPassageiros, proximaLista) -> {
                    acumulaPassageiros.addAll(proximaLista);
                    return acumulaPassageiros;
                });
    }

    public static List<Passageiro> listarPassageiros(Voo voo) {
        return voo.listarPassageiros();
    }

    public static List<Passageiro> listarFilaEsperaPassageiros(Voo voo) {
        return voo.listarFilaEspera();
    }

    public static void filtrarVooPorCodigo(ObservableList<Voo> listVoosDisponiveis, long codigo) {
        listVoosDisponiveis.removeIf(voo -> voo.getCodigoVoo() != codigo);
    }

    public static void filtrarVooPorOrigem(ObservableList<Voo> listVoosDisponiveis, String origem) {
        String origemLower = origem.toLowerCase();
        if (StringFilter.temAlgo(origem)) {
            listVoosDisponiveis.removeIf(
                    voo -> !voo.getOrigemToLower().contains(origemLower)
            );
        }
    }

    public static void filtraVooPorDestino(ObservableList<Voo> listVoosDisponiveis, String destino) {
        String destinoLower = destino.toLowerCase();
        if (StringFilter.temAlgo(destino)) {
            listVoosDisponiveis.removeIf(voo -> !voo.getDestinoToLower().contains(destinoLower));
        }
    }

}
