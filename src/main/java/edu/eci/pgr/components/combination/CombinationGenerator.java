package edu.eci.pgr.components.combination;
import java.io.Serializable;
import java.util.Random;


public class CombinationGenerator implements Serializable{
	private static final long serialVersionUID = 1L;

	protected CombinationDescription []values;
	protected long []initialValues;
	protected long []response;
	protected int []priorities;
	protected boolean firstTime;
	private static final int INCREMENT = 1;
	private static final int DECREMENT = -1;
	
	public CombinationGenerator(){
	}

	public void generateValues(long[] maxValues) {
		generateInitialValues(maxValues);
	}
	public void generateValues(Object[] maxValues) {
		long initValues[] = new long[maxValues.length];
		for(int i=0;i<initValues.length;i++)
			initValues[i] = ((Long)maxValues[i]).longValue();
		generateInitialValues(initValues);
	}

	private void generateInitialValues(long[] maxValues) {
		initialValues = initializeLongArray(maxValues.length);
		values = new CombinationDescription[maxValues.length];
		priorities = initializeArray(maxValues.length);
		firstTime = true;
		Random rn = new Random();
		for(int i=0;i<maxValues.length;i++){
			int r = rn.nextInt(maxValues.length);
			while(containsValue(priorities,r)){
				r = rn.nextInt(maxValues.length);
			}
			priorities[i] = r;
			initialValues[i] = new Random().nextInt((int)maxValues[i]);
			int d = new Random().nextInt(100);
			int direction = 0;
			if(d>=50)
				direction = INCREMENT;
			else
				direction = DECREMENT;
			values[i] = new CombinationDescription(maxValues[i],direction,initialValues[i]);
		}
	}
	public long[] getNextValues(){
		if(firstTime){
			firstTime = false;
			response = initialValues;
			return initialValues;
		}
		boolean gotResponse = false;
		int i = 0;
		while(!gotResponse){
			int pos = getPosForPriority(i);
			long nextValue = values[pos].getNextValue();
			if(!values[pos].isAtInitialValue()){
				response[pos] = nextValue;
				gotResponse = true;
			}else{
				response[pos] = values[pos].getInitialValue();
			}
			i++;
		}
		return response;
	}
	
	private int getPosForPriority(int p){
		for(int i=0;i<priorities.length;i++)
			if(priorities[i] == p)
				return i;
		return 0;
	}
	private int[] initializeArray(int max){
		int []array = new int[max];
		for(int i=0;i<max;i++)
			array[i] = -1;
		return array;
	}
	
	private long[] initializeLongArray(int max){
		long []array = new long[max];
		for(int i=0;i<max;i++)
			array[i] = -1;
		return array;
	}
	
	private boolean containsValue(int []vec, int val){
		boolean response = false;
		for (int i = 0; i < vec.length && !response; i++)
			if (vec[i] == val)
				response = true;
		return response;
	}
	
}
