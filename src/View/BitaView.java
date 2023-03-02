package View;

import Controller.BitaController;
import Controller.FileController;
import java.io.IOException;

public class BitaView {
    private static BitaController classificador = new BitaController();
    private static FileController arquivo = new FileController();
    
    public static void main(String[] args) throws IOException {
//        String nomeArquivo = "primeiroAqv";
        String codigoFonte = "-1";
        System.out.println(codigoFonte);
        
        String[] tokens = codigoFonte.split("[\\s;]+"); // dividir a string em tokens com base em espaço e ponto e vírgula
        
        for (String token : tokens) {
            System.out.println(token);
            System.out.println(classificador.ehNumero(token));
        }
    }
}
