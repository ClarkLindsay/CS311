public class Parser {

  public static Token previous;
  public static Token current;
  public static Token[] tokens = new Token[10000];
  public static Lexer lexer;
  public static Lexer first;
  public static int pos = 0;

  public static void main(String[] args){

    lexer = new Lexer(args[0]);

    lexer.next();
    current = new Token((lexer.position() + lexer.lexeme.length()), lexer.kind(), lexer.value(), lexer.getLine(), lexer.getLinePoint());
    tokens[0] = current;
    int count = 1;
    while (!current.kind.equals("end-of-text")){
      current = new Token((lexer.position() + lexer.lexeme.length()), lexer.kind(), lexer.value(), lexer.getLine(), lexer.getLinePoint());
      try{
        tokens[count] = current;
      }
      catch (Exception e){
        current.kind = "end-of-text";
        break;
      }
      lexer.next();
      count++;
    }

    try{
      getNext(0);
    }
    catch (Exception e){
      System.out.println("Error");
      System.out.println("Program must have more than just 'end' keyword");
      System.out.println("1 error");
      System.exit(1);
    }

    if (current.kind == "end-of-text"){
      System.out.println("Error");
      System.out.println("Program must have more than just 'end' keyword");
      System.out.println("1 error");
      System.exit(1);
    }

    Expression();
  }//end main

  public static boolean Expression(){
    if (BooleanExpression())
      return true;
    fail("Does not form valid Expression");
    return false;
  }//end Expression

  public static boolean BooleanExpression(){
    if (BooleanTerm()){
      while (current.kind == "OR"){
        //System.out.println("or");
        getNext(3);
        if (!BooleanTerm())
          return false;

        if (current.kind != "OR" && current.kind != ")" && current.kind != "end-of-text")
          fail("Expecting 'or'");
      }
      return true;
    }

    fail("Does not form valid BooleanExpression");
    return false;
  }//end BooleanExpression

  public static boolean BooleanTerm(){
    if (BooleanFactor()){
      getNext(4);
      while (current.kind == "AND"){
        //System.out.println("and");
        getNext(5);
        if (!BooleanFactor())
          return false;
        getNext(101);

        if (current.kind != "AND" && current.kind != ")" && current.kind != "end-of-text")
          fail("Expecting 'and'");
      }
      return true;
    }

    fail("Does not form valid BooleanTerm");
    return false;
  }//end BooleanTerm

  public static boolean BooleanFactor(){
    if (current.kind == "NOT"){
      //System.out.println("not");
      getNext(6);
      if (ArithmeticExpression()){
        if (current.kind != "=" && current.kind != "<" && current.kind != ")")
          getNext(200);
        if (current.kind == "=" || current.kind == "<"){
          //System.out.println("=<");
          getNext(77);
          if (ArithmeticExpression())
            return true;
          else
            fail("Expecting Arithemetic Expression");
        }
          return true;
      }
      else
        fail("Expecting Arithemetic Expression");
    }

    else if (ArithmeticExpression()){
      if (current.kind != "=" && current.kind != "<" && current.kind != ")")
        getNext(200);
      if (current.kind == "=" || current.kind == "<"){
        //System.out.println("=<");
        getNext(8);
        if (ArithmeticExpression())
          return true;
        else
          fail("Expecting ArithmeticExpression");
      }
      else
        getPrev(4);

      return true;
    }

    fail("Does not form valid BooleanFactor");
    return false;
  }//end BooleanFactor

  public static boolean ArithmeticExpression(){
    if (Term()){
      if (current.kind != ")")
        getNext(9);
      if ((current.kind == "+" || current.kind == "-")){
        while (current.kind == "+" || current.kind == "-"){
          //System.out.println("+-");
          getNext(10);
          if (!Term())
              fail("Expecting Term");
          getNext(100);

          if (current.kind != "+" && current.kind != "-" && current.kind != "*" && current.kind != "/" && current.kind != ")" && current.kind != "end-of-text"  && current.kind != "<" && current.kind != "="){
            fail("Expecting '+', '-', '*', '/'");
          }
        }
      }
      else
        getPrev(5);

      return true;
    }

    fail("Expecting '(', NUM, or ID");
    return false;
  }//end ArithmeticExpression

  public static boolean Term(){
    if (Factor()){
      getNext(11);
      if ((current.kind == "*" || current.kind == "/")){
        while (current.kind == "*" || current.kind == "/"){
          //System.out.println("*/");
          getNext(12);
          if (!Factor())
              fail("Expecting ID, NUM, or '('");
          getNext(105);

          if (current.kind != "*" && current.kind != "/" && current.kind != ")" && current.kind != "end-of-text" && current.kind != "<" && current.kind != "="){
            fail("Expecting '*', '/', ID, or NUM");
          }
        }
      }

      else
        getPrev(7);

      return true;
    }

    return false;
  }//end Term

  public static boolean Factor(){
    if (Literal())
      return true;

    else if (current.kind == "ID"){
      //System.out.println("ID");
      getNext(23);
      if (current.kind != "end-of-text" && current.kind != "*" && current.kind != "/" && current.kind != "+" && current.kind != "-" && current.kind != "OR" && current.kind != "AND" && current.kind != "(" && current.kind != ")" && current.kind != "=" && current.kind != "<")
        fail("Expecting '*', '/', '+', '-', 'or', or 'and'");
      else{
        getPrev(99);
        return true;
      }
    }

    else if (current.kind == "("){
      //System.out.println("(");
      getNext(13);
      if (Expression()){
        if (current.kind == ")"){
          //System.out.println(")");
          return true;
        }
        else{
          fail("Expecting ')'");
        }
      }
      else
        fail("Expecting Expression");
    }
    else
      getPrev(8);

    return false;
  }//end Factor

  public static boolean Literal(){
    if (BooleanLiteral())
      return true;

    else if (current.kind == "NUM"){
      //System.out.println("NUM");
      getNext(23);
      if (current.kind != "end-of-text" && current.kind != "*" && current.kind != "/" && current.kind != "+" && current.kind != "-" && current.kind != "OR" && current.kind != "AND" && current.kind != "(" && current.kind != ")" && current.kind != "=" && current.kind != "<")
        fail("Expecting '*', '/', '+', '-', 'or', or 'and'");
      else{
        getPrev(99);
        return true;
      }
    }

    return false;
  }//end Literal

  public static boolean BooleanLiteral(){
    if (current.kind == "FALSE" || current.kind == "TRUE"){
      //System.out.println("true/false");
      getNext(23);
      if (current.kind != "end-of-text" && current.kind != "*" && current.kind != "/" && current.kind != "+" && current.kind != "-" && current.kind != "OR" && current.kind != "AND" && current.kind != "(" && current.kind != ")" && current.kind != "=" && current.kind != "<")
        fail("Expecting '*', '/', '+', '-', 'or', or 'and'");
      else{
        getPrev(99);
        return true;
      }
    }

    return false;
  }//end BooleanLiteral

  public static void fail(String message){
    int space = 0;

    System.out.println("Error on line " + (current.line + 1) + ".");
    System.out.println(message);

    //System.out.println(current.line);

    for (int i = 0; i < 5; i++)
      System.out.print(" ");

    System.out.println(lexer.lines[current.line]);

    for (int i = 0; i < current.linePoint + 5; i++){
      System.out.print(" ");
    }

    System.out.println("^");

    System.out.println("1 error");
    System.exit(1);
  }//end fail

  public static void getNext(int i){
    try {
      pos++;
      current = tokens[pos];
      try{
        String temp = current.kind;
      }
      catch (Exception e){getPrev(-88);}
    }
    catch (Exception e){}
  }//end getNext

  public static void getPrev(int i){
    try {
      pos--;
      current = tokens[pos];
      //System.out.println("prev " + current.value + " " + current.kind + " " + i);
    }
    catch (Exception e){}
  }//end getPrev

}//end class
