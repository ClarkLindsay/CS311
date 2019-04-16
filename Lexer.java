import java.io.BufferedReader;
import java.io.FileReader;

public class Lexer {

  public static int pointer = 0;
  private static int position = 0;
  public static String input = "";
  public static String lexeme = "";
  private static int prevPointer = 0;
  public static int prevPosition = 0;
  public static String prevLexeme = "";
  public static String[] lines = new String[10000];
  public static int lineNum = 0;
  public static boolean flag = false;
  public static int linePoint = 0;

  public Lexer(String input){
    String filename = null;

    try {
      filename = input;
    }
    catch(Exception e){
      fail("Error: Please specify filename");
    }

		try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
			String currentLine;
      if ((currentLine = br.readLine()) != null){
        this.input = currentLine.trim();
        lines[0] = currentLine.trim();
      }
      int count = 1;
			while ((currentLine = br.readLine()) != null) {
				this.input = this.input.trim() + "\n" + currentLine.trim();
        lines[count] = currentLine.trim();
        count++;
			}
		} catch (Exception e) {
			fail("Error: Error reading file");
		}
  }

  public static boolean letter(char let){
    if (let == 'a' || let == 'b' || let == 'c' || let == 'd' || let == 'e' || let == 'f' || let == 'g' || let == 'h' || let == 'i'
    || let == 'j' || let == 'k' || let == 'l' || let == 'm' || let == 'n' || let == 'o' || let == 'p' || let == 'q' || let == 'r'
    || let == 's' || let == 't' || let == 'u' || let == 'v' || let == 'w' || let == 'x' || let == 'y' || let == 'z'
    || let == 'A' || let == 'B' || let == 'C' || let == 'D' || let == 'E' || let == 'F' || let == 'G' || let == 'H' || let == 'I'
    || let == 'J' || let == 'K' || let == 'L' || let == 'M' || let == 'N' || let == 'O' || let == 'P' || let == 'Q' || let == 'R'
    || let == 'S' || let == 'T' || let == 'U' || let == 'V' || let == 'W' || let == 'X' || let == 'Y' || let == 'Z'){
        return true;
    }
    else
      return false;
  }//end letter

  public static boolean digit(char dig){
    if (dig == '0' || dig == '1' || dig == '2' || dig == '3' || dig == '4' || dig == '5' || dig == '6' || dig == '7'
    || dig == '8' || dig == '9'){
        return true;
    }
    else
      return false;
  }//end digit

  public static boolean whiteSpace(char spc){
    if (spc == ' ' || spc == '\t' || spc == '\r')
      return true;
    else
      return false;
  }//end whiteSpace

  public static String next(){
    prevPointer = pointer;
    prevPosition = position;

    if (flag){
      lineNum++;
      linePoint = 0;
      flag = false;
    }

    char c = '#';
    String next = "";

    while (c != '\n'){
      try{
        c = nextChar(input, pointer);
      }
      catch (Exception e){break;}

      try{
        if (lines[lineNum].equals("")){
          lineNum++;
          linePoint = 0;
        }
      }
      catch (Exception e){
        lexeme = "end";
        break;
      }

      if (c == '/' && nextChar(input, pointer+1) == '/'){
        //if (){
          lineNum++;
          linePoint = 0;
        //}
        pointer = pointer + 2;
        c = nextChar(input, pointer);
        while (c != '\n'){
          c = nextChar(input, pointer);
          pointer++;
          linePoint++;
        }
        c = nextChar(input, pointer);
        if (c == '\n'){
          pointer++;
        }
        continue;
      }

      if (whiteSpace(c)){
        while (whiteSpace(c)){
          pointer++;
          linePoint++;
          c = nextChar(input, pointer);
        }
        if (input != "")
          break;
      }

      else {
        if (c != '\n')
          next = next + c;
        else
          lineNum++;
        pointer++;
        linePoint++;
        try {
          c = nextChar(input, pointer);
        }
        catch (Exception e){
          if (!next.equals("end"))
            fail("Error: Missing 'end' keyword");
        }
      }
    }
    prevLexeme = lexeme;
    lexeme = next;
    return next;
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
      type = "-";

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
          fail("Error at position " + position() + ": Identifiers can only contain letters, numbers, and '_'");
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
          fail("Error at position " + position() + ": Digits can only contain (0-9)");
        type = "NUM";
      }
    }

    else if (c == ' '){
      pointer--;
      next();
    }

    else {
      fail("Error at position " + (position() - point + 1) + ": Invalid character - " + c);
    }
    return type;
  }//end kind

  public static int position(){
    //int pos = position;
    prevPosition = position;
    position = position + lexeme.length();
    return (prevPosition);
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

  public static int getLine(){
    return lineNum;
  }

  public static int getLinePoint(){
    return linePoint;
  }

}//end Lexer
