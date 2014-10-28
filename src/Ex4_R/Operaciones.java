package Ex4_R;

import java.util.ArrayList;
import java.util.Map;

public class Operaciones {
	
	public static boolean isdni(String dni){
		boolean aux=false;
		if(dni.length()!=9){//tiene que tener 9 caracteres
			aux=false;
			}else if((dni.charAt(dni.length()-1))>90 ||(dni.charAt(dni.length()-1))<65){//mirar si el ultimo es letra
			aux=false;
			}else{//mirar que los 8 primeros sean numeros
				int i=0;
				do{
					i++;
				}while(dni.charAt(i)>47 && dni.charAt(i)<58);
				if(i!=8){
					aux=false;
				}else{
					aux=true;
				}
			}
		
		return aux;
	}
	public static Cliente isCliente(Map<String,Cliente> array, String dni){
		boolean aux=false;
		Cliente a=null;;
		
		a=array.get(dni);
		
		return a;
	}

}
