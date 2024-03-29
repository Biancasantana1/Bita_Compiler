package View;

import Controller.BitaController;
import Controller.FileController;
import Model.Token;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class BitaView {
    private static BitaController classificador = new BitaController();
    private static FileController arquivo = new FileController();
public static void main(String[] args) throws IOException {
    String nomeArquivo = "primeiroAqv";
    String[] linhas = arquivo.arqLeitura(nomeArquivo);
    List<Token> tokenList = new ArrayList<>();
    List<Token> tokenMalFormadoList = new ArrayList<>();
    int numeroLinha = 1;
    String lexema = "";
    Token token = null;
    
    for (String linha : linhas) {
    System.out.println(linha);
    String[] palavras = BitaController.quebraCaracter(linha);
    
    for (String palavra : palavras) {
        int i = 0;
        while(i < palavra.length()) {
            char c = palavra.charAt(i);
            lexema = "" + c;
            if(BitaController.ehOpRelacional(lexema)){
                if (token != null) {
                    tokenList.add(token);
                    token = null;
                }
                Token novoToken = new Token("REL", lexema, numeroLinha);
                tokenList.add(novoToken);
                i++;
            }
            else if(BitaController.ehOpAritimetico(lexema)){
                
                if (token != null) {
                    tokenList.add(token);
                    token = null;
                }    
                Token novoToken = new Token("ART", lexema, numeroLinha);
                tokenList.add(novoToken);
                i++;
                
            }
            else if(BitaController.ehOpLog(c)){
                if (token != null) {
                    tokenList.add(token);
                    token = null;
                }    
                Token novoToken = new Token("LOG", lexema, numeroLinha);
                tokenList.add(novoToken);
                i++;
                
            }
              else if(BitaController.ehDelimitador(lexema)){
                if (token != null) {
                    tokenList.add(token);
                    token = null;
                }
                Token novoToken = new Token("DEL", lexema, numeroLinha);
                tokenList.add(novoToken);
                i++;
            }
         else if (BitaController.ehNumero(c)) {
    lexema = "" + c;
    int j = i+1;
    int cont = 0;
    boolean pontoEncontrado = false;
    boolean primeiroPontoEncontrado = false;

    // Verifica se é um número negativo
    boolean ehNegativo = false;
    if (i == 0 && j < palavra.length() && palavra.charAt(j) == '-') {
        lexema += "-";
        j++;
        ehNegativo = true;
    }

    while(j < palavra.length() && (BitaController.ehNumero(palavra.charAt(j)) || palavra.charAt(j) == '.')){
        if(palavra.charAt(j) == '.' && pontoEncontrado == false && j < palavra.length() - 1){
            pontoEncontrado = true;
            cont ++;
            if (!primeiroPontoEncontrado) {
                primeiroPontoEncontrado = true;
            }
        } else if (palavra.charAt(j) == '.' && pontoEncontrado == true ){
            // Se encontrar um ponto após já ter encontrado um, então é um número mal formado
            Token novoToken = new Token("NMF", lexema, numeroLinha);
            tokenMalFormadoList.add(novoToken);
            i++;
            break; // Para sair do loop e não continuar lendo o número mal formado
        } else if(BitaController.ehLetra(palavra.charAt(j))){
            // Se encontrar um ponto após já ter encontrado um, então é um número mal formado
            Token novoToken = new Token("NMF", lexema, numeroLinha);
            tokenMalFormadoList.add(novoToken);
            i++;
            break; // Para sair do loop e não continuar lendo o número mal formado
        }

        lexema += palavra.charAt(j);
        j++;
    }

    i = j;
    Token novoToken;
    if (primeiroPontoEncontrado && cont > 1) { // Se mais de um ponto é encontrado e o primeiro ponto é encontrado, cria um token "NMF"
        novoToken = new Token("NMF", lexema, numeroLinha);
        tokenMalFormadoList.add(novoToken);
    } else { // Se for um número válido, cria um token "NUM" ou "NUM_NEG" se for um número negativo
        novoToken = new Token(ehNegativo ? "NUM_NEG" : "NUM", lexema, numeroLinha);
    }

    if (token == null) {
        token = novoToken;
    } else {
        token.setLexema(token.getLexema() + lexema);
    }
}


            


            else if (BitaController.ehLetra(c)){
                lexema = "" + c;
                int aspas = 0;
                int j = i+1;
                Token novoToken = null;

                boolean aspasAberta = false;

                while(j < palavra.length() && (BitaController.ehLetra(palavra.charAt(j)) 
                        || Character.isDigit(palavra.charAt(j)) || palavra.charAt(j) == '\"')) {
                    lexema += palavra.charAt(j);
                     if(palavra.charAt(j) == '\"' ){
            if(!aspasAberta){
                aspasAberta = true;
                    System.out.println(aspasAberta);

                
            }
            
        }
                    j++;
                    
                }
                i = j;
                if (BitaController.ehPalavraReservada(lexema)) {
                    novoToken = new Token("PR", lexema, numeroLinha);
                }
                else if (BitaController.ehIdentificador(lexema)){
                    novoToken = new Token("ID", lexema, numeroLinha);
                }
//                else {//if (BitaController.ehCadeiaCaracters(lexema)){
//                  if(!aspasAberta){
//        novoToken = new Token("CMF", lexema, numeroLinha);
//    }
//    else{
//        novoToken = new Token("CAC", lexema, numeroLinha);
//    }

                
                if (token == null) {
                     token = novoToken;
                }
                else {
                    token.setLexema(token.getLexema() + lexema);
                }
            }
                else if (c == '"') {
                        int j = i+1;
                Token novoToken = null;
                        //i = j;
                        while (j < palavra.length()) {
                            String aux = "" + palavra.charAt(j);

                            if (aux == "\"") {
                                lexema += aux;
                                i = j;
                                j++;
                                break;
                            } else {
                               lexema += aux;
                                i = j;
                                j++;
                            }
                        }

                        if (lexema.endsWith("\"")) {
                            novoToken = new Token("CAC", lexema, numeroLinha);
                            tokenList.add(novoToken);
                        } else {
                            novoToken = new Token("CMF", lexema, numeroLinha);
                            tokenMalFormadoList.add(novoToken);
                        }

                       // i++;
    
                }
//            else if(BitaController.ehCadeiaCaracters(lexema)){
//    lexema = "" + c;
//    boolean aspasAberta = false;
//    int j = i+1;
//        Token novoToken = null;
//
//    while(j < palavra.length() && (BitaController.ehLetra(palavra.charAt(j)) 
//            || Character.isDigit(palavra.charAt(j)) || palavra.charAt(j) == '\"')) {
//        lexema += palavra.charAt(j);
//        if(palavra.charAt(j) == '\"'){
//            if(!aspasAberta){
//                aspasAberta = true;
//            }
//            else{
//                //novoToken = new Token("CCM", lexema, numeroLinha);
//                break;
//            }
//        }
//        j++;
//    }
//    i = j;
//    if(!aspasAberta){
//        novoToken = new Token("CMF", lexema, numeroLinha);
//    }
//    else{
//        novoToken = new Token("CAC", lexema, numeroLinha);
//    }
//    
//    if (token == null) {
//         token = novoToken;
//    }
//    else {
//        token.setLexema(token.getLexema() + lexema);
//    }
//}

            else {
                System.out.println("Erro léxico na linha " + numeroLinha + ": caracter inválido " + c);
                i++;
            }
        }
        if (token != null) {
            tokenList.add(token);
            token = null;
        }
        
    }
    numeroLinha++;
}
for (Token t : tokenList) {
    System.out.println(t);
}
for (Token t : tokenMalFormadoList) {
    System.out.println(t);
}

  

//    FileWriter fileWriter = new FileWriter("teste3", true);
//    for (Token token : tokenList) {
//        String linhaSaida = String.format("%02d %s\n", token.getLinha(), token.toString());
//        fileWriter.write(linhaSaida);
//        System.out.println("Token encontrado: " + token);
//    }
//    fileWriter.close();
}
  
}


