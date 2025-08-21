import java.util.Scanner;

public class Main {

    public static void mostrarCandidatos(String[] nomes, int[] numeros) {
        System.out.println("Lista de candidatos:");
        for (int i = 0; i < nomes.length; i++) {
            System.out.println(numeros[i] + " - " + nomes[i]);
        }
    }

    public static int buscarIndicePorNumero(int[] numeros, int numero) {
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == numero) return i;
        }
        return -1;
    }

    public static int somar(int[] vetor) {
        int total = 0;
        for (int i = 0; i < vetor.length; i++) {
            total += vetor[i];
        }
        return total;
    }

    public static void ordenarPorVotosDesc(String[] nomes, int[] numeros, int[] votos) {
        boolean trocou;
        int n = votos.length;
        do {
            trocou = false;
            for (int i = 0; i < n - 1; i++) {
                if (votos[i] < votos[i + 1]) {
                    int tv = votos[i]; votos[i] = votos[i + 1]; votos[i + 1] = tv;
                    int tn = numeros[i]; numeros[i] = numeros[i + 1]; numeros[i + 1] = tn;
                    String ts = nomes[i]; nomes[i] = nomes[i + 1]; nomes[i + 1] = ts;
                    trocou = true;
                }
            }
            n--;
        } while (trocou);
    }

    public static int[][] construirMatrizResultados(int[] numeros, int[] votos) {
        int[][] matriz = new int[numeros.length][2];
        for (int i = 0; i < numeros.length; i++) {
            matriz[i][0] = numeros[i];
            matriz[i][1] = votos[i];
        }
        return matriz;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== SISTEMA DE VOTAÇÃO PARA SÍNDICO (apenas vetores e matriz) ===");

        int qtdCandidatos;
        do {
            System.out.print("Digite o número de candidatos (>= 1): ");
            qtdCandidatos = sc.nextInt();
        } while (qtdCandidatos < 1);
        sc.nextLine();

        String[] nomes = new String[qtdCandidatos];
        int[] numeros = new int[qtdCandidatos];
        int[] votos = new int[qtdCandidatos];

        for (int i = 0; i < qtdCandidatos; i++) {
            System.out.print("Nome do candidato " + (i + 1) + ": ");
            nomes[i] = sc.nextLine();
            System.out.print("Número do candidato " + (i + 1) + ": ");
            numeros[i] = sc.nextInt();
            sc.nextLine();
        }

        int qtdEleitores;
        do {
            System.out.print("\nDigite o número de eleitores (>= 1): ");
            qtdEleitores = sc.nextInt();
        } while (qtdEleitores < 1);

        for (int e = 1; e <= qtdEleitores; e++) {
            System.out.println("\n--- Eleitor " + e + " ---");
            mostrarCandidatos(nomes, numeros);

            int votoNumero;
            int idx;
            do {
                System.out.print("Digite o número do seu candidato: ");
                votoNumero = sc.nextInt();
                idx = buscarIndicePorNumero(numeros, votoNumero);
                if (idx == -1) {
                    System.out.println("Voto inválido! Informe um número de candidato listado.");
                }
            } while (idx == -1);

            votos[idx]++;
        }

        int totalVotos = somar(votos);
        ordenarPorVotosDesc(nomes, numeros, votos);

        System.out.println("\n=== RESULTADO DA ELEIÇÃO ===");
        for (int i = 0; i < nomes.length; i++) {
            double perc = (votos[i] * 100.0) / totalVotos;
            System.out.printf("%s (%d) - %d votos (%.2f%%)%n", nomes[i], numeros[i], votos[i], perc);
        }

        double percVencedor = (votos[0] * 100.0) / totalVotos;
        System.out.printf("%n>>> VENCEDOR: %s (%d) com %.2f%% dos votos! <<<%n",
                nomes[0], numeros[0], percVencedor);

        int[][] matrizResultados = construirMatrizResultados(numeros, votos);
        System.out.println("\nMatriz de resultados [numero, votos]:");
        for (int i = 0; i < matrizResultados.length; i++) {
            System.out.println("[" + matrizResultados[i][0] + ", " + matrizResultados[i][1] + "]");
        }

        sc.close();
    }
}
