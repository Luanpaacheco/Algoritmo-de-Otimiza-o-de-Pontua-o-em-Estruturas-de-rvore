import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private char[][] matriz; // Declare a matriz como um campo da classe
    private int linhaInicio;
    private int colunaInicio;
    

    public static void main(String[] args) {
        Main m = new Main();
        String []Casos={"casof30.txt","casof60.txt","casof90.txt","casof120.txt","casof150.txt","casof180.txt","casof250.txt","casof300.txt","casof500.txt"};
        for(String caso : Casos){
            m.matriz = m.convertLinesToMatrix(m.leArvore(caso));
            m.acharInicio(m.matriz);
            System.out.println("Melhor soma: " + m.buscarMelhorCaminho(m.linhaInicio, m.colunaInicio));  
        }

    }

    public int buscarMelhorCaminho(int linha, int coluna) {
        // if (linha < 0 || linha >= matriz.length || coluna < 0 || coluna >= matriz[0].length) {
        //     return 0;
        // }

        char atual = matriz[linha][coluna];

        if (atual == '#') {
            return 0;
        }

        int somaAtual = 0;
        if (Character.isDigit(atual)) {
            somaAtual = Character.getNumericValue(atual);
        }

        int melhorSoma = 0;

        switch (atual) {
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
                            rightV = buscarMelhorCaminho(linha - i, coluna + i) + somaAtual;
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
                            leftV = buscarMelhorCaminho(linha - i, coluna - i) + somaAtual;
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
                            rightW = buscarMelhorCaminho(linha - i, coluna + i) + somaAtual;
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
                            leftW = buscarMelhorCaminho(linha - i, coluna - i) + somaAtual;
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
                            centerW = buscarMelhorCaminho(linha - i, coluna) + somaAtual;
                            break;
                        }
                    }
                }
                melhorSoma = Math.max(rightW, Math.max(centerW, leftW));
                break;

            case '\\':
                for (int i = 1; linha - i > 0; i++) {
                    if ((matriz[linha - i][coluna - i] != '|' && matriz[linha - 1][coluna - 1] != '/') && matriz[linha - i][coluna - i] != ' ') {
                        melhorSoma = buscarMelhorCaminho(linha - i, coluna - i) + somaAtual;
                        break;
                    }
                }
                break;

            case '/':
                for (int i = 1; linha - i > 1; i++) {
                    if ((matriz[linha - i][coluna + 1] != '|' && matriz[linha - i][coluna + 1] != '\\') && matriz[linha - i][coluna + 1] != ' ') {
                        melhorSoma = buscarMelhorCaminho(linha - i, coluna + i) + somaAtual;
                        break;
                    }
                }
                break;

            case '|':
                for (int i = 1; linha - i > 0; i++) {
                    if ((matriz[linha - i][coluna] != '\\' && matriz[linha - i][coluna] != '/') && matriz[linha - i][coluna] != ' ') {
                        melhorSoma = buscarMelhorCaminho(linha - i, coluna) + somaAtual;
                        break;
                    }
                }
                break;

            default:
                // System.out.println("Tem um número antes do primeiro V ou W");
                if ((matriz[linha - 1][coluna] != '\\' && matriz[linha - 1][coluna] != '/') && matriz[linha - 1][coluna] != ' ') {
                    for (int i = 1; linha - i > 0; i++) {
                        if (matriz[linha - i][coluna] == '|' || Character.isDigit(matriz[linha - i][coluna]) || matriz[linha - i][coluna] == '#' || matriz[linha - i][coluna] == 'W' || matriz[linha - i][coluna] == 'V') {
                            melhorSoma = buscarMelhorCaminho(linha - i, coluna) + somaAtual;
                            break;
                        }
                    }
                }
                if ((matriz[linha - 1][coluna - 1] != '|' && matriz[linha - 1][coluna - 1] != '/') && matriz[linha - 1][coluna - 1] != ' ') {
                    for (int i = 1; linha - i > 0 && coluna - i > 0; i++) {
                        if (matriz[linha - i][coluna - i] == '\\' || Character.isDigit(matriz[linha - i][coluna - i]) || matriz[linha - i][coluna] == '#' || matriz[linha - i][coluna] == 'W' || matriz[linha - i][coluna] == 'V') {
                            melhorSoma = buscarMelhorCaminho(linha - i, coluna - i) + somaAtual;
                            break;
                        }
                    }
                }
                if((matriz[linha-1][coluna+1]!='|'&&matriz[linha-1][coluna+1]!='\\')&&matriz[linha-1][coluna+1]!=' '){// /
                    for (int i = 1; linha-i > 0; i++) {
                        if(matriz[linha-i][coluna+i]=='/'||Character.isDigit(matriz[linha-i][coluna+i])||matriz[linha-i][coluna]=='#'||matriz[linha-i][coluna]=='W'||matriz[linha-i][coluna]=='V'){
                            melhorSoma = buscarMelhorCaminho( linha - i, coluna+i) + somaAtual;
                            break;
                        }
                    }
                    
                }
        }
        return melhorSoma;
    }

    // Implemente os métodos leArvore() e leRoot() conforme necessário.



    private static char[][] convertLinesToMatrix(List<String> lines) {
        int numeroLinhas = lines.size();
        int numeroColunas = lines.stream().mapToInt(String::length).max().orElse(0);

        char[][] treeMatrix = new char[numeroLinhas][numeroColunas];

        for (int i = 0; i < numeroLinhas; i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                treeMatrix[i][j] = line.charAt(j);
            }
        }

        return treeMatrix;
    }

    public static void imprimeArvore(char[][] arvoreMatriz) {
        System.out.println("Matriz da árvore:");
        for (int i = 0; i < arvoreMatriz.length; i++) {
            System.out.print(i + " ");  // Imprime o número da linha
            for (char c : arvoreMatriz[i]) {
                System.out.print(c);
            }
            System.out.println();  // Nova linha após cada linha da matriz
        }
    }

    public static List<String> leArvore(String nomeArquivo) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private void acharInicio(char[][] arvoreLida){//ACHAR O ROOT INVERTENDO FAZENDO UM FOR DO CONTRARIO OK
        //Fiz o metodo que retorna um vetor para podermos separar em linha e coluna e usar no nosso metodo de buscar melhor caminho
        int numRows = arvoreLida.length;
        int numCols = arvoreLida[0].length;
        System.out.print("caso: "+ numCols+" | ");
        int[] valorRetorno = new int[2];
        for(int i=numRows-1; i > 0 ;i--){
            //este for começa a processar a arvore de baixo para cima
            for(int j = 0; j < numCols; j++){
                //aqui fica normal pois é da esquerda pra direita
                String valor = String.valueOf(arvoreLida[i][j]);
                if(!valor.isBlank()){
                    valorRetorno[0] = i;
                    linhaInicio=i;
                    valorRetorno[1] = j;
                    colunaInicio=j;
                    // imprimeArvore(arvoreLida);//COLUNA
                    return ;
                }
            }
        }
        return ;
    }
}

