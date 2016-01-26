import java.util.ArrayList;
import java.util.List;

public class Controller {

	private  String[] host;
	private  String[] router;
	private  String[] lan;
	private static FileIO io;
	private static final String HOST="host";
	private static final String ROUTER="router";
	private static final String LAN="lan";
	
	
	public Controller() {
		io=new FileIO();
		
	}
		
	public String[] getlan() 
	{return lan;}
	
	public void sethost(String[] host) 
	{this.host = host;}
	public String[] getrouter() 
	{return router;}
	public String[] gethost() 
	{return host;}
	
	public void setrouter(String[] router) 
	{this.router = router;}
    
	public void setlan(String[] lan) 
		{this.lan = lan;}
	

	public static void main(String[] args){
		
			try{
			Thread.sleep(1000);
		}catch(InterruptedException ie){}
		
		int i=0,j=0,k=0;
		Controller c=new Controller();
		
		
		String[] splt=null;
		
		String protype="";
		splt=args[0].split(" ");
		
		for(int a=0;a<splt.length;a++){ 
							
			if(splt[a].equals("host")||splt[a].equals("router")||splt[a].equals("lan")){
									protype=splt[a];
										a++;
									}
								if(protype.equals("host")){
									if(c.host==null)c.host=new String[splt.length];
									
										c.host[i]=splt[a];
									
									i=i+1;
								}
												if(protype.equals("router")){
													if(c.router==null)c.router=new String[splt.length];
													c.router[j]=splt[a];
													j=j+1;
												}
								
								
								
								
								if(protype.equals("lan")){
									if(c.lan==null)c.lan=new String[splt.length];
									c.lan[k]=splt[a];
									k=k+1;
								}
										
							}
		int tot_nodes=(i+j+k);
		List<String> configure=new ArrayList<String>();
		configure = io.readRouterConfig("RouterConfig");
		List<String[]> adjLan = new ArrayList<String[]>(configure.size());
		
				for(int q=0;q<configure.size();q++)
								{
					System.out.println("...."+configure.get(q));
				adjLan.add(q,new String[0]);	
								}
								
								for(int q=0;q<configure.size();q++)
								{
									String[] x =configure.get(q).toString().split(" ");
									
									adjLan.add(Integer.parseInt(x[0]),x);
									
								}
		
for(int rt=0;rt<100;rt++)
{
try {
	Thread.sleep(1000);
						for(int fl=0;fl<i;fl++){
									io.readAndWriteFile("hout"+c.host[fl],"lan"+c.lan[fl],"controller","hout"+c.host[fl],-1);}
								
								for(String re[]:adjLan)
								          {	
										for(int ll=1;ll<re.length;ll++)
											{	
												io.readAndWriteFile("rout"+re[0], "lan"+re[ll],"controller","rout"+re[0],-1);
											}
								}
					
		} catch (InterruptedException e) {
				
io.close_str();
				
e.printStackTrace();
			}
		
		
		
		}
	}

}
