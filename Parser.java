public class Parser {

  public static int pointer = 0;

  public static void main(String[] args){

    Token relToken = new Token();
    String id = "test";
    String input = "int_3";

    //id = relop(input);
    //System.out.println(id);
    //System.out.println(identifier(input));
    //System.out.println(integerLiteral("12343489"));
    keyword("true");

  }//end main

  public static String relop(String input){
    int state = 0;
    char c = '#';
    boolean loop = true;

    while (loop == true){
      switch (state){
        case 0:
          try {
            c = nextChar(input);
          }
          catch (Exception e){}
          if (c == '<')
            state = 1;
          else if (c == '=')
            state = 5;
          else if (c == '>')
            state = 6;
          else {
            fail(0);
            loop = false;
          }
        break;

        case 1:
          try {
            c = nextChar(input);
          }
          catch (Exception e){
            c = ' ';
          }
          if (c == ' ')
            state = 4;
          else if (c == '=')
            state = 2;
          else if (c == '>')
            state = 3;
          else {
            fail(1);
            loop = false;
          }
        break;

        case 2:
          try {
            c = nextChar(input);
          }
          catch (Exception e){
            c = ' ';
          }
          if (c == ' ')
            return "<=";
          else
            fail(2);
          loop = false;
        break;

        case 3:
          try {
            c = nextChar(input);
          }
          catch (Exception e){
            c = ' ';
          }
          if (c == ' ')
            return "<>";
          else
            fail(3);
          loop = false;
        break;

        case 4:
          try {
            c = nextChar(input);
          }
          catch (Exception e){
            c = ' ';
          }
        if (c == ' ')
          return "<";
        else
          fail(4);
        loop = false;
        break;

        case 5:
          try {
            c = nextChar(input);
          }
          catch (Exception e){
            c = ' ';
          }
        if (c == ' ')
          return "=";
        else
          fail(5);
        loop = false;
        break;

        case 6:
          try {
            c = nextChar(input);
          }
          catch (Exception e){
            c = ' ';
          }
          if (c == '='){
            try {
              c = nextChar(input);
            }
            catch (Exception e){
              c = ' ';
            }
            if (c == ' ')
              return ">=";
            else
              fail(62);
          }
          else if (c == ' ')
            return ">";
          else
            fail(6);
          loop = false;
        break;
      }//end switch
    }//end while
    return "error";
  }//end relop

  public static boolean letter(char let){
    if (let == 'a' || let == 'b' || let == 'c' || let == 'd' || let == 'e' || let == 'f' || let == 'g' || let == 'h' || let == 'i'
    || let == 'j' || let == 'k' || let == 'l' || let == 'm' || let == 'n' || let == 'o' || let == 'p' || let == 'q' || let == 'r'
    || let == 's' || let == 't' || let == 'u' || let == 'v' || let == 'w' || let == 'x' || let == 'y' || let == 'z'){
      //System.out.println("let");
        return true;
    }//end if
    else {
            //System.out.println(let);
      return false;
    }
  }//end letter

  public static boolean digit(char dig){
    if (dig == '0' || dig == '1' || dig == '2' || dig == '3' || dig == '4' || dig == '5' || dig == '6' || dig == '7'
    || dig == '8' || dig == '9'){
        //System.out.println("dig");
        return true;
    }//end if
    else {
            //System.out.println(dig);
      return false;
    }
  }//end digit

  public static String integerLiteral(String str){
    String integer = "";
    char c = '#';
    boolean loop = true;

    while (c != ' '){
      try {
        c = nextChar(str);
      }
      catch (Exception e){
        c = ' ';
        break;
      }
      if (digit(c) != true)
        fail(8);
      integer = integer + c;
    }//end while
    return integer;
  }//end integerLiteral

  public static String keyword(String str){
    int state = 0;
    char c = '#';
    String key = "";
    boolean loop = true;

    try {
      c = nextChar(str);
    }
    catch (Exception e){
      c = ' ';
    }

    while (loop == true){
      switch(state) {
        case 0:
          if (letter(c) == true)
            state = 1;
          else if (c == '=')
            state = 2;
          else if (c == '<')
            state = 3;
          else if (c == '*')
            state = 4;
          else if (c == '/')
            state = 5;
          else if (c == '+')
            state = 6;
          else if (c == '-')
            state = 7;
          else if (c == ' ')
            state = 8;
        break;

        case 1:
          if (c == 'f'){
            try {
              c = nextChar(str);
            }
            catch (Exception e){
              c = ' ';
            }
            if (c == 'a'){
              try {
                c = nextChar(str);
              }
              catch (Exception e){
                c = ' ';
              }
              if (c == 'l'){
                try {
                  c = nextChar(str);
                }
                catch (Exception e){
                  c = ' ';
                }
                if (c == 's'){
                  try {
                    c = nextChar(str);
                  }
                  catch (Exception e){
                    c = ' ';
                  }
                  if (c == 'e'){
                    System.out.println("false");
                    loop = false;
                  }
                }
              }
            }
          }//end outer if
          else if (c == 't'){
            try {
              c = nextChar(str);
            }
            catch (Exception e){
              c = ' ';
            }
            if (c == 'r'){
              try {
                c = nextChar(str);
              }
              catch (Exception e){
                c = ' ';
              }
              if (c == 'u'){
                try {
                  c = nextChar(str);
                }
                catch (Exception e){
                  c = ' ';
                }
                if (c == 'e'){
                  System.out.println("true");
                  loop = false;
                }
              }
            }
          }//end outer if
        break;
      }//end switch
  }//end while
    return key;
  }//end keyword

  public static String identifier(String str){
    String id = "";
    char c = '#';
    boolean loop = true;

    try {
      c = nextChar(str);
    }
    catch (Exception e){
      c = ' ';
    }

    if (letter(c) != true)
      fail(8);
    else {
      id = id + c;
    }

    while (c != ' '){
      try {
        c = nextChar(str);
      }
      catch (Exception e){
        c = ' ';
        break;
      }
      if (letter(c) != true && digit(c) != true && c != '_')
        fail(8);
      id = id + c;
    }//end while
    return id;
  }//end identifier

  public static void fail(int errorID){
    System.out.println("Error: Invalid entry" + errorID);
    System.exit(1);
  }//end fail

  public static char nextChar(String input){
    char next = input.charAt(pointer);
    pointer++;
    return next;
  }

  public static char prevChar(String input){
    pointer--;
    char next = input.charAt(pointer);
    return next;
  }

}//end class
