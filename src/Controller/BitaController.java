/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Token;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.Arrays;

/**
 *
 * @author tpass
 */
public class BitaController {
    private Token token = null;
    
    public static boolean ehIdentificador(String palavra) {
        if (palavra == null || palavra.isEmpty()) {
            return false;
        }
        char primeiroChar = palavra.charAt(0);
        if (!Character.isLetter(primeiroChar)) {
            return false;
        }
        for (int i = 1; i < palavra.length(); i++) {
            char c = palavra.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }
    
    public static boolean ehNumero(char c) {
        boolean pontoEncontrado = false;
        if (c == '.') {
            pontoEncontrado = true;
        } else if (Character.isDigit(c)) {
            return true;
        }
            return false;
        
    }
    
    public static boolean ehOpAritimetico(String palavra){
        if(palavra.equals("+") || palavra.equals("-") || palavra.equals("*") ||
            palavra.equals("/") || palavra.equals("++") || palavra.equals("--")){
            return true;   
        }
        return false;
    }
    
    
    
    public static boolean ehOpRelacional(String palavra){
        if(palavra.equals("!=") || palavra.equals("==") || palavra.equals("<") ||
            palavra.equals(">") || palavra.equals(">=") || palavra.equals("<=") || palavra.equals("=")){
            return  true;
        }
    return false;
    }
    
    public static boolean ehDelimitadorDeComentarioDeLinha(String palavra) {
        if (palavra.startsWith("//")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ehDelimitadorDeComentarioDeBloco(String palavra) {
        if (palavra.startsWith("/*")) {
            return true;
        } else if (palavra.endsWith("*/")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ehDelimitador(String palavra) {
        if (";,()[]{}.".contains(palavra)) {
            return true;
        } else {
            return false;
        }
    }
    
   public static boolean ehPalavraReservada(String palavra) {
        var palavrasReservadas = Arrays.asList(
                "var", "const",
                "struct", "procedure", "function",
                "start", "return", "if", "else",
                "then", "while", "read", "print",
                "int", "real", "boolean", "string",
                "true", "false");

        if (palavrasReservadas.contains(palavra)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean ehCaracters(String palavra) {
        if (palavra.length() == 0) {
            return false; // Palavra vazia não é uma cadeia de caracteres válida
        }
        
        if (palavra.charAt(0) != '"' || palavra.charAt(palavra.length() - 1) != '"') {
            return false; // Cadeia de caracteres deve começar e terminar com aspas duplas
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
                return false;
            }
        }

        return true;
    }

    public static boolean ehSimbolo(char c) {
        return ";,()[]{}".contains(Character.toString(c));
    }
    
    public static boolean ehLetra(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    public static String[] quebraCaracter(String linha){
        String[] caracter = linha.split("[\\\\ \\\\]");
        return caracter;
    }
}
