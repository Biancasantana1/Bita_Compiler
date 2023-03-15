import Model.Token;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class Bita_Compiler {
    public static List<Token> tokenList = new ArrayList<>();
    public static List<Token> tokenMalFormadoList = new ArrayList<>();
    public static void main(String[] args) {
        try {
            String nomeArquivo = "x-entrada.txt";
            String saidaArquivo = "x-saida.txt";

            BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
            FileWriter writer = new FileWriter(saidaArquivo);

            String line;
            int lineCounter = 1;
            boolean inBlockComment = false;
            String utf_8 = "áãâàÁÃÂÀéêèÉÊÈíîìÍÎÌóõôòÓÕÔÒúûùÚÛÙçÇ";

            while ((line = reader.readLine()) != null) {
                int columnCounter = 1;
                int i = 0;
                String lexeme = "";
                while (i < line.length()) {
                    char currentChar = line.charAt(i);
                    
                    if (inBlockComment) {
                        // Verifica se o comentário de bloco está terminado
                        if (currentChar == '*' && i < line.length() - 1 && line.charAt(i + 1) == '/') {
                            inBlockComment = false;
                            lexeme += "*/";
                            i += 2;
                            columnCounter += 2;
                            Token novoToken = new Token("COM", lexeme, lineCounter);
                            tokenList.add(novoToken);
                            lexeme = "";
                        } else if (i == line.length() - 1) {
                            // Fim de linha dentro do comentário de bloco
                            lexeme += currentChar;
                            i++;
                            columnCounter++;
                            Token novoToken = new Token("COMF", lexeme, lineCounter);
                            tokenMalFormadoList.add(novoToken);


                            lexeme = "";
                        } else {
                            lexeme += currentChar;
                            i++;
                            columnCounter++;
                        }
                        continue;
                    }else if (currentChar == '/' && i < line.length() - 1 && line.charAt(i + 1) == '*') {
                        // Início de comentário de bloco
                        inBlockComment = true;
                        lexeme += "/*";
                        i += 2;
                        columnCounter += 2;
                        continue;
                    }else if (Character.isDigit(currentChar) || currentChar == '.'  || currentChar == '-') {
                        boolean isFloat = false;
                        boolean isNegative = false;
                        if (currentChar == '-') {
                            if (i < line.length() - 1 && (Character.isDigit(line.charAt(i + 1)))) {
                                lexeme += currentChar;
                                isNegative = true;
                                i++;
                                currentChar = line.charAt(i);
                            } else {
                                Token novoToken = new Token("NMF", lexeme, lineCounter);
                                tokenMalFormadoList.add(novoToken);
                                lexeme = "";
                                i++;
                                columnCounter++;
                                break;
                            }
                        }
                        while (i < line.length() && (Character.isDigit(currentChar) || currentChar == '.')) {
                            if (currentChar == '.') {
                                if (isFloat) {
                                    Token novoToken = new Token("NMF", lexeme, lineCounter);
                                    tokenMalFormadoList.add(novoToken);
                                    lexeme = "";
                                    break;
                                } else {
                                    isFloat = true;
                                }
                            }
                            lexeme += currentChar;
                            i++;

                            if (i < line.length()) {
                                currentChar = line.charAt(i);
                            }
                        }

                        if (lexeme.endsWith(".")|| lexeme.startsWith(".")) {
                            Token novoToken = new Token("NMF", lexeme, lineCounter);
                            tokenMalFormadoList.add(novoToken);
                            lexeme = "";
                       
                        } else if (lexeme.endsWith("0") || lexeme.endsWith("1") || lexeme.endsWith("2")||
                                lexeme.endsWith("3") || lexeme.endsWith("4")|| lexeme.endsWith("5") ||
                                lexeme.endsWith("6") || lexeme.endsWith("7") || lexeme.endsWith("8") ||
                                lexeme.endsWith("9")){
                            Token novoToken = new Token("NUM", lexeme, lineCounter);
                            tokenList.add(novoToken);
                            lexeme = "";
                        }
                        continue;
                    } else if (Character.isLetter(currentChar)) {
                        while (i < line.length() && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
                            lexeme += currentChar;
                            i++;

                            if (i < line.length()) {
                                currentChar = line.charAt(i);
                            }
                        }
                        if(isReservedWord(lexeme)){
                            Token novoToken = new Token("PRE", lexeme, lineCounter);
                            tokenList.add(novoToken);
                            lexeme = "";
                        }else if(utf_8.contains(lexeme)){
                            Token novoToken = new Token("TMF", lexeme, lineCounter );
                            tokenMalFormadoList.add(novoToken);
                            lexeme = "";
                        }
                        else{
                           Token novoToken = new Token("IDE", lexeme, lineCounter);
                            tokenList.add(novoToken);
                            lexeme = "";
                        }
                        continue;
                    } else if (currentChar == '"') {
                        lexeme += currentChar;
                        i++;
                        columnCounter++;

                        while (i < line.length()) {
                            currentChar = line.charAt(i);

                            if (currentChar == '"') {
                                lexeme += currentChar;
                                i++;
                                columnCounter++;
                                break;
                            } else {
                                lexeme += currentChar;
                                i++;
                                columnCounter++;
                            }
                        }
                        if(utf_8.contains(lexeme)){
                            Token novoToken = new Token("TMF", lexeme, lineCounter );
                            tokenMalFormadoList.add(novoToken);
                            lexeme = "";
                            break;
                        }else if (lexeme.endsWith("\"")) {
                            Token novoToken = new Token("CAC", lexeme, lineCounter );
                            tokenList.add(novoToken);
                            lexeme = "";
                        } else {
                            Token novoToken = new Token("CMF", lexeme, lineCounter );
                            tokenMalFormadoList.add(novoToken);
                            lexeme = "";
                        }

                        continue;
                    } else if (currentChar == '/') {
                        if (i < line.length() - 1 && line.charAt(i + 1) == '/') {
                            Token novoToken = new Token("COM", line.substring(i), lineCounter);
                            tokenList.add(novoToken);
                            lexeme = "";
                            break;
                        } else {
                            Token novoToken = new Token("ART", "/", lineCounter);
                            tokenList.add(novoToken);
                            lexeme = "";
                            i++;
                            continue;
                        }
                    } else if (isDelimiter(currentChar)) {
                        lexeme = "" + currentChar;
                        Token novoToken = new Token("DEL", lexeme, lineCounter);
                        tokenList.add(novoToken);
                        lexeme = "";
                    } else if (isArithmeticOperator(currentChar)) {
                        lexeme = "" + currentChar;
                        if (!isLastChar(line, i) && line.charAt(i + 1) == currentChar) {
                            lexeme += line.charAt(i + 1);
                            i++;
                        }

                        Token novoToken = new Token("ART", lexeme, lineCounter);
                        tokenList.add(novoToken);
                        lexeme = "";
                    } else if (isRelationalOperator(currentChar)) {
                        lexeme = "" + currentChar;
                        if (!isLastChar(line, i) && line.charAt(i + 1) == '=') {
                            lexeme += line.charAt(i + 1);
                            i++;
                        }

                        Token novoToken = new Token("REL", lexeme, lineCounter);
                        tokenList.add(novoToken);
                        lexeme = "";
                    }else if (isLogicalOperator(currentChar)) {
                        lexeme = "" + currentChar;

                        if (!isLastChar(line, i) && line.charAt(i + 1) == currentChar) {
                            lexeme += line.charAt(i + 1);
                            i++;
                        }

                        Token novoToken = new Token("LOG", lexeme, lineCounter);
                        tokenList.add(novoToken);
                        lexeme = "";    
                } 
                i++;
                columnCounter++;
            }

            lineCounter++;
        }
        
        for (Token t : tokenList) {
            writer.write(t.toString() + "\n");
        }
         writer.write(" " + "\n");
        for (Token t : tokenMalFormadoList) {
            writer.write(t.toString() + "\n");
        }

        reader.close();
        writer.close();

    } catch (IOException e) {
        System.err.println("An error occurred while processing the file: " + e.getMessage());
    }
}

private static boolean isDelimiter(char c) {
    return c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == '[' || c == ']'|| c == '.'|| c == ',';
}

private static boolean isArithmeticOperator(char c) {
    return c == '+' || c == '-' || c == '*' || c == '/';
}

private static boolean isRelationalOperator(char c) {
    return c == '<' || c == '>' || c == '=' ;
}

private static boolean isLogicalOperator(char c) {
    return c == '&' || c == '|'|| c == '!';
}

private static boolean isLastChar(String line, int i) {
    return i == line.length() - 1;
}
public static boolean isUTF8(byte[] bytes) {
    Charset utf8charset = Charset.forName("UTF-8");
    String str = new String(bytes, utf8charset);
    return true;
}

private static boolean isReservedWord(String lexeme) {
    return lexeme.equals("int") || lexeme.equals("var") || lexeme.equals("boolean") || 
            lexeme.equals("const") || lexeme.equals("if")
            || lexeme.equals("else") || lexeme.equals("while") || lexeme.equals("struct") 
            || lexeme.equals("then") || lexeme.equals("read")
            || lexeme.equals("print") || lexeme.equals("real")
            || lexeme.equals("string") || lexeme.equals("true")
            || lexeme.equals("false") || lexeme.equals("procedure") || lexeme.equals("return")
            || lexeme.equals("function") || lexeme.equals("start");
     
}

}
