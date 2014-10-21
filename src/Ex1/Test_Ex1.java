package Ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test_Ex1 {

	private static BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) {
		boolean valor=false;
		do{
		try{
			
			System.out.print("Introduce un DNI (12345678?)=> ");
			String input=stdin.readLine();
			
				valor=Dni.correct(input.toUpperCase());

		}catch(NullPointerException e){
			System.out.println(e+" No se ha introducido ningun caracter por teclado");
		}catch(NumberFormatException e){
			System.out.println(e+" El dni tine que tener 8 numeros.");
		}catch(StringIndexOutOfBoundsException e){ 
			System.out.println(e+" El dni tine que tener 9 caracteres");
		}catch(IllegalArgumentException e){
			System.out.println(e+" El dni tine que terminar en un caracter de la A-Z");
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		if(valor==false){
			System.out.println("Vuelve a intentarlo");
		}
		
	}while(valor==false);
		try {
			stdin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
}
