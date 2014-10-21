package Ex3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test_Temperature {
	private static BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args){
		
			byte op=0;
			do{
			System.out.println("1. Pasar de grados Celsius a Kelvin");
			System.out.println("2. Pasar de grados Kelvin a Celsius");
			System.out.println("3. Salir");
			try{
			System.out.print("OP ==> ");
			op=Byte.parseByte(stdin.readLine());
			} catch (NumberFormatException e) {
				System.out.println(e+" Se espera un valor numerico del 1 al 3");
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(op){
			case 1:
				float grados=0;
				try{
					System.out.print("Grados Celsius => ");
					grados=Float.parseFloat(stdin.readLine());
					Temperature a=new Temperature(grados,'c');
					System.out.println("Son => "+a.convert('k', grados)+"ºK");
				}catch(NumberFormatException e){
					System.out.println(e+" Se espera un valor numerico del 1 al 3");
				} catch (IllegalTemperatureException e) {
					System.out.println(""+e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}				
				break;
			case 2:
				grados=0;
				try{
					System.out.print("Grados Kelvin => ");
					grados=Float.parseFloat(stdin.readLine());
					Temperature a=new Temperature(grados,'k');
					System.out.println("Son => "+a.convert('c', grados)+"ºC");
				}catch(NumberFormatException e){
					System.out.println(e+" Se espera un valor numerico del 1 al 3");
				} catch (IllegalTemperatureException e) {
					System.out.println(""+ e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("Bye");
				break;
				default:
					//nunca entrara pq esta controlado en el do while valores del 1 al 3
					break;
			}
	
			//Temperature a=new Temperature(10,'k');
			}while(op!=3);			
	}
}
