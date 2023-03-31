public class  Esercizio7{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;
	
	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
		if (ch == 'M' )     
			state=1;   
		else
			state=8;
		break;
				
	    case 1:
		if (ch=='i')
			state=2;
		else
			state=9;
		break;

	    case 2:
		if (ch=='c')      
			state=3;
		else
			state=10;
		break;

	    case 3:
		if (ch=='h')     
		    state=4;
		else
			state=11;
		break;
				
		case 4:
		if (ch=='e')     
		    state=5;
		else
			state=12;
		break;
		
		case 5:
		if (ch=='l')    
			state=6;
		else
			state=13;
		break;
	    
		
		case 6:
		state=7;  
		break;
	    
	
		case 7:
		state=-1;
		break;
		
		case 8:
		if (ch=='i')     //lettera minuscola
			state=9; 
		else
			state=-1;
		break;
			
		case 9:
		if (ch=='c')     //lettera minuscola
			state=10; 
		else
			state=-1;
		break;
			
		case 10:
		if (ch=='h')     //lettera minuscola
			state=11; 
		else
			state=-1;
		break;
			
		case 11:
		if (ch=='e')     //lettera minuscola
			state=12; 
		else
			state=-1;
		break;
			
		case 12:
		if (ch=='l')     //lettera minuscola
			state=13; 
		else
			state=-1;
		break;
			
		case 13:
		if (ch=='e')     //lettera minuscola
			state=7; 
		else
			state=-1;
		break;
				

	    }
		
		
	}
	return state==7;
    }

    public static void main(String[] args)
    {
	System.out.println(scan("michele") ? "OK" : "NOPE");
	System.out.println(scan("Michele") ? "OK" : "NOPE");
	System.out.println(scan("MIchele") ? "OK" : "NOPE");
	System.out.println(scan("Michel ") ? "OK" : "NOPE");
	System.out.println(scan("M chele") ? "OK" : "NOPE");
	System.out.println(scan("Michel%") ? "OK" : "NOPE");
	System.out.println(scan("Michete") ? "OK" : "NOPE");
	System.out.println(scan("Michelee") ? "OK" : "NOPE");
	System.out.println(scan("M chele ") ? "OK" : "NOPE");
	System.out.println(scan("michel ") ? "OK" : "NOPE");
	System.out.println(scan("mIchele") ? "OK" : "NOPE");
	System.out.println(scan("michel") ? "OK" : "NOPE");
	System.out.println(scan("Michel") ? "OK" : "NOPE"); 
	System.out.println(scan("MICHELE") ? "OK" : "NOPE");
	System.out.println(scan("aschele") ? "OK" : "NOPE");
	System.out.println(scan("Michioe") ? "OK" : "NOPE");
	System.out.println(scan("a") ? "OK" : "NOPE");
    }
}