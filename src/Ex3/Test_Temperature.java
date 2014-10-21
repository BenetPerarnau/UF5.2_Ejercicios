package Ex3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test_Temperature {

	public static void main(String[] args){
		
			byte op=0;
			do{//entrara como minimo una vez y repite hasta dar con la op 3
			System.out.println("1. Pasar de grados Celsius a Kelvin");
			System.out.println("2. Pasar de grados Kelvin a Celsius");
			System.out.println("3. Salir");
			try{
			System.out.print("OP ==> ");
			BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
			op=Byte.parseByte(stdin.readLine());
			} catch (NumberFormatException e) {//si el valor que parseamos no es un entero, es capturado en este catch
				System.out.println(e+" Se espera un valor numerico tipo entero");
				Test_Temperature.main(args);//para que no vaya a parar al default del switch, ahorramos esto volviendo a llamar al main
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(op){
			case 1://Pasar de grados Celsius a Kelvin
				float grados;
				try{
					System.out.print("Grados Celsius => ");
					BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
					grados=Float.parseFloat(stdin.readLine());
					Temperature a=new Temperature(grados,'c');
					System.out.println("Son => "+a.convert('k', grados)+"ºK");
				}catch(NumberFormatException e){//si el valor que parseamos no es un entero, es capturado en este catch
					System.out.println(e+" Se espera un valor numerico tipo entero");
				} catch (IllegalTemperatureException e) {//si el valor de grados es superior a los limites entra en este catch
					System.out.println(""+e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}				
				break;
			case 2: //Pasar de grados Kelvin a Celsius
				try{
					System.out.print("Grados Kelvin => ");
					BufferedReader stdin=new BufferedReader(new InputStreamReader(System.in));
					grados=Float.parseFloat(stdin.readLine());
					Temperature a=new Temperature(grados,'k');
					System.out.println("Son => "+a.convert('c', grados)+"ºC");
				}catch(NumberFormatException e){//si el valor que parseamos no es un entero, es capturado en este catch
					System.out.println(e+" Se espera un valor numerico tipo entero");
				} catch (IllegalTemperatureException e) {//si el valor de grados es superior a los limites entra en este catch
					System.out.println(""+ e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("Bye");
				break;
				default:
					System.out.println("Se espera un valor numerico del 1 al 3.");
					break;
			}
			}while(op!=3);
	}
}
