import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileIO {

	private BufferedWriter output_stream;
	private Map<String,Integer> cal=new HashMap<String,Integer>();
	private String dvrp[];
	private String cpy_lan[];
	private Map<String,Integer> dvmrp_cal=new HashMap<String,Integer>();
	int b=0;
	private BufferedReader read_me;
	
	
	int dvc=0;
	public FileIO(){}
	
public void gen_file(String f_name){
	File file=null;
		
						if(f_name!=null&&file==null&&!f_name.equals("RouterConfig"))
							try {
						
								file = new File(f_name);
									
										if(file.createNewFile());
							}
						
						
						catch (IOException e) {
								
							}

	
}

void readAndWriteFile(String readFrom, String writeTo,String protype,String key,int routNum)
{	
	int c =b;
	if(key!=null&&!key.startsWith("dvrouter"))
	{
		if(cal.get(key)==null)
			cal.put(key,b);
		else
			c=cal.get(key);
	}
		if(key!=null&&key.startsWith("dvrouter")){
			c=dvc;
			if(dvmrp_cal.get(key)==null)
				dvmrp_cal.put(key,dvc);
			else
			c=dvmrp_cal.put(key, dvc);
		}
	try
    {    			boolean prnt=true;
                String str = readFrom;
                BufferedReader read_and_write = new BufferedReader(new FileReader(str));
                int temp = 0;
                int x=0;
                while((str = read_and_write.readLine()) != null)
                {
				                    ++temp;
				                    if(!key.startsWith("dvrouter")&&temp >c) /* new msg */
				                    {	
				                    	if(!str.startsWith("data")&&(protype.equals("receiver")))
				                               prnt=false;   
				                    }
				                    ++temp;
				                    if(key.startsWith("dvrouter")&&temp >c)
				                    {
					                    if(protype.equals("router")&&str.startsWith("DV")&&str.charAt(5)!=routNum){
					                	dvrp[x]=str;x++;
					               		prnt=false;
					                }
					                if(protype.equals("router")&&key.startsWith("data_read")){
					                	cpy_lan[x]=str;
					                }
				                    
					                if(prnt){
				                    	
					                    String filePath = writeTo;
					                    
					                    BufferedWriter copy = new BufferedWriter(new FileWriter(filePath,true));
					                    copy.write(str);
					                    copy.write("\n");
					                    copy.close(); 
					                	
				                    }
                   }
               }read_and_write.close();
               b = temp;
               dvc=temp;
               if(key!=null){
            	   if(!key.startsWith("dvrouter"))
            		   cal.put(key,b);
            	   else
            		   dvmrp_cal.put(key,dvc);
               }
    }
    catch(Exception e)
    {    }
}


void writeFile(String message,String f_name)
{
	
    try
    {                              
    
			    String filePath = f_name;
			    BufferedWriter copy = new BufferedWriter(new FileWriter(filePath,true));
			    copy.write(message);
			    copy.write("\n");
			    copy.close();                       
			      
               
    }
    catch(Exception e)
    {
       
    }
}


public synchronized boolean writeMessage(String message){

	
	
	try{
		output_stream=new BufferedWriter(new FileWriter("RouterConfigured",true));
		}catch(FileNotFoundException fe){}
	catch (IOException e){}
	boolean write=false;
	try {
		output_stream.append(message);
		output_stream.newLine();
		output_stream.flush();
		write=true;
		output_stream.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return write;
}
public List<String> readRouterConfig(String f_name){
	String message="";
	List<String> configure = new ArrayList<String>();
	
	try{
		 String str = f_name;
         read_me = new BufferedReader(new FileReader(str));
         while((str = read_me.readLine()) != null)
         {
        	 message=str;
        	 configure.add(message);
         }
		}catch(FileNotFoundException fe){System.out.println("File :"+f_name +" does not exist");}
		catch (IOException e) {
		e.printStackTrace();
	}
	System.out.println("Deleting Router Config : "+new File("RouterConfig").delete());
	return configure;
}


public boolean close_str(){
	try {
		read_me.close();
		output_stream.close();
		return true;
	} catch (IOException e) {
		return false;
	}
}
public String[] dvrp(){
	return this.dvrp;
}
public String[] cpy_lan(){
	return this.cpy_lan;
}
}
