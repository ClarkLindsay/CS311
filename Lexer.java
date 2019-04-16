import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {

  private static int pointer = 0;
  private static int position = 1;
  private static String input = "";
  private static String lexeme = "";

  public static void main(String[] args){

    String filename = null;

    try {
      filename = args[0];
    }
    catch(Exception e){
      fail("Error: Please specify filename");
    }

		try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
			String currentLine;
      if ((currentLine = br.readLine()) != null)
        input = currentLine;
			while ((currentLine = br.readLine()) != null) {
				input = input + "\n" + currentLine;
			}
		} catch (IOException e) {
			fail("Error: Error reading file");
		}

    next();
    while (!kind().equals("end-of-text")){
      System.out.println("<" + position() + ", " + kind() + ", " + value() + ">");
      position = position + lexeme.length()+1;
      next();
    }
   }//end main

  public static boolean letter(char let){
    if (let == 'a' || let == 'b' || let == 'c' || let == 'd' || let == 'e' || let == 'f' || let == 'g' || let == 'h' || let == 'i'
    || let == 'j' || let == 'k' || let == 'l' || let == 'm' || let == 'n' || let == 'o' || let == 'p' || let == 'q' || let == 'r'
    || let == 's' || let == 't' || let == 'u' || let == 'v' || let == 'w' || let == 'x' || let == 'y' || let == 'z'
    || let == 'A' || let == 'B' || let == 'C' || let == 'D' || let == 'E' || let == 'F' || let == 'G' || let == 'H' || let == 'I'
    || let == 'J' || let == 'K' || let == 'L' || let == 'M' || let == 'N' || let == 'O' || let == 'P' || let == 'Q' || let == 'R'
    || let == 'S' || let == 'T' || let == 'U' || let == 'V' || let == 'W' || let == 'X' || let == 'Y' || let == 'Z'){
        return true;
    }
    else {
      return false;
    }
  }//end letter

  public static boolean digit(char dig){
    if (dig == '0' || dig == '1' || dig == '2' || dig == '3' || dig == '4' || dig == '5' || dig == '6' || dig == '7'
    || dig == '8' || dig == '9'){
        return true;
    }
    else {
      return false;
    }
  }//end digit

  public static String next(){
    char c = '#';
    boolean loop = true;
    String next = "";

    while (loop == true){
      try {
        c = nextChar(input, pointer);
        pointer++;
      }
      catch (Exception e){
        c = ' ';
        break;
      }
      if (c == ' ')
        break;

      else if (c == '\n'){
        c = nextChar(input, pointer);
        if (c == '/'){
          c = nextChar(input, pointer);
          if (c == '/'){
            pointer = pointer+2;
            while (c != '\n'){
                c = nextChar(input, pointer);
                pointer++;
            }
          }
        }
        break;
      }

      else if (c == '/'){
        c = nextChar(input, pointer);
        if (c == '/'){
          pointer++;
          while (c != '\n'){
            c = nextChar(input, pointer);
            pointer++;
          }
          pointer++;
        }
        break;
      }

      else
        next = next + c;

      try {
        c = nextChar(input, pointer);
      }
      catch (Exception e){
        if (!next.equals("end"))
          fail("Error: Missing 'end' keyword");
      }
      lexeme = next;
    }
    return lexeme;
  }//end next

  public static String kind(){
    String type = "";
    int point = 0;
    char c = '#';
    boolean loop = true;

    try {
      c = nextChar(lexeme, point);
      point++;
    }
    catch (Exception e){
      c = ' ';
    }

    if (lexeme.equals("begin"))
      type = "BEGIN";

    else if (lexeme.equals("end"))
      type = "end-of-text";

    else if (lexeme.equals("bool"))
      type = "BOOL";

    else if (lexeme.equals("int"))
      type = "INT";

    else if (lexeme.equals(";"))
      type = ";";

    else if (lexeme.equals(":="))
      type = ":=";

    else if (lexeme.equals("if"))
      type = "IF";

    else if (lexeme.equals("fi"))
      type = "FI";

    else if (lexeme.equals("then"))
      type = "THEN";

    else if (lexeme.equals("else"))
      type = "ELSE";

    else if (lexeme.equals("while"))
      type = "WHILE";

    else if (lexeme.equals("do"))
      type = "DO";

    else if (lexeme.equals("od"))
      type = "OD";

    else if (lexeme.equals("print"))
      type = "PRINT";

    else if (lexeme.equals("or"))
      type = "OR";

    else if (lexeme.equals("and"))
      type = "AND";

    else if (lexeme.equals("not"))
      type = "NOT";

    else if (lexeme.equals("="))
      type = "=";

    else if (lexeme.equals("<"))
      type = "<";

    else if (lexeme.equals("+"))
      type = "+";

    else if (lexeme.equals("-"))
      type = "+";

    else if (lexeme.equals("*"))
      type = "*";

    else if (lexeme.equals("/"))
      type = "/";

    else if (lexeme.equals("("))
      type = "(";

    else if (lexeme.equals(")"))
      type = ")";

    else if (lexeme.equals("true"))
      type = "TRUE";

    else if (lexeme.equals("false"))
      type = "FALSE";

    else if (letter(c) == true){
      while (c != ' '){
        try {
          c = nextChar(lexeme, point);
          point++;
        }
        catch (Exception e){
          c = ' ';
          break;
        }
        if (letter(c) != true && digit(c) != true && c != '_')
          fail("Error at position " + (position() + point - 1) + ": Identifiers can only contain letters, numbers, and '_'");
      }
      type = "ID";
    }

    else if (digit(c) == true){
      try {
        c = nextChar(lexeme, point);
      }
      catch (Exception e){
        c = ' ';
      }
      if (c == ' ')
      type = "NUM";

      while (c != ' '){
        try {
          c = nextChar(lexeme, point);
          point++;
        }
        catch (Exception e){
          c = ' ';
          break;
        }
        if (digit(c) != true)
          fail("Error at position " + (position() + point - 1)+ ": Digits can only contain (0-9)");
        type = "NUM";
      }
    }

    else if (c == ' '){
      pointer--;
      next();
    }

    else {
      fail("Error at position " + position() + ": Invalid character");
    }
    return type;
  }//end kind

  public static int position(){
    return (position);
  }//end position

  public static String value(){
    if (kind().equals("NUM") || kind().equals("ID"))
      return lexeme;
    return null;
  }//end value

  public static void fail(String error){
    System.out.println(error);
    System.exit(1);
  }//end fail

  public static char nextChar(String input, int point){
    char next = input.charAt(point);
    return next;
  }//end nextChar
}//end Lexer
