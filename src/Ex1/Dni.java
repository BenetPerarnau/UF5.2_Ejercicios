package Ex1;

import java.io.IOException;

public class Dni {
	
	public static boolean correct(String dni)throws NullPointerException, NumberFormatException, StringIndexOutOfBoundsException,IllegalArgumentException{
		
		boolean valor=false;
		if(dni.length()==0){
			throw new NullPointerException();
			}
		else if(dni.length()!=9){
			valor=false;
			throw new StringIndexOutOfBoundsException();
		}else if((dni.charAt(dni.length()-1))<65 ||(dni.charAt(dni.length()-1))>90){
			valor=false;
			throw new IllegalArgumentException();
		}else{
			int i=0;
			while(dni.charAt(i)>47 && dni.charAt(i)<58){
				i++;
			}
			if(i==8){
				valor=true;
				}else{
				valor=false;
				throw new NumberFormatException();
			}
		}
		return valor;		
	}
}
