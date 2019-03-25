package pack1;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;
class Pass1AssemblerImplementation{
	// String ST[][]; better to create st hashmap as in generate_ic code we have to search st
	HashMap<String, String[]> ST;
	String LT[][];
	int ltindex;
	int stindex;
	FileReader alpfile; //assembly language program input
	BufferedReader br; // bufferedreader for reading ALP
	ArrayList<String[]> br1;
	FileWriter fileoutput;
	BufferedWriter bwoutput;
	String st;
	String starray[];
	String starray1[];
	int lc; //location counter
	String motvalue[];
	String potvalue;
	int flag;
	String otemp;
	MOT_POT temp;
	int ltorg;
	Set<String> keys;
	HashMap<Integer, Integer> PoolT; // index and value
	int PoolT_index;
	int i;
	int line= 1;
	public Pass1AssemblerImplementation() {
		try{
			alpfile = new FileReader("/home/ankit/sem_6_prac/ALP.txt");
			fileoutput = new FileWriter("/home/ankit/sem_6_prac/Assembler_Pass1_output.txt");
			br = new BufferedReader(alpfile);
			br1 = new ArrayList<String[]>();
			bwoutput = new BufferedWriter(fileoutput);
			temp = new MOT_POT();
			ST = new HashMap<String, String[]>(); // symbol name symbol address symbol length
			//PT = new Integer[10];
			LT = new String[10][3]; // literal no literal address
			ltindex = 1;
			stindex = 1;
			ltorg = 0;
			keys = new HashSet<String>();
			PoolT = new HashMap<Integer, Integer>();
			PoolT_index = 1;
			PoolT.put(1, 1);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	void readfile() {
		temp.create_MOT();
		temp.create_POT();
		try{
			while( (st = br.readLine() ) != null){
				starray = st.trim().split("\\s+"); 
				br1.add(starray); // ithink this is used somewhere else
				i =0;
				potvalue = temp.find_POT(starray[i]);
				if(potvalue != null){
					process_pseudo_op(i);
					System.out.println(lc);
					continue;
				}
				motvalue = temp.find_MOT(starray[i]);
				if(motvalue != null){
					//process literal stored if any 
					if(starray[i].equals("STOP")){
						lc +=  Integer.parseInt(motvalue[1]);
						System.out.println(lc);
						continue;
					}
					store_literal(starray[i+1], 1); //1 implies its called from here 0 implies called from process_pseudo_op
					lc +=  Integer.parseInt(motvalue[1]); //parseint return int valueof return Integer object
					System.out.println(lc);
					continue;
				}
				//symbol found
				insert_ST(starray[i]);
				System.out.println(lc);
			}	
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	void process_pseudo_op(int i){
		switch (starray[i]){
		case "START":
			if(i+1 < starray.length){
				lc = Integer.parseInt(starray[i+1]);
			}else{
				lc = 0;
			}
			break;
		case "DC":
			store_literal(starray[i+1], 0);
			break;
		case "DS":
			lc += Integer.parseInt(starray[i+1]);
			break;
		case "LTORG":
			PoolT_index++;
			PoolT.put(PoolT_index,ltindex);
			change_LT();
			break;
		case "END":
			//to give values to literals if not yet
			//get last entry of PoolTable
			int temp=0;
			for(int key : PoolT.keySet()){
				temp = PoolT.get(key);
			}
			System.out.println("temp"+temp+"ltindesx"+ltindex);
			while(temp < ltindex){
				if(LT[temp][2] == null){
					LT[temp][2] = Integer.toString(lc++);
				}
				temp++;
			}
		}
	}
	void store_literal(String key, int flag) {
		if(flag == 0){
			// find store is called from process pseudo op
			LT[ltindex][0] = Integer.toString(ltindex);
			LT[ltindex][1] = key; // literal value;
			lc += Integer.parseInt(key.replace("'", ""));
		}else{
			// find store is called from readfile mot
			starray1 = key.split("=");
			if(starray1.length == 1){return ;}
			LT[ltindex][0] = Integer.toString(ltindex);
			LT[ltindex][1] = starray1[1];
		}
		ltindex++;
	}
	void change_LT(){
		for(int j=PoolT.get(PoolT_index-1) ;j < ltindex; j++){
			LT[j][2] = Integer.toString(lc);
			lc++;
		}
	}
	void insert_ST(String key) {
		// TODO Auto-generated method stub
		ST.put(key, new String[]{Integer.toString(lc), "1"});//key value(addr, length)
		if(i+1 < starray.length){
			potvalue = temp.find_POT(starray[i+1]);
			if(potvalue != null){
				process_pseudo_op(i+1);
			}
			else{
				motvalue = temp.find_MOT(starray[i+1]);
				if(motvalue != null){
					//process literal stored if any 
					store_literal(starray[i+1], 1); //1 implies its called from here 0 implies called from process_pseudo_o
					lc +=  Integer.parseInt(motvalue[1]); //parseint return int valueof return Integer object
				}
			}
		}
	}
	void display(){
		System.out.println("\nSYMBOL TABLE");
		System.out.println("s_name s_addr s_length");
		Set keys = ST.keySet();   // It will return you all the keys in Map in the form of the Set
		for (java.util.Iterator i = keys.iterator(); i.hasNext();) 
		{

		      String key = (String) i.next();
		      System.out.print(key+"\t");
		      String[] values = ST.get(key); // Here is an Individual Record in your HashMap
		      for(int j=0 ; j < values.length; j++){
		    	  System.out.print(values[j]+ "\t");
		      }
		      System.out.println();
		}
		System.out.println("\nLITERAL TABLE");
		System.out.println("lt_no\tliteral\tlt_addr");
		for(int i=1 ;i < ltindex; i++){
			System.out.println(LT[i][0]+"\t"+LT[i][1]+"\t"+LT[i][2]);
		}
		
		System.out.println("\nLITERAL POOL TABLE");
		System.out.println("Index\tLiteralTableIndex");
		for(int key : PoolT.keySet()){
			System.out.println(key+"\t"+PoolT.get(key));
		}
	}
	void generate_icfile(){
		try{
			int i;
			for(int j=0 ;j < br1.size(); j++){
				if(j != 0){
					bwoutput.write("\n");
				}
				String[] line =  br1.get(j);
				int len = line.length;
				for(i=0 ;i < len ;i++){
					potvalue = temp.find_POT(line[i]);
					if(potvalue != null){
						/*if(i==0){
							bwoutput.write("\t");
						}*/
						if(potvalue.equals("END"))
						bwoutput.write("AD,"+potvalue+"\t");
						continue;
					}
					motvalue = temp.find_MOT(line[i]);
					if(motvalue != null){
						if(i == 0){
							bwoutput.write("\t");
						}
						bwoutput.write("IS,"+motvalue[0]+"\t");
						continue;
					}
					if(ST.containsKey(line[i])){
						bwoutput.write(ST.get(line[i])[0]+"\t");
						continue;
					}
					break;		
				}
				if(i == len){
					continue;
				}else{
					for( ;i < len; i++){
						bwoutput.write(line[i]+"\t");
					}
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	void close(){
		try{
			bwoutput.close();
			br.close();
			//br1.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
public class Pass1Assembler {
	public static void main(String[] args) {
		Pass1AssemblerImplementation o1 = new Pass1AssemblerImplementation();
		o1.readfile();
		o1.generate_icfile();
		//o1.write_file();
		o1.display();
		o1.close();
	}
}

