/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Token;
import java.util.Arrays;

/**
 *
 * @author tpass
 */
public class BitaController {
    private Token token = null;
    
    public static Token ehIdentificador(String palavra, int numeroLinha) {
        if (palavra == null || palavra.isEmpty()) {
            return null;
        }
        char primeiroChar = palavra.charAt(0);
        if (!Character.isLetter(primeiroChar)) {
            return null;
        }
        for (int i = 1; i < palavra.length(); i++) {
            char c = palavra.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return null;
            }
        }
        return new Token("IDE", palavra, numeroLinha);
    }
    
    public static Token ehNumero(String palavra, int numeroLinha) {
        boolean pontoEncontrado = false;
        for (int i = 0; i < palavra.length(); i++) {
            char c = palavra.charAt(i);
            if (c == '.') {
                if (pontoEncontrado) {
                    return null;
                } else {
                    pontoEncontrado = true;
                }
            } else if (!Character.isDigit(c)) {
                return null;
            }
        }
        return new Token("NRO", palavra, numeroLinha);
    }
    
    public static Token ehOpAritimetico(String palavra, int numeroLinha){
        if(palavra.equals("+") || palavra.equals("-") || palavra.equals("*") ||
            palavra.equals("/") || palavra.equals("++") || palavra.equals("--")){
            return new Token("OPA", palavra, numeroLinha);   
        }
        return null;
    }
    
    
    
    public static Token ehOpRelacional(String palavra, int numeroLinha){
        if(palavra.equals("!=") || palavra.equals("==") || palavra.equals("<") ||
            palavra.equals(">") || palavra.equals(">=") || palavra.equals("<=") || palavra.equals("=")){
            return  new Token("OPR", palavra, numeroLinha);
        }
    return null;
    }
    
    public static Token ehDelimitadorDeComentarioDeLinha(String palavra, int numeroLinha) {
        if (palavra.startsWith("//")) {
            return new Token("COM", palavra, numeroLinha);
        } else {
            return null;
        }
    }

    public static Token ehDelimitadorDeComentarioDeBloco(String palavra, int numeroLinha) {
        if (palavra.startsWith("/*")) {
            return new Token("COM", palavra, numeroLinha);
        } else if (palavra.endsWith("*/")) {
            return new Token("COM", palavra, numeroLinha);
        } else {
            return null;
        }
    }

    public static Token ehDelimitador(String palavra, int numeroLinha) {
        if (";,()[]{}".contains(palavra)) {
            return new Token("DEL", palavra, numeroLinha);
        } else {
            return null;
        }
    }
    
   public static Token ehPalavraReservada(String palavra, int numeroLinha) {
    var palavrasReservadas = Arrays.asList(
            "var", "const",
            "struct", "procedure", "function",
            "start", "return", "if", "else",
            "then", "while", "read", "print",
            "int", "real", "boolean", "string",
            "true", "false");

    if (palavrasReservadas.contains(palavra)) {
        return new Token("PRE", palavra, numeroLinha);
    } else {
        return null;
    }
}

    
    public static Token ehCaracters(String palavra, int numeroLinha) {
        if (palavra.length() == 0) {
            return null; // Palavra vazia não é uma cadeia de caracteres válida
        }
        
        if (palavra.charAt(0) != '"' || palavra.charAt(palavra.length() - 1) != '"') {
            return null; // Cadeia de caracteres deve começar e terminar com aspas duplas
        }

        for (int i = 1; i < palavra.length() - 1; i++) {
            if (i < palavra.length() && palavra.charAt(i) == '\\') {
                i++;
                continue;
            }
            char c = palavra.charAt(i);
            if (c == '\\') {
                // Ignora o caractere de escape e o próximo caractere
                i++;
            } else if (!Character.isLetterOrDigit(c) && !ehSimbolo(c)) {
                return null;
            }
        }

        return new Token("CAD", palavra, numeroLinha);
    }

    public static boolean ehSimbolo(char c) {
        return ";,()[]{}".contains(Character.toString(c));
    }
}
