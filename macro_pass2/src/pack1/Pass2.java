package pack1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

class Pass2_implementaion{
	FileReader pass1;
	FileReader sp;
	FileWriter modified;
	BufferedReader brpass1;
	BufferedReader brsp;
	BufferedWriter bwoutput;
	String stsp, stpass1;
	String temp[];
	String temp1;
	String mdt[][];
	String mnt[][];
	int mntc,mdtc;
	int[] start_end;
	int flag;
	String ALA[][];
	int index;
	int k;
	public Pass2_implementaion(){
		try{
			sp = new FileReader("/home/ankit/sem_6_prac/macro_pass1_input.txt"); // source program
			pass1 = new FileReader("/home/ankit/sem_6_prac/Macro_Pass1_output.txt");
			modified = new FileWriter("/home/ankit/sem_6_prac/Macro_Pass2_output.txt");
			brsp = new BufferedReader(sp);
			brpass1 = new BufferedReader(pass1);
			bwoutput = new BufferedWriter(modified);
			mdt = new String[40][1];
			mnt = new String[10][2];
			ALA = new String[20][2];
			k =0 ; //used in modify_ala()
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	void fetch_MDT(){
		mdtc=1;
		try{
			
			stpass1 = brpass1.readLine();
			stpass1 = brpass1.readLine();
			while(true){
				stpass1 = brpass1.readLine();
				if(stpass1.equals("MACRO NAME TABLE")){
					break;
				}
				else{
					temp = stpass1.split("\\s+", 2);
					mdt[mdtc++][0] = temp[1];
				}
			}
			/*for(int i=0; i< mdtc ;i++){
				System.out.println(mdt[i][0]);
			}*/
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	void fetch_MNT(){
		try{
			mntc=1;
			stpass1 = brpass1.readLine();
			stpass1 = brpass1.readLine();
		//store macro name in dict (macroname , mdtc)
			while(true){
				if(stpass1.equals("ARGUMENT LIST ARRAY")){
					// stpass1 points to ala contents
					break;
				}
				temp = stpass1.split("\\s+");
				//System.out.println(temp[2]);
				mnt[mntc][0] = temp[1];
				mnt[mntc++][1] = temp[2];
				stpass1 = brpass1.readLine();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	void fetch_ALA(){
		try{
			index=0;
			stpass1 = brpass1.readLine();
			while(true){
				stpass1 = brpass1.readLine();
				if(stpass1 == null)
					break;
				else{
					temp = stpass1.split("\\s+", 2);
					ALA[index][0] = temp[0];
					ALA[index++][1] =temp[1];
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	void read_sp(){
		try{
			//go till start
			while(true){
				stsp = brsp.readLine();
				bwoutput.write(stsp+"\n");
				if(stsp.equals("START")){
					break;
				}
			}
			
			while((stsp = brsp.readLine())!= null){
				if(stsp.equals("MACRO")){
					//skip till macro end
					do{
						bwoutput.write(stsp+"\n");
						stsp = brsp.readLine();
					}while(!stsp.equals("MEND"));
					bwoutput.write(stsp+"\n");
				}
				else{
					int s,e;
					temp = stsp.split("\\s+" );
					for(int i =0 ;i < temp.length ;i++){
						flag = 0;
						//search in mnt if fount return mdtc else -1
						start_end = find(temp[i]);
						if(start_end[0] == -1)
							continue;
						else{
							//make changes in ala and display on console
							modify_ala(temp, temp[i]);//temp[i] is the macro name
							//expand sp
							flag = 1;
							s = start_end[0]+1;
							if(start_end[1] == -1)
								e = mdtc;
							else
								e = start_end[1];
							//System.out.println(e);
							for(int j=s ; j < e;j++){
								bwoutput.write(mdt[j][0] + "\n");
								//System.out.println(mdt[j][0]);
							}
							
							break;
						}
						
					}
					if(flag == 0){
						bwoutput.write(stsp+"\n");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	void modify_ala(String[]temp, String macroname){
		for(int i=0 ; i <temp.length;i++){
			if(temp[i].equals(macroname))
				continue;
			ALA[k++][1] = temp[i];
		}
	}
	int[] find(String token){
		int[] start_end = new int[]{-1,-1};
		for(int i =1 ;i < mntc; i++){
			if(mnt[i][0].equals(token)){
				//System.out.println(mnt[i][1]);
				start_end[0]=Integer.parseInt(mnt[i][1]);
				//System.out.println(mntc);
				if(i != mntc-1)
					start_end[1]=Integer.parseInt(mnt[i+1][1]);
				else
					start_end[1]=-1;
			}
				
		}
		return start_end;
	}
	
	void display_ALA(){
		System.out.println("ARGUMENT LIST ARRAY");
		System.out.println("INDEX     " + "\tARGUMENT");
		for(int i =0 ;i < k;i++)
		{
			System.out.println(ALA[i][0]+"		"+ALA[i][1]);
		}	
	}
void close(){
		
		try {
			bwoutput.close();
			brpass1.close();
			brsp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
public class Pass2 {
	public static void main(String[] args) {
		try{
			Pass2_implementaion o1 = new Pass2_implementaion();
			o1.fetch_MDT();
			o1.fetch_MNT();
			o1.fetch_ALA();
			o1.read_sp();
			o1.display_ALA();
			o1.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
