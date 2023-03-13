import java.io.*;

public class Bita_Compiler {
    public static void main(String[] args) {
        try {
            String nomeArquivo = "primeiroAqv.txt";
            String saidaArquivo = "teste3.txt";

            BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
            FileWriter writer = new FileWriter(saidaArquivo);

            String line;
            int lineCounter = 1;

            while ((line = reader.readLine()) != null) {
                int columnCounter = 1;
                int i = 0;

                while (i < line.length()) {
                    char currentChar = line.charAt(i);
                    String lexeme = "" + currentChar ;
                    System.out.println(lexeme);

                    if (Character.isDigit(currentChar) || currentChar == '.') {
                        boolean isFloat = false;

                        while (i < line.length() && (Character.isDigit(currentChar) || currentChar == '.')) {
                            if (currentChar == '.') {
                                if (isFloat) {
                                    processMalformedNumberToken(writer, lexeme, lineCounter, columnCounter);
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

                        if (lexeme.endsWith(".")) {
                            processMalformedNumberToken(writer, lexeme, lineCounter, columnCounter);
                        } else {
                            processNumberToken(writer, lexeme, lineCounter, columnCounter);
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

                        processIdentifierOrReservedToken(writer, lexeme, lineCounter, columnCounter);

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

                        if (lexeme.endsWith("\"")) {
                            processStringToken(writer, lexeme, lineCounter, columnCounter - lexeme.length());
                        } else {
                            processMalformedStringToken(writer, lexeme, lineCounter, columnCounter - lexeme.length());
                        }

                        continue;
                    } else if (currentChar == '/') {
                        if (i < line.length() - 1 && line.charAt(i + 1) == '/') {
                            processCommentToken(writer, line.substring(i), lineCounter, columnCounter);
                            break;
                        } else {
                            processArithmeticOperatorToken(writer, "/", lineCounter, columnCounter);
                            i++;
                            continue;
                        }
                    } else if (isDelimiter(currentChar)) {
                        lexeme = "" + currentChar;
                        processDelimiterToken(writer, lexeme, lineCounter, columnCounter);
                    } else if (isArithmeticOperator(lexeme)) {
                        lexeme = "" + currentChar;

                        if (!isLastChar(line, i) && line.charAt(i + 1) == currentChar) {
                            lexeme += line.charAt(i + 1);
                            i++;
                        }

                        processArithmeticOperatorToken(writer, lexeme, lineCounter, columnCounter);
                        lexeme = "";
                    } else if (isRelationalOperator(lexeme)) {
                        lexeme = "" + currentChar;
                        if (!isLastChar(line, i) && line.charAt(i + 1) == '=') {
                            lexeme += line.charAt(i + 1);
                            i++;
                        }

                        processRelationalOperatorToken(writer, lexeme, lineCounter, columnCounter);
                        lexeme = "";
                    } else if (isLogicalOperator(lexeme)) {
                        lexeme = "" + currentChar;

                        if (!isLastChar(line, i) && line.charAt(i + 1) == currentChar) {
                            lexeme += line.charAt(i + 1);
                            i++;
                        }

                        processLogicalOperatorToken(writer, lexeme, lineCounter, columnCounter);
                        lexeme = "";
                } else {
                    processUnknownToken(writer, "" + currentChar, lineCounter, columnCounter);
                }

                i++;
                columnCounter++;
            }

            lineCounter++;
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

private static boolean isArithmeticOperator(String c) {
    return c == "+" || c == "-" || c == "*" || c == "/" || c == "++"|| c == "--";
}

private static boolean isRelationalOperator(String c) {
    return c == "<" || c == ">" || c == "=" || c == "!="|| c == ">=" || c == "<="|| c == "==";
}

private static boolean isLogicalOperator(String c) {
    return c == "&&" || c == "||"|| c == "!";
}

private static boolean isLastChar(String line, int i) {
    return i == line.length() - 1;
}

private static void processNumberToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
    writer.write(String.format("%02d NRO %s\n", line, lexeme));
}

private static void processMalformedNumberToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
    writer.write(String.format("%02d NMF %s\n", line, lexeme));
}

private static void processIdentifierOrReservedToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
    if (isReservedWord(lexeme)) {
        writer.write(String.format("%02d PRE %s\n", line, lexeme));
    } else {
        writer.write(String.format("%02d IDE %s\n", line, lexeme));
    }
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

private static void processStringToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
    writer.write(String.format("%02d CAC %s\n", line, lexeme));
}

private static void processMalformedStringToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
    writer.write(String.format("%02d CMF %s\n", line, lexeme));
}

private static void processCommentToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d COM %s\n", line, lexeme));
}

private static void processMalformedCommentToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d CMT %s\n", line, lexeme));
}

private static void processDelimiterToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d DEL %s\n", line, lexeme));
}

private static void processArithmeticOperatorToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d ART %s\n", line, lexeme));
}

private static void processRelationalOperatorToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d REL %s\n", line, lexeme));
}

private static void processLogicalOperatorToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d LOP %s\n", line, lexeme));
}

private static void processUnknownToken(FileWriter writer, String lexeme, int line, int column) throws IOException {
writer.write(String.format("%02d DES %s\n", line, lexeme));
}
}
