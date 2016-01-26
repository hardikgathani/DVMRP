import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class Router {
	private static FileIO io;
	private static Integer[][] dv;
	private static int router_id;
	private static String rout_file;
	private static String[] lans;	
	
	public Router() {
		
	}
	
	public Router(int router_id,String[] lans){
		
		this.router_id=router_id;
		this.lans=lans;
		rout_file="rout".concat(String.valueOf(router_id));
		io=new FileIO();
		io.gen_file(rout_file);
		String configMsg=String.valueOf(router_id);
		int i=-1,p=0;
		for(String lan:lans){
			dv[p][0]=Integer.parseInt(lan);
			for(int k=1;k<11;k++){
						if(k==router_id)
								{i=0;}	
				
							dv[p][k]=i;p++;
									}
			configMsg=configMsg+" "+lan;
			if(lan!=null)
			io.gen_file("lan"+lan);
		}
		io.writeMessage(configMsg);
	}

	public String[] getLans(){return lans;}
		public void setLans(String[] lans) 	{this.lans = lans;}
		public int getRouter_id() 	{return router_id;}
		public void setRouter_id(int router_id) 
		{this.router_id = router_id;}
		public static void main(String[] args){
		String[] splt=null;
		splt=args[0].split(" ");
		String[] routerNum=new String[splt.length-2];
		int router_id=Integer.parseInt(splt[1]);
		for(int j=0;j<routerNum.length;j++)
						{routerNum[j]=splt[j+2];}
		
		new Router(router_id,routerNum);
		for(String lan:lans)
		io.writeFile("DV "+" "+lan+" "+router_id+" 0 0 -1 1 -1 2 -1 3 -1 4 -1 5 -1 6 -1 7 -1 8 -1 9","rout"+String.valueOf(router_id));
		for(int x=0;x<10;x++){
			for(Integer[] dvmrp:dv)
			{  	
				updateDV();
				io.writeFile("DV"+" "+String.valueOf(dvmrp[0])+" "+router_id+" "+String.valueOf(dvmrp[1])+" "+String.valueOf(dvmrp[2])+" "+String.valueOf(dvmrp[3])
					+" "+String.valueOf(dvmrp[4])+" "+String.valueOf(dvmrp[5])+" "+String.valueOf(dvmrp[6])+" "+String.valueOf(dvmrp[7])+" "+String.valueOf(dvmrp[8])+" "+String.valueOf(dvmrp[9]), "rout"+String.valueOf(router_id));
			}
		}
		
		for(int time=0;time<100;time++){
			try{
				Thread.sleep(1000);
				for(String lan:lans){
					io.readAndWriteFile("lan"+lan, null, "router", "data_read"+String.valueOf(router_id)+lans, -1);
				}
				String[] lanData=io.cpy_lan();
				String[][] d=new String[lanData.length][];
				int ii=0;
				for(String data:lanData){
					d[ii] = data.split(" ");
					ii++;
				}
				
				for (int kk=0;kk<lanData.length-1;kk++){
					if(Integer.parseInt(d[kk][1])<Integer.parseInt(d[kk+1][1])){
						io.writeFile("data "+d[kk][1], "rout"+router_id);
					}
				}
				if(time%5==0)
				for(Integer[] dvmrp:dv)
				{  	
					updateDV();
					io.writeFile("DV"+" "+String.valueOf(dvmrp[0])+" "+String.valueOf(dvmrp[1])+" "
					+String.valueOf(dvmrp[2])+" "+String.valueOf(dvmrp[3])
						+" "+String.valueOf(dvmrp[4])+" "+String.valueOf(dvmrp[5])+" "+String.valueOf(dvmrp[6])+" "
					+String.valueOf(dvmrp[7])+" "+String.valueOf(dvmrp[8])+" "+String.valueOf(dvmrp[9]), "rout"+
						String.valueOf(router_id));
				}
			}catch(InterruptedException ie){ie.printStackTrace();}
		}
		}
		
	public static void updateDV(){
		for(String lan:lans)
			io.readAndWriteFile("lan"+lan, null, "router", "dvrouter"+router_id, router_id);
		String[] dvrp_tk=io.dvrp(); //Get DV strings from the LANX
		String[][] dvrp_udp=new String[dv.length+1][]; 
		int i=0;
		for(String distanceVector:dvrp_tk){
			dvrp_udp[i]=distanceVector.split(" ");
			for(Integer[] l_in:dv){
			if(l_in[0]==Integer.parseInt(dvrp_udp[i][1]))
			if(dv[l_in[0]][2]>Integer.parseInt(dvrp_udp[i][3])+1){
				dv[l_in[0]][2]=(Integer.parseInt(dvrp_udp[i][3])+1);
			}
			if(dv[l_in[0]][3]>Integer.parseInt(dvrp_udp[i][4])+1)
			{dv[l_in[0]][3]=Integer.parseInt(dvrp_udp[i][4])+1;}
			
			if(dv[l_in[0]][5]>Integer.parseInt(dvrp_udp[i][6])+1)
			
			{dv[l_in[0]][5]=Integer.parseInt(dvrp_udp[i][6])+1;}
			
			if(dv[l_in[0]][6]>Integer.parseInt(dvrp_udp[i][7])+1)
			{dv[l_in[0]][6]=Integer.parseInt(dvrp_udp[i][7])+1;}
			
			if(dv[l_in[0]][4]>Integer.parseInt(dvrp_udp[i][5])+1)
			{dv[l_in[0]][4]=Integer.parseInt(dvrp_udp[i][5])+1;}
			
			if(dv[l_in[0]][8]>Integer.parseInt(dvrp_udp[i][9])+1)
			
			
			{dv[l_in[0]][8]=Integer.parseInt(dvrp_udp[i][9])+1;}
			
			
			if(dv[l_in[0]][7]>Integer.parseInt(dvrp_udp[i][8])+1)
			{dv[Integer.parseInt(dvrp_udp[i][2])][7]=Integer.parseInt(dvrp_udp[i][8])+1;}
			i++;
			}
		}
		
	}
	
}
