/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.util.Arrays;

/**
 *
 * @author tpass
 */
public class BitaController {
   
     public boolean ehIdentificador(String token) {
        //verificar se o primeiro character do token é uma letra ou não
        if (!Character.isLetter(token.charAt(0))) {
            return false;
        }
        for (int i = 1; i < token.length(); i++) {
            char c = token.charAt(i);
            //verifica se o character é uma letra ou um dígito ou se tem o sublinhado '_' 
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true;
    }
    
    public boolean ehNumero(String token) {
        boolean pontoEncontrado = false;
        for (int i = 1; i < token.length(); i++) {
            char c = token.charAt(i);
            if (c == '.') {
                if (pontoEncontrado) {
                    return false;
                } else {
                    pontoEncontrado = true;
                }
            } else if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean ehOpAritimetico(String token){
        if(token.equals("+") || token.equals("-") || token.equals("*") ||
            token.equals("/") || token.equals("++") || token.equals("--")){
            return true;   
        }
        return false;
    }
    
    public boolean ehOpRelacional(String token){
        if(token.equals("!=") || token.equals("==") || token.equals("<") ||
            token.equals(">") || token.equals(">=") || token.equals("<=") || token.equals("=")){
            return true;   
        }
        return false;
    }
    
    public static boolean ehDelimitadorDeComentarioDeLinha(String token) {
        return token.startsWith("//");
    }

    public static boolean ehDelimitadorDeComentarioDeBloco(String token) {
        return token.startsWith("/*") || token.endsWith("*/");
    }

    public static boolean ehDelimitador(String token) {
        return ";,()[]{}".contains(token);
    }
    
    public static boolean ehPalavrareservada(String token){
        var palavrasReservadas = Arrays.asList(
                "var", "const",
                "struct", "procedure", "function", 
                "start", "return", "if", "else",
                "then", "while", "read", "print",
                "int", "real", "boolean", "string",
                "true", "false");
        
        return palavrasReservadas.contains(token);
    }
    
    public static boolean ehCaracters(String input) {
        if (input.charAt(0) != '"' || input.charAt(input.length() - 1) != '"') {
            return false; // Cadeia de caracteres deve começar e terminar com aspas duplas
        }

        for (int i = 1; i < input.length() - 1; i++) {
            char c = input.charAt(i);
            if (c == '\\') {
                // Ignora o caractere de escape e o próximo caractere
                i++;
            } else if (!Character.isLetterOrDigit(c) && !isSymbol(c)) {
                // Caractere inválido na cadeia de caracteres
                return false;
            }
        }

        return true;
    }
    
    public static boolean isSymbol(char c) {
        String symbols = "!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?`~";
        return symbols.indexOf(c) != -1;
    }





}
