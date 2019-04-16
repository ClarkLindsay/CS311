public class Token {

  public int position;
  public String kind;
  public String value;
  public int line;
  public int linePoint;

  public Token(int position, String kind, String value, int line, int linePoint){
    this.position = position;
    this.kind = kind;
    this.value = value;
    this.line = line;
    this.linePoint = linePoint;
  }
}
