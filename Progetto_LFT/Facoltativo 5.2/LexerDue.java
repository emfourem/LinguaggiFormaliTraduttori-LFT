import java.io.*; 
import java.util.*;
//Lexer per esercizio 2.2
public class LexerDue {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r'|| peek== '/') {
			if(peek== '/'){
				readch(br);
				if(peek== '/'){ //ho riconosciuto l'inizio di un commento
					//leggo ed elimino i caratteri letti finchè non trovo un a capo o un EOF
					while(peek!='\n'&& peek!=(char)-1){
						peek= ' ';
						readch(br);
					}
					//scorro la stringa dopo il commento finchè non vado a capo o arrivo all'end of file
				}else if(peek== '*'){
						//Qui ho riconosciuto l'apertura di un commento "/*"
						int state = 0;
						peek= ' ';
						readch(br);
						/*
						Leggo il primo carattere dopo l'apertura e entro nel while 
						Ciclo finchè:
						-sono in uno stato che non sia finale(ovvero state!=2)
						-leggo caratteri che non siano l'EOF
						
						
						*/
						
						
						while (state!=2 &&peek!=(char)-1) {

							switch (state) {
							//Ho già letto /* quando entro nel while quindi con un asterisco mi muovo verso una chiusura(verso lo stato 1) altrimenti resto nello stato zero e leggo il commento
							case 0:
							if (peek=='*') {    
								state=1;
							}else{
								state=0;
							}
							break;
									
							
							//Arrivo qui in questa condizione /*......* e posso o leggere uno '/' e quindi ho terminato o continuare a leggere
							case 1:
							if (peek=='*'){     
								state=1;
							}else if (peek=='/') {    
								state=2;
							}else{
								state=0;
							}
							break;		
							
							}
							//vado avanti con la lettura del commento finchè non entro nello stato 2 ovvero stato finale
							if(state!=2){
							peek= ' ';
							readch(br);
							}
							//se sono nello stato finale posso uscire perchè ho riconosciuto apertura e chiusura del commento--->commento corretto
						}
					if(state!=2){
						//se non sono in uno stato finale allora non ho trovato una chiusura dopo una apertura quindi stampo un messaggio di errore
						System.err.println("Erroneus comment");
						return null;
					}
					}else{
					return Token.div;
				}
			}
			//se  non ho uno slash gestisco il resto normalmente affidando il 'controllo' ai vari case
			if (peek == '\n'){ line++;}
			//leggo il carattere e vado a riconoscere quello che la sequenza successiva mi identifica
            readch(br);
        }
		
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
				
			case '(':
				peek= ' ';
				return Token.lpt;

				
			
			case ')':
				peek= ' ';
				return Token.rpt;
				
			
			case '{':
				peek= ' ';
				return Token.lpg;
				
			case '}':
				peek= ' ';
				return Token.rpg;    
				
				
			
			case '+':
				peek= ' ';
				return Token.plus;
			
			
			case '-':
				peek= ' ';
				return Token.minus;
				
			
			case '*':
				peek= ' ';
				return Token.mult;
				
			case ';':
				peek= ' ';
				return Token.semicolon;
			
			case ',':
				peek= ' ';
				return Token.comma;
				
			
			
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }
				
			 case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }
				
				
					
			 case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if(peek=='>') {
					peek = ' ';
                    return Word.ne;
					
				}else{
					return Word.lt;
				}
				
				/*
				else if(peek==' '||Character.isDigit(peek)){
					peek= ' ';
					return Word.lt;
				}else{
                    System.err.println("Erroneous character"
                            + " after < : "  + peek );
                    return null;
                }*/
				
						
			 case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                }else{
					return Word.gt;
				}
				/* else if (peek==' '||Character.isDigit(peek)){
					peek= ' ';
					return Word.gt;
				}else{
                    System.err.println("Erroneous character"
                            + " after > : "  + peek );
                    return null;
                }*/
				
				case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
				}else{
                    System.err.println("Erroneous character"
                            + " after = : "  + peek );
                    return null;
                }
					
          
            case (char)-1:
                return new Token(Tag.EOF);

            default:
			  if ((Character.isLetter(peek))|| (peek == '_')) {
					//se ho una lettera
					//allora il primo carattere è una lettera e quindi sono dentro questo caso
				  		String s="";
						while(Character.isLetter(peek)||Character.isDigit(peek)||peek== '_'){ //caratteri eventualmente seguiti da numeri
							s=s+peek;
							peek = ' '; //devo metterlo sempre o solo alla fine? solo nel while,fuori ti farebbe saltare dei token
							readch(br);
						}
						if(s.equals("assign")){
							return Word.assign;	
						}else if(s.equals("to")){
							return Word.to;	
						}else if(s.equals("if")){
							return Word.iftok;	
						}else if(s.equals("else")){
							return Word.elsetok;	
						}else if(s.equals("while")){
							return Word.whiletok;	
						}else if(s.equals("begin")){
							return Word.begin;	
						}else if(s.equals("end")){
							return Word.end;	
						}else if(s.equals("print")){
							return Word.print;	
						}else if(s.equals("read")){
							return Word.read;	
						}else{
							int state = 0;
							int i = 0;

							while (state >= 0 && i < s.length()) {
								final char ch = s.charAt(i++);

								switch (state) {
								case 0:
								if ((ch>=65&&ch<=90)||(ch>=97 &&ch<=122))     //lettera
									state = 2;
								else if (ch == 95)     //underscore
									state = 1;
								else
									state = -1;
								break;

								case 1:
								if ((ch>=65&&ch<=90)||(ch>=97 &&ch<=122)||(ch >=48&&ch<=57))
									state = 2;
								else if (ch == 95)     
									state = 1;
								else
									state = -1;
								break;

								case 2:
								if ((ch>=65&&ch<=90)||(ch>=97 &&ch<=122)||(ch >=48&&ch<=57)||(ch == 95) )
									state = 2;
								else
									state = -1;
								break;
								
								}	
							}
							if(state==2){
								return new Word(Tag.ID,s);
							}else{
								 System.err.println("Erroneous identifier");
                        		return null;
							}
                		}	
		


                } else if (Character.isDigit(peek)) {
						String s="";//non dobbiamo accettare numeri che iniziano con sequenze arbitrarie di 0? O supponiamo che non vengano inseriti?
						while(Character.isDigit(peek)){ //numeri
							s=s+peek;
							peek = ' ';
							readch(br);
						}
				  		if(Character.isLetter(peek)){
							 System.err.println("Erroneous identifier");
							return null;
						}
						//ho tolto un peek= ' '; perchè saltavo il carattere dopo il numero se questo non fosse diviso da uno spazio
						//es: 2) mi stampava solo <256,2><-1> senza la parentesi ma senza peek va bene
				  
				  		
						//int p=Integer.parseInt(s);
				  		int lun=s.length()-1;
				  		int ris=0;
				  		int i=0;
				  		while(lun>=0){
							final char ch = s.charAt(i++);
							int p= ch - '0';  //per ottenere l'intero sfrutto l'ascii
							ris=ris+(p*((int)Math.pow(10,lun))); //cast di math.pow a int perchè restituisce un double altrimenti
							lun--;	
						}
							
							
						return new NumberTok(Tag.NUM,ris);

                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
         }
    }
		
    public static void main(String[] args) {
        LexerDue lex = new LexerDue();
        String path = "/home/michele/Documenti/lft/File123.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}


