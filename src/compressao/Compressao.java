/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Augusto
 */
public class Compressao {

    static int tamDicionario = 6;
    static int tamBuffer = 4;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
//        String input = "bananabanabofana";
        String input = new String();
        String dataset = "entrada.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(dataset))) {
            String row;
            while ((row = br.readLine()) != null) {
                input += row;
            }
        }
        String saida = new String();
        saida = comprime(input);
        save("saida.txt", saida);
        System.out.println(saida);
    }

    public static String comprime(String input) {
        String resultado = new String();
        String dicionario = new String("");
        String buffer = new String();
        ArrayList<String> cod = new ArrayList();
        int iniDicionario, i = 0;

        while (i < input.length()) {
            iniDicionario = i - tamDicionario;
            if (iniDicionario < 0) {
                iniDicionario = 0;
            }

            dicionario = input.substring(iniDicionario, i);

            if (i + tamBuffer < input.length()) {
                buffer = input.substring(i, i + tamBuffer);
            } else {
                buffer = input.substring(i, input.length());
            }

            System.out.println("\ndic: " + dicionario);
            System.out.println("buf: " + buffer + "\n");
//            if (i + tamBuffer > input.length()) {
//            } else {
//            }

//            cod.add("(0,0," + input.substring(i, i + 1) + ")");
            int j, matchSize = 0, idMatch = 0;
            String prox = new String();

            for (j = buffer.length(); j > 0; j--) {
                System.out.println("sub: " + buffer.substring(0, j));
                int match = dicionario.lastIndexOf(buffer.substring(0, j));
                if (match >= 0) {
                    idMatch = dicionario.length() - match;
                    matchSize += j;
                    if (j == buffer.length()) {
                        break;
                    } else {
                        int aux = j;
                        for (int k = j; k > 0; k--) {
                            int rematch = buffer.substring(aux, buffer.length()).lastIndexOf(dicionario.substring(match, match + k));
                            while (rematch == 0) {
                                System.out.println(dicionario.substring(match, match + k));
                                matchSize += k;
                                aux = dicionario.substring(match, match + k).length();
                                rematch = buffer.substring(aux, buffer.length()).lastIndexOf(dicionario.substring(match, match + k));
                            }
                        }
                        break;
                    }
                }
            }

            prox = "";
            if (i + matchSize < input.length()) {
                prox = input.substring(i + matchSize, i + matchSize + 1);
            } else {
                prox = "null";
            }

            if (matchSize > 0) {
                cod.add("(" + idMatch + "," + matchSize + "," + prox + ")");
            } else {
                cod.add("(0," + matchSize + "," + prox + ")");
            }

            System.out.println("matchsize: " + matchSize);

            i = i + matchSize + 1;
        }
        for (String c : cod) {
            resultado += c;
        }
        return resultado;
    }

    public static void save(String file_name, String input) throws IOException {
        File file = new File(file_name);
        file.createNewFile();

        FileWriter fw = new FileWriter(file);

        fw.write("Saída Compressão\n");

        fw.write(input);

        fw.flush();
        fw.close();
    }
}
