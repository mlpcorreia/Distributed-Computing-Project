package serverSide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import comInf.HostessStates;
import comInf.Logger;
import comInf.PassengerStates;
import comInf.PilotStates;
import comInf.SimulPar;

/**
 * This class represents the creation of the log file.
 * There is a internal buffer where the contents are written.
 */
public class LoggerImpl implements Logger{
	
	private Lock mutex;								// mutual exclusion lock
	private BufferedWriter bw;						// Buffer
	private PilotStates pilotState;					// Different states of the Pilot
	private HostessStates hostessState;				// Different states of the Hostess 
	private PassengerStates passengerState[];		// Different states of the Passengers
	private String filename;						// Name of the log file
	private int nFlight;							// Flight number
	private int InQ, InF, toB;						// Number of passenger in queue, Number of passengers in flight, Total number of passengers that already performed boarding
	private String finalText;						// Log final text
	
	/**
	 * Constructs the Logger implementation
	 * @param filename File to be saved name
	 */
	public LoggerImpl(String filename){
		this.filename = filename;
		mutex = new ReentrantLock();
		try {
			bw = new BufferedWriter(new FileWriter(new File(this.filename)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		finalText="";
		nFlight = 1;
		InQ=0;
		InF=0;
		toB=0;
		pilotState = PilotStates.FB;
		hostessState = HostessStates.WFF;
		passengerState = new PassengerStates[SimulPar.NPASS];
		
		for(int i = 0;i < SimulPar.NPASS;i++){
			passengerState[i] = PassengerStates.GTA;
		}
		
		saveHeader();
	}
	
	/**
	 * Set the pilot state and save it in the log file
	 * @param state	Pilot State
	 */
	@Override
	public void setStatePilot(PilotStates state){
		mutex.lock();
		
		pilotState = state;
		saveState();
		
		mutex.unlock();
	}
	
	/**
	 * Set the hostess state and save it in the log file
	 * @param state Hostess State
	 */
	@Override
	public void setStateHostess(HostessStates state){
		mutex.lock();
		
		hostessState = state;
		saveState();
		
		mutex.unlock();
	}
	
	/**
	 * Set the passenger state and save it in the log file
	 * @param id Passenger Identification
	 * @param state Passenger State
	 */
	@Override
	public void setStatePassenger(int id, PassengerStates state){
		mutex.lock();
		
		passengerState[id] = state;
		
		if(state == PassengerStates.AD){
			InF--;
			toB++;
		}
		else if(state == PassengerStates.IQ)
			InQ++;
		else if(state == PassengerStates.IF){
			InF++;
			InQ--;
		}
		saveState();
		
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file the header of the Air Lift Problem
	 */
	private void saveHeader(){
		mutex.lock();
		
		String tmp = String.format("%3s %4s %4s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s " +
				"%3s %3s %3s %3s %3s %3s %3s %3s %4s %3s %3s","PT","HT","P00","P01", "P02", "P03", "P04", "P05", "P06", "P07", "P08", "P09", "P10", "P11", "P12", "P13", "P14", "P15", "P16", "P17", "P18", "P19", "P20", "InQ", "InF", "toB");
		
		String tmp2 = String.format("%3s %4s %4s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s " +
				"%3s %3s %3s %3s %3s %3s %3s %3s %4s %3s %3s",pilotState,hostessState,passengerState[0],passengerState[1], passengerState[2], passengerState[3], passengerState[4], passengerState[5], passengerState[6], passengerState[7], passengerState[8], passengerState[9], passengerState[10], passengerState[11],  passengerState[12], passengerState[13], passengerState[14], passengerState[15], passengerState[16], passengerState[17], passengerState[18], passengerState[19], passengerState[20], InQ, InF, toB);
		
		
		try {
			bw.write("				Air Lift - Description of the internal state\n");
			bw.newLine();
			bw.write(tmp+"\n");
			bw.write(tmp2+"\n");
		} catch (IOException e) {}
		
		mutex.unlock();
	}
	
	/**
	 * Saves the current states of the all entities involved in the Air Lift Problem
	 */
	private void saveState(){
		mutex.lock();
		
		String tmp = String.format("%3s %4s %4s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s %3s " +
				"%3s %3s %3s %3s %3s %3s %3s %3s %4s %3s %3s",pilotState,hostessState,passengerState[0],passengerState[1], passengerState[2], passengerState[3], passengerState[4], passengerState[5], passengerState[6], passengerState[7], passengerState[8], passengerState[9], passengerState[10], passengerState[11],  passengerState[12], passengerState[13], passengerState[14], passengerState[15], passengerState[16], passengerState[17], passengerState[18], passengerState[19], passengerState[20], InQ, InF, toB);

		try {
			bw.write(tmp+"\n");
		} catch (IOException e) {}
		
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file that the boarding started
	 */
	@Override
	public void boardingStarted(){
		mutex.lock();
		
		try {
			bw.newLine();
			bw.write("Flight "+ nFlight +" : boarding started.\n");
		} catch (IOException e) {}
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file the passenger that got is passport checked by the hostess
	 * @param id Passenger Identification
	 */
	@Override
	public void passengerChecked(int id){
		mutex.lock();
		
		try {
			bw.newLine();
			bw.write("Flight "+ nFlight +" : Passenger "+ id + " checked.\n");
		} catch (IOException e) {}
		
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file the number of passengers that left in the plane to the destination
	 */
	@Override
	public void departedWith(){
		mutex.lock();
		
		try {
			bw.newLine();
			bw.write("Flight "+ nFlight +" : Departed with  "+ InF + " passengers.\n");
		} catch (IOException e) {}
		
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file that the plane arrived at the destination
	 */
	@Override
	public void arrived(){
		mutex.lock();
		
		finalText+="\nFlight "+nFlight+" transported "+InF+" passengers";
		try {
			bw.newLine();
			bw.write("Flight "+nFlight+" : arrived\n");
		} catch (IOException e) {}
		
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file that the plane is returning to the airport
	 */
	@Override
	public void returning(){
		mutex.lock();
		
		try {
			bw.newLine();
			bw.write("Flight "+nFlight+" : returning\n");
		} catch (IOException e) {}
		
		nFlight++;
		
		mutex.unlock();
	}
	
	/**
	 * Saves in the log file the final results of the Air Lift problem and closes the buffer 
	 */
	@Override
	public void logEnd(){
		mutex.lock();
		
		if(bw != null)
			try {
				bw.newLine();
				bw.write("Airlift sum up:"+finalText+".");
				bw.close();
			} catch (IOException e) {}
		else
			bw = null;
			
		mutex.unlock();
	}
}
