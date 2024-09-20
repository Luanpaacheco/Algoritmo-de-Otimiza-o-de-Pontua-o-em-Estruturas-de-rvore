import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Main {
    private char[][] matriz;
    private int linhaInicio;
    private int colunaInicio;

    public static void main(String[] args) throws FileNotFoundException {
        long inicioTotal = System.nanoTime();
        Main m = new Main();
        String []Casos={"casof30.txt","casof60.txt","casof90.txt","casof120.txt","casof150.txt","casof180.txt","casof250.txt","casof300.txt","casof500.txt"};
        
        for(String caso : Casos){
            long inicio = System.nanoTime();
            m.matriz = m.lerArquivo(caso);
            m.acharInicio(m.matriz); 
            System.out.print("Melhor soma: " + m.PercorrerESomaDoMelhorCaminho(m.linhaInicio, m.colunaInicio) + " | "); 
            long fim = System.nanoTime();
            System.out.println("Tempo de execução: "+ (fim-inicio));
        }
        long fimTotal = System.nanoTime();
        System.out.println("Tempo de execução total: "+ (fimTotal-inicioTotal));
    }

    public int PercorrerESomaDoMelhorCaminho(int linha, int coluna) {
        char atual = matriz[linha][coluna];
        int somaAtual,melhorSoma;
        melhorSoma = 0;
        somaAtual=0;
        switch (atual) {
            case '#':
                melhorSoma=0;
                break;
                
                
            case 'V':
                int rightV = -1;
                int leftV = -1;
                somaAtual = 0;
                
                for (int i = 1; linha - i > 0 && coluna + i < matriz[linha - i].length; i++) {
                    if ((matriz[linha - i][coluna + i] != '|' && matriz[linha - i][coluna + i] != '\\') && matriz[linha - i][coluna + i] != ' ') {
                        if (Character.isDigit(matriz[linha - i][coluna + i])) {
                            somaAtual += Character.getNumericValue(matriz[linha - i][coluna + i]);
                        }
                        if (matriz[linha - i][coluna + i] == 'V' || matriz[linha - i][coluna + i] == 'W' || matriz[linha - i][coluna + i] == '#') {
                            rightV = PercorrerESomaDoMelhorCaminho(linha - i, coluna + i) + somaAtual;
                            break;
                        }
                    }
                }
                somaAtual = 0;
                for (int i = 1; linha - i > 0 && coluna - i > 0; i++) {
                    if ((matriz[linha - i][coluna - i] != '|' && matriz[linha - i][coluna - i] != '/') && matriz[linha - i][coluna - i] != ' ') {
                        if (Character.isDigit(matriz[linha - i][coluna - i])) {
                            somaAtual += Character.getNumericValue(matriz[linha - i][coluna - i]);
                        }
                        if (matriz[linha - i][coluna - i] == 'V' || matriz[linha - i][coluna - i] == 'W' || matriz[linha - i][coluna - i] == '#') {
                            leftV = PercorrerESomaDoMelhorCaminho(linha - i, coluna - i) + somaAtual;
                            break;
                        }
                    }
                }
                melhorSoma = Math.max(rightV, leftV);
                break;

            case 'W':
                
                int rightW = -1;
                int leftW = -1;
                int centerW = -1;
                somaAtual = 0;
                
                for (int i = 1; linha - i > 0 && coluna + i < matriz[linha - i].length; i++) {
                    if ((matriz[linha - i][coluna + i] != '|' && matriz[linha - i][coluna + i] != '\\') && matriz[linha - i][coluna + i] != ' ') {
                        if (Character.isDigit(matriz[linha - i][coluna + i])) {
                            somaAtual += Character.getNumericValue(matriz[linha - i][coluna + i]);
                        }
                        if (matriz[linha - i][coluna + i] == 'V' || matriz[linha - i][coluna + i] == 'W' || matriz[linha - i][coluna + i] == '#') {
                            rightW = PercorrerESomaDoMelhorCaminho(linha - i, coluna + i) + somaAtual;
                            break;
                        }
                    }
                }
                somaAtual = 0;
                for (int i = 1; linha - i > 0 && coluna - i > 0; i++) {
                    if ((matriz[linha - i][coluna - i] != '|' && matriz[linha - i][coluna - i] != '/') && matriz[linha - i][coluna - i] != ' ') {
                        if (Character.isDigit(matriz[linha - i][coluna - i])) {
                            somaAtual += Character.getNumericValue(matriz[linha - i][coluna - i]);
                        }
                        if (matriz[linha - i][coluna - i] == 'V' || matriz[linha - i][coluna - i] == 'W' || matriz[linha - i][coluna - i] == '#') {
                            leftW = PercorrerESomaDoMelhorCaminho(linha - i, coluna - i) + somaAtual;
                            break;
                        }
                    }
                }
                somaAtual = 0;
                for (int i = 1; linha - i > 0; i++) {
                    if ((matriz[linha - i][coluna] != '|' && matriz[linha - i][coluna] != '/') && matriz[linha - i][coluna] != ' ') {
                        if (Character.isDigit(matriz[linha - i][coluna])) {
                            somaAtual += Character.getNumericValue(matriz[linha - i][coluna]);
                        }
                        if (matriz[linha - i][coluna] == 'V' || matriz[linha - i][coluna] == 'W' || matriz[linha - i][coluna] == '#') {
                            centerW = PercorrerESomaDoMelhorCaminho(linha - i, coluna) + somaAtual;
                            
                            break;
                        }
                    }
                }
                melhorSoma = Math.max(rightW, Math.max(centerW, leftW));
                break;

            default:
                
                if (Character.isDigit(atual)) {
                    somaAtual = Character.getNumericValue(atual);
                }
                if ((matriz[linha - 1][coluna] != '\\' && matriz[linha - 1][coluna] != '/') && matriz[linha - 1][coluna] != ' ') {
                    for (int i = 1; linha - i > 0; i++) {
                        if ( Character.isDigit(matriz[linha - i][coluna]) || matriz[linha - i][coluna] == '#' || matriz[linha - i][coluna] == 'W' || matriz[linha - i][coluna] == 'V') {
                            melhorSoma = PercorrerESomaDoMelhorCaminho(linha - i, coluna) + somaAtual;
                            break;
                        }
                    }
                }
                break;
        }
        return melhorSoma;
    }

    private char[][] lerArquivo(String arquivo) throws FileNotFoundException {
        List<String> linhas = new ArrayList<>();
        File file = new File(arquivo);
        Scanner leitor = new Scanner(file);
        String primeiraLinha = leitor.nextLine();
        String[] colunas = primeiraLinha.split(" ");
        int numeroLinhas = Integer.parseInt(colunas[0])+1;
        int numeroColunas = Integer.parseInt(colunas[1]);

        char[][]mat = new char[numeroLinhas][numeroColunas];
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                linhas.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < numeroLinhas; i++) {
            String line = linhas.get(i);
            for (int j = 0; j < line.length(); j++) {
                mat[i][j] = line.charAt(j);
            }
        }
        return mat;
    }  
    private void acharInicio(char[][] arvoreLida) {
        int totalLinhas = arvoreLida.length;
        int totalColunas = arvoreLida[0].length;
        System.out.print("caso: " + totalColunas + " | ");
        
        int linhaAtual = totalLinhas - 1;
        int colunaAtual = 0; 
        while (colunaAtual < totalColunas) {
            char valor = arvoreLida[linhaAtual][colunaAtual];
            if (valor!=' ') {
                    linhaInicio = linhaAtual;
                    colunaInicio = colunaAtual;
                    return;
            }
            colunaAtual++;
        }
       
    }

}
