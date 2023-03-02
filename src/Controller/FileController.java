package Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tpass
 */
public class FileController {
    
    
    public String arqLeitura(String nomeArquivo) throws UnsupportedEncodingException, IOException{
        String linha ="";
        String concatenar ="";

        try{ 
            FileInputStream file = new FileInputStream(nomeArquivo+".txt");
            InputStreamReader input = new InputStreamReader(file, "ISO-8859-1");
            BufferedReader br = new BufferedReader(input);
            while(true){
                if(linha!=null){ 
                    concatenar.concat(linha); // printando cada linha do arquivo de texto
                }
                else
                    break;
                linha = br.readLine();   
            }
            System.out.println(concatenar);

            br.close();
            file.close();
            input.close();
            return concatenar;
        }catch (Exception e){
            return e.getMessage();
        }
        
    }
    
    public void arqEscrita(){
        
    }
}
