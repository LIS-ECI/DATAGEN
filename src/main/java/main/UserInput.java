package main;
//=======================================================================
//ESCUELA COLOMBIANA DE INGENIERIA
//Archivo: Entrada.java
//Fecha Creacíon: 
//=======================================================================


//=======================================================================
//BIBLIOTECAS REQUERIDAS
//=======================================================================

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//=======================================================================
//CLASE ENTRADA
//=======================================================================

public class UserInput {

//-----------------------------------------------------------------------
//METODOS
//-----------------------------------------------------------------------
	
/**
* Definicion: Obtener el dato ingresado por el usuario
* Precondicion: Existir entrada
* Postcondicion: El sistema obtiene el daro y lo utiliza
*/
	
	public static String dato(){
		
		String dato = "";
		
	try{
		InputStreamReader iStream = new InputStreamReader(System.in);
		BufferedReader buffer = new BufferedReader(iStream);
		dato = buffer.readLine();
	}
	catch(IOException e){
		System.err.println("Error: " + e.getMessage());
	}
	return dato;
	
}

/**
* Definicion: Obtener el dato ingresado por el usuario
* Precondicion: Existir entrada
* Postcondicion: El sistema obtiene el daro y lo utiliza
*/

	public static int leerInt(String msn){
		
		System.out.print(msn + "");
	try{
 		return Integer.parseInt(dato());
	}
	catch(NumberFormatException e){
		return Integer.MIN_VALUE;
	}
	
	}

/**
* Definicion: Obtener el dato ingresado por el usuario
* Precondicion: Existir entrada
* Postcondicion: El sistema obtiene el daro y lo utiliza
*/

	public static long leerLong(String msn){
		
		System.out.print(msn + "");
	try{
		return Long.parseLong(dato());
	}
	catch(NumberFormatException e){
 		return Long.MIN_VALUE;
	}
	
	}

/**
* Definicion: Obtener el dato ingresado por el usuario
* Precondicion: Existir entrada
* Postcondicion: El sistema obtiene el daro y lo utiliza
*/

	public static float leerFloat(String msn){
		
		System.out.print(msn + "");
	try{
 		Float f = new Float(dato());
 		return f.floatValue();
	}
	catch(NumberFormatException e){
		return Float.NaN;
	}
	
	}

/**
* Definicion: Obtener el dato ingresado por el usuario
* Precondicion: Existir entrada
* Postcondicion: El sistema obtiene el daro y lo utiliza
*/

	public static double leerDouble(String msn){
		
		System.out.print(msn + "");
	try{
 		Double d = new Double(dato());
 		return d.doubleValue();
	}
	catch(NumberFormatException e){
 		return Double.NaN;
	}
	
	}

/**
* Definicion: Obtener el dato ingresado por el usuario
* Precondicion: Existir entrada
* Postcondicion: El sistema obtiene el daro y lo utiliza
*/

	public static String leerString(String msn){
		
		System.out.print(msn + "");
		String d = new String(dato());
		
		return d;	
		
	}

}