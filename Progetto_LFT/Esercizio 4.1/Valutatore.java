import java.io.*;
public class Valutatore {
    private LexerDue lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(LexerDue l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
	if (look.tag == t) {
	    if (look.tag != Tag.EOF) move();
	} else error("syntax error");
    }

    public void start() {
		switch(look.tag){
			case '(':
			case Tag.NUM:
				int S_v=expr();
				match(Tag.EOF);
				System.out.println(S_v);
				break;
			default:
				error("Errore nello start");
		}
    }

    private int expr() {
	// E->TE' unica produzione
		switch(look.tag){
			case '(':
			case Tag.NUM:{
				int T_val=term();
				int E_val=exprp(T_val);
				return E_val;
			}
			default:{
				error("Errore nell'expr");
				return -100;
			}
		}
    }

	//Er_val=valore ereditato
    private int exprp(int Er_val) {
	switch (look.tag) { //ritorno 
		case '+':{
			match('+');
			int T_val=term();
			int Ex_val=exprp(Er_val+T_val);
			return Ex_val;
		}
		case '-':{
			match('-');
			int T_val=term();
			int Ex_val=exprp(Er_val-T_val);
			return Ex_val;
		}
			
		case Tag.EOF:
		case ')':{return Er_val;}
			
		default:{
			error("Errore in exprp");
			return -100;
		}	
	
		}
    }

    private int term() {
		// T->FT' unica produzione
		switch(look.tag){
			case '(':
			case Tag.NUM:{
				int F_val=fact();
				int T_val=termp(F_val);
				return T_val;
			}
			
			default:{
				error("Errore nel term");
				return -100;
			}
		}
    }

    private int termp(int Te_val) {
       switch (look.tag) {
		case '*':{
			match('*');
			int F_val=fact();
			int T_val=termp(Te_val*F_val);
			return T_val;
		}
		case '/':{
			match('/');
			int F_val=fact();
			int T_val=termp(Te_val/F_val);
			return T_val;
		}
			
		case Tag.EOF:
		case Tag.NUM:
		case ')':
		case '+':
		case '-':{
			return Te_val;
		}
		default:{
			error("Errore in termp");
		}
		return Te_val;
	
		}
    }

    private int fact() {
		switch(look.tag){
			case '(':{
				match('(');
				int F_s=expr();
				match(')');
				return F_s;
			}
			
			case Tag.NUM:{
				//salvo l'intero prima di matchare il tag del numero perchè se lo mettessi dopo non riconoscerebbe più il numero dato che dopo il match viene fatto avanzare il Lexer
				int i=((NumberTok)look).value; //cast per poter accedere al valore del NumberTok relativo al numero riconosciuto
				match(Tag.NUM);
				return i;
			}
			
			default:
				error("Errore nel fact");
				return -100;
		}
        
    }
		
    public static void main(String[] args) {
        LexerDue lex = new LexerDue();
        String path = "Espressione_6.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}