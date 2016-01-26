/*
 * Receiver reports to the Router in it's LAN that its a reveciver
 * receiver lan_id
 * EVERY SECOND RECEIVER CHECKS the LANX for the data message and copy them in hinX
 * 
 * Data (startUp,Period)--->Lan--->router reads from the LAN
 * data lan_id host_lan_id
 * 
 * 
 */

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.*;
public class Host {

	private static final String SENDER = "sender";
	private static final String RECIEVER = "receiver";
	private static String lan_x;
	private static String hout;
	private static String hinx;
	private static int timetostart;
	private static int period;
	private static int host_id;
	private static int lan_id;
	private static String type;
	private static FileIO create_file;
	
	public Host(){}

	
	public void setLan_id(int lan_id) {
		this.lan_id = lan_id;
	}
	
	public String getType() {
		return type;
	}
	public void setHost_id(int host_id) {
		this.host_id = host_id;
	}
	public int getLan_id() {
		return lan_id;
	}
	public int getHost_id() {
		return host_id;
	}
	
	public void setType(String type) {
		if(type.equals(SENDER)||type.equals(RECIEVER))
		this.type = type;
		else try{
			throw new MyProcessException("A host has to be sender or receiver.");
		}catch(MyProcessException pe){}
		}
	public int getTime_to_start_host() 
		{return timetostart;}

	public void setTime_to_start_host(int timetostart) 
		{this.timetostart = timetostart;}
	public int getperiod() 
		{return period;}
	public void setperiod(int period) 
		{this.period = period;}	
	public String toString()
		{return " Host";}
	
	
	public static void main(String[] args){
		
		String[] splt=null;
		
		if(args[0]!=null)
		{ 
			splt=args[0].split(" ");
		host_id=Integer.parseInt(splt[1]);
		lan_id=Integer.parseInt(splt[2]);
		type=splt[3];
		if(type.equals(SENDER))
		{
		timetostart=Integer.parseInt(splt[4]);
		period=Integer.parseInt(splt[5]);
		}
		hinx="hin"+host_id;
		hout="hout"+host_id;

	create_file=new FileIO();
	lan_x="lan"+lan_id;
	
	create_file.gen_file(hinx);
	create_file.gen_file(hout);
	create_file.gen_file(lan_x);
			
	try
			
	{
	if(type.equals("sender"))
	{	
	Thread.sleep(timetostart*1000);
	for(int i=1;i<100;i++)
	{   
	create_file.writeFile("data "+lan_id+" "+host_id,hout);
	Thread.sleep(period*1000);					
		}
						
	}
				
	else if(type.equals("receiver")){
	create_file.writeFile("reciever "+lan_id,hout);
	   for(int i=1;i<100;++i)
	          {	    
							   		Thread.sleep(1000);		
									
							   		create_file.readAndWriteFile(lan_x,hinx,"receiver",null,-1);
							     	
							   		if(i%10==0)
									create_file.writeFile("reciever "+lan_id,hout);
								    }
								   
							   
							   create_file.close_str();
								   }
		else
		{System.exit(0);
				}
	    }
	    catch(Exception e)
	       {
		            
	      }
		    
		 }
	}
}
