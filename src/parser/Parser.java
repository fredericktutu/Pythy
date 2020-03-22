/* Generated By:JavaCC: Do not edit this line. Parser.java */
package pythy.parser;
import java.io.*;
import java.util.Stack;
import java.util.ArrayList;
import java.lang.Number;

public class Parser implements ParserConstants {
    public int last_level = 0;
    public int line_number = 0;
    public Stack<Statement_AST> stack_stmt;
    public ArrayList<Statement_AST> array_stmt;
    public ArrayList<Statement_AST> set_stmt;

    public ArrayList<Statement_AST> get_statement_list() {
        return this.array_stmt;
    }
    public void fill(ArrayList<Statement_AST> set_stmt, int m) {
        for(Statement_AST stmt : set_stmt) {
            if(stmt.type == "while" || stmt.type =="if") {
                stmt.out = m;
            } else {
                stmt.next = m;
            }
        }
    }
    public void fillAlgorithm(int down) {
        Statement_AST x;
        if(down > 0) {
            set_stmt.clear();
            set_stmt.add(array_stmt.get(line_number - 1));
            while(down > 0) {
                x = stack_stmt.pop();
                down = down - 1;
                if(x.type == "if") {
                    set_stmt.add(x);
                    if(down == 0) {
                        fill(set_stmt, line_number);
                    }
                } else if(x.type == "while"){
                    fill(set_stmt, x.line);
                    if(down == 0) {
                        x.out = line_number;
                    } else {
                        set_stmt.clear();
                        set_stmt.add(x);
                    }
                }
            }
        }
    }

  final public ArrayList<Statement_AST> Program() throws ParseException {
    stack_stmt = new Stack();
    array_stmt = new ArrayList();
    array_stmt.add(null);
    set_stmt = new ArrayList();
    int down;
    int current_level = 0;
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TAB:
      case NEWLINE:
      case IF:
      case NAME:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      Oneline();
    }
        line_number ++;
        current_level = 0;
        down = last_level - current_level;
        fillAlgorithm(down);
        array_stmt.add(new Statement_AST("null")); //the last
        {if (true) return array_stmt;}
    throw new Error("Missing return statement in function");
  }

  final public void Oneline() throws ParseException {
    Statement_AST ast;
    int down;
    Statement_AST x;
    down = Indentation();
    ast = Statement();
        line_number ++;
        ast.line = line_number;

        if(ast.type == "while" || ast.type == "if") { //设置in和next，若是while或if则进栈
            ast.in = line_number + 1;
            stack_stmt.push(ast);
        } else {
            ast.next = line_number + 1;
        }

        array_stmt.add(ast); // 把语句加入语句数组

        fillAlgorithm(down);
  }

  final public int Indentation() throws ParseException {
    int current_level = 0;
    int down;
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TAB:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      jj_consume_token(TAB);
           current_level++;
    }
        if(current_level > last_level) {
            //不可能出现这种情况,报错
            down = -1;
        } else if (current_level == last_level) {
            down = 0;
        } else {
            down = current_level - last_level;
            last_level = current_level;
        }
        {if (true) return down;}
    throw new Error("Missing return statement in function");
  }

  final public Statement_AST Statement() throws ParseException {
    Statement_AST ast;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NEWLINE:
      ast = Empty_Statement();
                            {if (true) return ast;}
      break;
    case NAME:
      ast = Assign_Statement();
                              {if (true) return ast;}
      break;
    case IF:
      ast = If_Statement();
                           {if (true) return ast;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Statement_AST Empty_Statement() throws ParseException {
    Statement_AST ast;
    jj_consume_token(NEWLINE);
        ast = new Statement_AST("null");
        System.out.println("a null statement detected");
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Statement_AST Assign_Statement() throws ParseException {
    Statement_AST ast;
    Token t;
    Expression_AST ast2;
    t = jj_consume_token(NAME);
    jj_consume_token(21);
    ast2 = Expression();
    jj_consume_token(NEWLINE);
        ast = new Statement_AST("=");
        ast.left = t.image;
        ast.right = ast2;
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Statement_AST If_Statement() throws ParseException {
    Statement_AST ast;
    jj_consume_token(IF);
    Expression();
    jj_consume_token(22);
    jj_consume_token(NEWLINE);
        ast = new Statement_AST("if");
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Statement_AST Where_Statement() throws ParseException {
    Statement_AST ast;
    jj_consume_token(WHERE);
    Expression();
    jj_consume_token(22);
    jj_consume_token(NEWLINE);
        ast = new Statement_AST("where");
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Statement_AST Else_Statement() throws ParseException {
    Statement_AST ast;
    jj_consume_token(ELSE);
    jj_consume_token(22);
        ast = new Statement_AST("else");
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Expression() throws ParseException {
    Expression_AST ast;
    ast = Bool_Expression();
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Bool_Expression() throws ParseException {
    Expression_AST ast;
    ast = Or_Expression();
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Or_Expression() throws ParseException {
    Expression_AST ast;
    Expression_AST ast_left;
    Expression_AST ast_right;
    ast_left = And_Expression();
                               ast = ast_left;
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OR:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_3;
      }
      jj_consume_token(OR);
      ast_right = And_Expression();
                                    ast = new Expression_AST("or");
                                    ast.left = ast_left;
                                    ast.right = ast_right;
                                    ast_left = ast;
    }
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST And_Expression() throws ParseException {
    Expression_AST ast;
    Expression_AST ast_left;
    Expression_AST ast_right;
    ast_left = Not_Expression();
                               ast = ast_left;
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_4;
      }
      jj_consume_token(AND);
      ast_right = Not_Expression();
                                    ast = new Expression_AST("and");
                                    ast.left = ast_left;
                                    ast.right = ast_right;
                                    ast_left = ast;
    }
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Not_Expression() throws ParseException {
    int count = 0;
    Expression_AST ast_ret;
    Expression_AST ast_ret2;
    Expression_AST ast;
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NOT:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_5;
      }
      jj_consume_token(NOT);
               count += 1;
    }
    ast = Compare_Expression();
        if(count == 0) {
            {if (true) return ast;}
        } else if(count % 2 == 1) {
            ast_ret = new Expression_AST("not");
            ast_ret.left = ast;
            {if (true) return ast_ret;}
        } else {
            ast_ret2 = new Expression_AST("not");
            ast_ret2.left = ast;
            ast_ret = new Expression_AST("not");
            ast_ret.left = ast_ret2;
            {if (true) return ast_ret;}
        }
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Compare_Expression() throws ParseException {
    Expression_AST ast;
    Expression_AST ast_left;
    Expression_AST ast_right;
    ast_left = Arithmatic_Expression();
                                          ast = ast_left;
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 23:
        jj_consume_token(23);
        ast_right = Arithmatic_Expression();
                            ast = new Expression_AST("==");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case 24:
        jj_consume_token(24);
        ast_right = Arithmatic_Expression();
                            ast = new Expression_AST(">=");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case 25:
        jj_consume_token(25);
        ast_right = Arithmatic_Expression();
                            ast = new Expression_AST(">");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case 26:
        jj_consume_token(26);
        ast_right = Arithmatic_Expression();
                            ast = new Expression_AST("!=");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case 27:
        jj_consume_token(27);
        ast_right = Arithmatic_Expression();
                            ast = new Expression_AST("<=");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case 28:
        jj_consume_token(28);
        ast_right = Arithmatic_Expression();
                            ast = new Expression_AST("<");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Arithmatic_Expression() throws ParseException {
    Expression_AST ast;
    ast = Additive_Expression();
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Additive_Expression() throws ParseException {
    Expression_AST ast;
    Expression_AST ast_left;
    Expression_AST ast_right;
    ast_left = Multive_Expression();
                                       ast = ast_left;
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ADD:
      case SUB:
        ;
        break;
      default:
        jj_la1[8] = jj_gen;
        break label_7;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ADD:
        jj_consume_token(ADD);
        ast_right = Multive_Expression();
                            ast = new Expression_AST("+");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case SUB:
        jj_consume_token(SUB);
        ast_right = Multive_Expression();
                            ast = new Expression_AST("-");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Multive_Expression() throws ParseException {
    Expression_AST ast;
    Expression_AST ast_left;
    Expression_AST ast_right;
    ast_left = Primary_Expression();
                                        ast = ast_left;
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIME:
      case DIV_INT:
      case DIV_FLOAT:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_8;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIME:
        jj_consume_token(TIME);
        ast_right = Primary_Expression();
                            ast = new Expression_AST("*");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case DIV_INT:
        jj_consume_token(DIV_INT);
        ast_right = Primary_Expression();
                            ast = new Expression_AST("//");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      case DIV_FLOAT:
        jj_consume_token(DIV_FLOAT);
        ast_right = Primary_Expression();
                            ast = new Expression_AST("/");
                            ast.left = ast_left;
                            ast.right = ast_right;
                            ast_left = ast;
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
        {if (true) return ast;}
    throw new Error("Missing return statement in function");
  }

  final public Expression_AST Primary_Expression() throws ParseException {
    Token t;
    Expression_AST ast;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NAME:
      t = jj_consume_token(NAME);
        String s = t.image;
        ast = new Expression_AST("name");
        ast.value_name = s;
        {if (true) return ast;}
      break;
    case INT:
      t = jj_consume_token(INT);
        int i = Integer.parseInt(t.image);
        ast = new Expression_AST("int");
        ast.value_int = i;
        {if (true) return ast;}
      break;
    case FLOAT:
      t = jj_consume_token(FLOAT);
        float f = Float.parseFloat(t.image);
        ast = new Expression_AST("float");
        ast.value_float = f;
        {if (true) return ast;}
      break;
    case BOOL:
      t = jj_consume_token(BOOL);
        boolean b;
        System.out.println("boolean");
        if(t.image.equals("True")) {
            b = true;
        } else {
            b = false;
        }
        ast = new Expression_AST("bool");
        ast.value_boolean = b;
        {if (true) return ast;}
      break;
    case LP:
      jj_consume_token(LP);
      ast = Expression();
      jj_consume_token(RP);
        {if (true) return ast;}
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x4001c,0x4,0x40018,0x100,0x80,0x200,0x1f800000,0x1f800000,0xc00,0xc00,0x7000,0x7000,0x1e8000,};
   }

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[29];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 29; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
