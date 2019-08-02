package interdisciplinar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dinamica implements Runnable {

    private int[][] matriz;
    private int saida;
    private int valorMinimo = Integer.MAX_VALUE;
    private No menor;
    private String tipo = "Dinamica/";

    public Dinamica(int[][] m, int ArquivoSaida) {
        this.matriz = m;
        this.saida = ArquivoSaida;
    }

    private void arvore() {

        int linha = 0;
        No origem = new No(linha, null);

        ArrayList<Integer> todosNos = new ArrayList<>();

        int total = matriz.length;
        for (int i = 0; i < total; i++) {
            todosNos.add(i);
        }

        origem.setNaoVisitados(todosNos);

        No atual = origem;

        ArrayList<No> expansaoAtual = new ArrayList<>();

        for (Integer i : atual.getNaoVisitado()) {
            No novo = new No(i, atual, matriz[atual.valor][i]);
            novo.setNaoVisitados(atual.naoVisitados);
            expansaoAtual.add(novo);
        }

        while (true) {
            ArrayList<No> lista = new ArrayList<No>();
            for (No n : expansaoAtual) {

                atual = n;
                for (Integer i : atual.getNaoVisitado()) {

                    No novo = new No(i, atual, matriz[atual.valor][i]);
                    novo.setNaoVisitados(atual.naoVisitados);
                    if(!addNoLista(novo, lista)){
                        lista.add(novo);
                    }

                }
            }

            if (!lista.isEmpty()) {
                expansaoAtual = lista;
            } else {
                for (No n : expansaoAtual) {
                    No novo = new No(0, n, matriz[n.valor][0]);
                    lista.add(novo);
                }
                expansaoAtual = lista;
                break;
            }
        }

        for (No n : expansaoAtual) {
            if (n.peso < valorMinimo) {
                valorMinimo = n.peso;
                menor = n;
            }
        }

    }

    public boolean addNoLista(No n, ArrayList<No> lista) {

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).valor == n.valor) {
                if (n.peso < lista.get(i).valor) {
                    lista.set(i, n);
                    return true;
                }
            }
        }
        return false;

    }

    public String getCaminho(No n) {
        String saida = n.valor + "-";

        No pai = n.pai;
        while (pai != null) {
            saida += pai.valor + "-";
            pai = pai.pai;
        }
        return saida;
    }

    @Override
    public void run() {

        String formato = "#0000";
        DecimalFormat d = new DecimalFormat(formato);

        long tempoInicio = System.currentTimeMillis();

        arvore();

        long tempoFim = System.currentTimeMillis();
        long tempoTotal = tempoFim - tempoInicio;
        String escrita = d.format(saida) + ":" + String.valueOf(tempoTotal) + "\n";

        EscreverArquivo.getInstance().escreverCaixeiroViajante(getCaminho(menor), valorMinimo, String.valueOf(saida), tipo);

        try {
            Files.write(Paths.get("C:/Users/vanes/Documents/Interdisciplinar/grafos/Dinamica/tempo.txt"), escrita.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(ForcaBruta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}