package View;

import Controller.BitaController;
import Controller.FileController;
import Model.Token;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BitaView {
    private static BitaController classificador = new BitaController();
    private static FileController arquivo = new FileController();

   public static void main(String[] args) throws IOException {
    String nomeArquivo = "primeiroAqv";
    String[] linhas = arquivo.arqLeitura(nomeArquivo);
    List<Token> tokenList = new ArrayList<>();
    int numeroLinha = 1;
    for (String linha : linhas) {
        System.out.println(linha);
        String[] palavras = linha.split("\\s+");
        for (String palavra : palavras) {
            System.out.println(palavra);
            Token token = identificarToken(palavra, numeroLinha);
            if (token != null) {
                tokenList.add(token);
            }
        }
        numeroLinha++;
    }

    FileWriter fileWriter = new FileWriter("teste3", true);
    for (Token token : tokenList) {
        String linhaSaida = String.format("%02d %s\n", token.getLinha(), token.toString());
        fileWriter.write(linhaSaida);
        System.out.println("Token encontrado: " + token);
    }
    fileWriter.close();
}


    public static Token identificarToken(String palavra, int numeroLinha) {
        Token token = classificador.ehPalavraReservada(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehOpAritimetico(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehOpRelacional(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehIdentificador(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehCaracters(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehDelimitadorDeComentarioDeLinha(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehDelimitadorDeComentarioDeBloco(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehDelimitador(palavra, numeroLinha);
        if (token != null) {
            return token;
        }

        token = classificador.ehNumero(palavra, numeroLinha);
        if (token != null){
            return token;
        }
    return null;
    
    }
}


