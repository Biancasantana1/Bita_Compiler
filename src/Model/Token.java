/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tpass
 */
public class Token {
    private String tipo, lexema;
    private int linha;
    
    public Token(String tipo, String lexema, int linha) {
    this.tipo = tipo;
    this.lexema = lexema;
    this.linha = linha;
}

public String getTipo() {
    return tipo;
}

public String getLexema() {
    return lexema;
}

public int getLinha() {
    return linha;
}

@Override
public String toString() {
    return linha + " " + tipo + " " + lexema;
}

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

}
