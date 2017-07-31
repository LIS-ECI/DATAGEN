/**
 * 
 */
package edu.eci.pgr.components;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Observable;

import edu.eci.pgr.i18n.MessageBundleManager;
import edu.eci.pgr.view.ViewConstants;

/**
 * @author felipe
 *
 */
public class ControlProcessComponent extends Observable implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final int CONTROLPOINTPERCENTAGE = 10;
	//the percentage to process
	private double amount;
	private double process;
	private double add;
	private String messages;
	/** time in seconds**/
	private double time;
	/** the actual control point**/
	private int actualPoint;
	private NumberFormat nf;
	
	public ControlProcessComponent(){
		this.process=0;
		this.amount=0;
		this.messages=""; //$NON-NLS-1$
		this.actualPoint = 0;
		this.nf = new DecimalFormat ("##.##"); //$NON-NLS-1$
	}
	
	public ControlProcessComponent (double total){
		this.amount=total;
		this.process=0;
		this.messages=""; //$NON-NLS-1$
		this.actualPoint = 0;
		this.nf = new DecimalFormat ("##.##"); //$NON-NLS-1$
	}
	
	public void setProcess(double proc){
		double currentTime = ((double)Calendar.getInstance().getTimeInMillis()/1000)-this.time; 
		if(this.process>=CONTROLPOINTPERCENTAGE*this.actualPoint){
			if(this.actualPoint==0){
				this.time=Calendar.getInstance().getTimeInMillis()/1000;
				currentTime = 0;
			}
			this.addMessages(MessageBundleManager.getString("ControlProcessComponent.0")+(int)proc+MessageBundleManager.getString("ControlProcessComponent.1") +nf.format(currentTime)+"\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			this.actualPoint++;
		}
		this.process=proc;
		this.setChanged();
		this.notifyObservers(this.process);
	}
	
	public double getProcess(){
		return process;
	}
	
	public void addToProcess(double add){
		this.add=add;
		this.setProcess((this.getProcess()+((add*100)/amount)));
	}
	
	public void resetProcess(){
		this.setProcess(0);
	}
	
	public void setMessages(String messages) {
		this.messages = messages;
		this.setChanged();
		this.notifyObservers(this.messages);
	}
	
	public String getMessages() {
		return messages;
	}
	
	public void addMessages(String mess){
		this.setMessages (this.getMessages()+"\n>"+mess); //$NON-NLS-1$
	}
	
	public void setAmount(double newamount){
		this.amount=newamount;
		this.setProcess(((this.add*100)/this.amount));
	}
	
	public boolean isFinished(){
		return process>=100;
	}
	
	public void exportToFile(String Filename) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter ((Filename)));
		bw.write(ViewConstants.LOGFILETIITTLE+"\n"); //$NON-NLS-1$
		bw.write(this.messages);
		bw.close();
	}
}
