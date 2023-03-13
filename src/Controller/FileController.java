package Controller;

import Model.Token;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tpass
 */
public class FileController {
    
    
     public static String[] arqLeitura(String nomeArquivo) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(nomeArquivo + ".txt"));
    List<String> linhas = new ArrayList<>();

    String linha = br.readLine();
    while (linha != null) {
        linhas.add(linha);
        linha = br.readLine();
    }

    br.close();

    return linhas.toArray(new String[0]);
}

    
    public static void arqEscrita(String nomeArquivo, List<Token> tokenList) throws IOException {
         FileWriter fileWriter = new FileWriter(nomeArquivo, true);
        for (Token token : tokenList) {
        String linhaSaida = String.format("%02d %s\n", token.getLinha(), token.toString());
        fileWriter.write(linhaSaida);
        System.out.println("Token encontrado: " + token);
        }
        fileWriter.close();
    }
}
