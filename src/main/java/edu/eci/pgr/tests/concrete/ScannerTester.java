package edu.eci.pgr.tests.concrete;

import java.util.ArrayList;

import edu.eci.pgr.components.ScannerComponent;
import edu.eci.pgr.components.ScannerComponent.InvalidAmountError;
import edu.eci.pgr.exceptions.ConnectionException;
import edu.eci.pgr.exceptions.InstantException;
import edu.eci.pgr.exceptions.RetrieverException;
import edu.eci.pgr.persistence.Facade;

public class ScannerTester {
	 public static void main(String[] args) {
		 try {
			ArrayList<ScannerComponent.InvalidAmountError> list=(ArrayList<InvalidAmountError>) 
			ScannerComponent.getInstance().verrifyInsertAmount(Facade.getInstance().getDatabase());
			System.out.println("ERRORS: ");
			for(ScannerComponent.InvalidAmountError ae: list){
				System.out.println(ae.getMessage());
			}
		 } catch (InstantException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (RetrieverException e) {
			e.printStackTrace();
		} 
		 
	}
}
