
package pack1;
import java.io.*;
class Pass1implementation{
	int mdtc,mntc,index;
	String ALA[][]; // index argument // dummy argument not the one stored in source program
	String MDT[][]; // mdtindex line
	String MNT[][]; // mntindex name mdtinndex
	String hash = "#";
	String st ,st1;
	String temp[];
	FileReader fileinput;
	BufferedReader brinput; 
	FileWriter fileoutput;
	BufferedWriter bwoutput;
	int flag;
	public Pass1implementation(){
		mdtc = 1;
		mntc = 1;
		index = 0;
		ALA = new String[10][2]; // index argument // dummy argument not the one stored in source program
		MDT = new String[30][2]; // mdtindex line
		MNT = new String[5][3]; // mntindex name mdtinndex
		hash = "#";
		try {
			fileinput = new FileReader("/home/ankit/sem_6_prac/macro_pass1_input.txt");
			brinput = new BufferedReader(fileinput);
			fileoutput = new FileWriter("/home/ankit/sem_6_prac/Macro_Pass1_output.txt");
			bwoutput = new BufferedWriter(fileoutput);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void readfile(){
		try{
			while((st = brinput.readLine())!= null){
				if(st.equals("MACRO")){
					insert_into_table();
				}
				else
					System.out.println(st);
			}
		}catch(Exception e){
				e.printStackTrace();
		}
	}
	void insert_into_table(){
		flag=1;
		try{
			st = brinput.readLine(); // first line of macro definition
			insert_into_mdt(st,flag);
			flag=0;
			insert_into_mnt_ala(st);
			while(true){
				st = brinput.readLine(); // second line and consecuently of macro definition
				if(st.equals("MEND"))
					break;
				
				insert_into_mdt(st, flag);
			}
			}catch(Exception e){
				e.printStackTrace();
		}
	}
	void insert_into_mnt_ala(String st){
		temp = st.split("\\s+");
		//System.out.println(flag);
		for(int i=0;i < temp.length ;i++)
		{
			if(temp[i].charAt(0) == '&')
			{
				ALA[index][0] = hash+String.valueOf(index);
				ALA[index][1] = temp[i];
				index++;
			}
			else
			{
				//System.out.println("mntc"+mntc);
				MNT[mntc][0] = String.valueOf(mntc);
				MNT[mntc][1] = temp[i];
				MNT[mntc][2] = String.valueOf(mdtc-1);
				mntc++;
			}
		}
	}
	void insert_into_mdt(String st, int flag){
		if(flag == 1){
			MDT[mdtc][0] = String.valueOf(mdtc);
			MDT[mdtc][1] = st;
			mdtc++;
		}else{
		st1 = "";
		temp = st.split(" ");
		for(int i=0 ;i < temp.length ;i++){
			if(temp[i].charAt(0) == '&'){
				//find in ala and get the corresponding index
				temp[i]=find_in_ala(temp[i]);
			}
			st1 = st1 + " " + temp[i];
		}
		MDT[mdtc][0] = String.valueOf(mdtc);
		MDT[mdtc][1] = st1;
		mdtc++;
		}	
	}
	String find_in_ala(String temp){
		for(int i =0 ;i < index ;i++){
			if(ALA[i][1].equals(temp))
				return ALA[i][0];
		}
		return "";
	}
	void display(){
		System.out.println("MACRO DEFINITION TABLE");
		System.out.println("MDTC\t"+"\tCARD");
		for(int i =1 ;i < mdtc ;i++)
		{
			System.out.println(MDT[i][0]+"		"+MDT[i][1]);
		}
		System.out.println("MACRO NAME TABLE");
		System.out.println("MNTC 	"+" 	"+"NAME		"+ "MDTC ");
		for(int i =1 ;i < mntc ;i++)
		{
			System.out.println(MNT[i][0]+"		"+MNT[i][1]+"		"+MNT[i][2]);
		}
		System.out.println("ARGUMENT LIST ARRAY");
		System.out.println("INDEX     " + "\tARGUMENT");
		for(int i =0 ;i < index ;i++)
		{
			System.out.println(ALA[i][0]+"		"+ALA[i][1]);
		}	
	}
	void write_to_file(){
		try{
			bwoutput.write("MACRO DEFINITION TABLE\n");
			bwoutput.write("MDTC\t"+"\tCARD");
			for(int i =1 ;i < mdtc ;i++)
			{
				bwoutput.write("\n");
				bwoutput.write(MDT[i][0]+"		"+MDT[i][1]);
				
			}
			bwoutput.write("\n");
			bwoutput.write("MACRO NAME TABLE\n");
			bwoutput.write("MNTC 	"+" 	"+"NAME		"+ "MDTC ");
			for(int i =1 ;i < mntc ;i++)
			{
				bwoutput.write("\n");
				//System.out.println(ALA[i][1]);
				bwoutput.write(MNT[i][0]+"		"+MNT[i][1]+"		"+MNT[i][2]);
			}
			bwoutput.write("\n");
			bwoutput.write("ARGUMENT LIST ARRAY\n");
			bwoutput.write("INDEX     " + "\tARGUMENT");
			for(int i =0 ;i < index ;i++)
			{
				bwoutput.write("\n");
				//System.out.println(ALA[i][1]);
				bwoutput.write(ALA[i][0]+"		"+ALA[i][1]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	void close(){
		try {
			brinput.close();
			bwoutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
public class Pass1 {
	public static void main(String[] args) {
		try{
			Pass1implementation o1 = new Pass1implementation();
			o1.readfile();
			o1.display();
			o1.write_to_file();
			o1.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

