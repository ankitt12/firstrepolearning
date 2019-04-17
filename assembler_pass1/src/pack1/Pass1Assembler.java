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
	String ST[][]; //better to create st hashmap as in generate_ic code we have to search st
	
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
	Integer lc1, lc; //location counter
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
	ArrayList<Integer> LC;
	public Pass1AssemblerImplementation() {
		try{
			alpfile = new FileReader("/home/ankit/sem_6_prac/ALP.txt");
			fileoutput = new FileWriter("/home/ankit/sem_6_prac/Assembler_Pass1_output.txt");
			br = new BufferedReader(alpfile);
			br1 = new ArrayList<String[]>();
			bwoutput = new BufferedWriter(fileoutput);
			temp = new MOT_POT();
			ST = new String[20][4]; // symbol name symbol address symbol length
			//PT = new Integer[10];
			LT = new String[10][3]; // literal no literal address
			ltindex = 1;
			stindex = 1;
			ltorg = 0;
			keys = new HashSet<String>();
			PoolT = new HashMap<Integer, Integer>();
			PoolT_index = 1;
			PoolT.put(1, 1);
			LC = new ArrayList<>();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	void readfile() {
		temp.create_MOT();
		temp.create_POT();
		try{
			while( (st = br.readLine() ) != null){
				starray = st.replace(',', ' ').trim().split("\\s+"); 
				br1.add(starray); // usedin generate ic code function
				i =0;
				potvalue = temp.find_POT(starray[i]);
				if(potvalue != null){
					process_pseudo_op(i);
					if(lc1 != lc)
					{
					lc1 = lc;
					LC.add(lc1);
					}
//					System.out.println(lc);
					continue;
				}
				motvalue = temp.find_MOT(starray[i]);
				if(motvalue != null){
					//process literal stored if any 
					if(starray[i].equals("STOP")){
						lc +=  Integer.parseInt(motvalue[1]);
						if(lc1 != lc)
						{
						lc1 = lc;
						LC.add(lc1);
						}
						//System.out.println(lc);
						continue;
					}
					store_literal(i+1, 1); //1 implies its called from here 0 implies called from process_pseudo_op
					if(lc1 != lc)
					{
					lc1 = lc;
					LC.add(lc1);
					}
					
					lc +=  Integer.parseInt(motvalue[1]); //parseint return int valueof return Integer object
					if(lc1 != lc)
					{
					lc1 = lc;
					LC.add(lc1);
					}
					//System.out.println(lc);
					continue;
				}
				//symbol found
				insert_ST(starray[i]);
				if(lc1 != lc)
				{
				lc1 = lc;
				LC.add(lc1);
				}
//				System.out.println(lc);
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
			store_literal(i+1, 0);
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
//			System.out.println("temp"+temp+"ltindesx"+ltindex);
			while(temp < ltindex){
				if(LT[temp][2] == null){
					LT[temp][2] = Integer.toString(lc++);
				}
				temp++;
			}
		}
	}
	void store_literal(int j, int flag) {
		if(flag == 0){
			// find store is called from process pseudo op
			LT[ltindex][0] = Integer.toString(ltindex);
			LT[ltindex][1] = starray[j]; // literal value;
			lc += Integer.parseInt(starray[j].replace("'", ""));
			ltindex++;
		}else{
			// find store is called from readfile mot
			for(int k = j ;k < starray.length ;k++){
				starray1 = starray[k].split("=");
				if(starray1.length == 1){continue;}
				LT[ltindex][0] = Integer.toString(ltindex);
				LT[ltindex][1] = starray1[1];
				ltindex++;
				break;
			}
			
		}
		
	}
	void change_LT(){
		for(int j=PoolT.get(PoolT_index-1) ;j < ltindex; j++){
			LT[j][2] = Integer.toString(lc);
			lc++;
		}
	}
	void insert_ST(String key) {
		// TODO Auto-generated method stub
		//ST.put(key, new String[]{Integer.toString(lc), "1"});//key value(addr, length)
		ST[stindex][0] = Integer.toString(stindex);
		ST[stindex][1] = key;
		ST[stindex][2] = Integer.toString(lc);
		ST[stindex][3] = "1";
		stindex++;
		if(i+1 < starray.length){
			potvalue = temp.find_POT(starray[i+1]);
			if(potvalue != null){
				process_pseudo_op(i+1);
			}
			else{
				motvalue = temp.find_MOT(starray[i+1]);
				if(motvalue != null){
					//process literal stored if any 
					store_literal(i+1, 1); //1 implies its called from here 0 implies called from process_pseudo_o
					lc +=  Integer.parseInt(motvalue[1]); //parseint return int valueof return Integer object
				}
			}
		}
	}
	void display_writetofile(){
		try{
			for(int i=0 ;i < LC.size();i++){
				System.out.println(LC.get(i));
			}
			System.out.println("\nSYMBOL TABLE");
			bwoutput.write("\nSYMBOL TABLE\n");
			System.out.println("s_no s_name s_addr s_length");
			bwoutput.write("s_no s_name s_addr s_length\n");
			for(int i=1 ;i < stindex ;i++){
				System.out.println(ST[i][0]+"\t"+ST[i][1]+"\t"+ST[i][2]+"\t"+ST[i][3]);
				bwoutput.write(ST[i][0]+"\t"+ST[i][1]+"\t"+ST[i][2]+"\t"+ST[i][3]+"\n");
			}
			System.out.println("\nLITERAL TABLE");
			bwoutput.write("\nLITERAL TABLE\n");
			System.out.println("lt_no\tliteral\tlt_addr");
			bwoutput.write("lt_no\tliteral\tlt_addr\n");
			for(int i=1 ;i < ltindex; i++){
				System.out.println(LT[i][0]+"\t"+LT[i][1]+"\t"+LT[i][2]);
				bwoutput.write(LT[i][0]+"\t"+LT[i][1]+"\t"+LT[i][2]+"\n");
			}
			
			System.out.println("\nLITERAL POOL TABLE");
			bwoutput.write("\nLITERAL POOL TABLE\n");
			System.out.println("Index\tLiteralTableIndex");
			bwoutput.write("Index\tLiteralTableIndex\n");
			for(int key : PoolT.keySet()){
				System.out.println(key+"\t"+PoolT.get(key));
				bwoutput.write(key+"\t"+PoolT.get(key)+"\n");
			}
			//TO DEBUG VIEW ARRAYLIST br1 contents
//			for(String[] temp: br1){
//				int s = temp.length;
//				for(int i=0 ;i < s ;i++){
//					System.out.print(temp[i]+"\t");
//				}
//				System.out.println();
//			});
			System.out.println("s_no s_name s_addr s_length");
			for(int i=1 ;i < stindex ;i++){
				System.out.println(ST[i][0]+"\t"+ST[i][1]+"\t"+ST[i][2]+"\t"+ST[i][3]);
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
			//TO DEBUG VIEW ARRAYLIST br1 contents
//			for(String[] temp: br1){
//				int s = temp.length;
//				for(int i=0 ;i < s ;i++){
//					System.out.print(temp[i]+"\t");
//				}
//				System.out.println();
//			}
			for(int i=1 ;i < stindex ;i++){
				System.out.println(ST[i][0]+"\t"+ST[i][1]+"\t"+ST[i][2]+"\t"+ST[i][3]);
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
			//TO DEBUG VIEW ARRAYLIST br1 contents
//			for(String[] temp: br1){
//				int s = temp.length;
//				for(int i=0 ;i < s ;i++){
//					System.out.print(temp[i]+"\t");
//				}
//				System.out.println();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	void generate_icfile(){
		try{
			int i;
			int k=0;
			for(int j=0 ;j < br1.size(); j++){
				
				if(j != 0){
					bwoutput.write("\n"); // to
					bwoutput.write("("+Integer.toString(LC.get(k++)) + ")\t" );
				}
				
				String[] line =  br1.get(j);
				int len = line.length;
				for(i=0 ;i < len ;i++){
					potvalue = temp.find_POT(line[i]);
					if(potvalue != null){
						/*if(i==0){
							bwoutput.write("\t");
						}*/
						//if(potvalue.equals("END"))
						bwoutput.write("(AD,"+potvalue+")\t");
						continue;
					}
					motvalue = temp.find_MOT(line[i]);
					if(motvalue != null){
//						if(i == 0){
//							bwoutput.write("\t");
//						}
						bwoutput.write("(IS,"+motvalue[0]+")\t");
						continue;
					}
					if(i == 0) continue; // implies its a symbol defintion whichis skipped
					
					// three cases the operand can be symbol, constant, literal, register
					
					//if its a literal
					char c = line[i].charAt(0);
					if(c == '=' || c == '\''){
						if(c == '=')line[i] = line[i].split("=")[1];
//						System.out.println(line[i]);
						bwoutput.write("(L,"+findliteral(line[i])+")\t");
						continue;
					}
					//if its a symbol
					String stindex1 = findst(line[i]);
					if( stindex1 != null){
						
						bwoutput.write("(S,"+stindex1+")\t");
						continue;
					}
					// if its a register
					if(line[i].equals("AREG")){
						bwoutput.write("(1)");
						continue;
					}
					else if(line[i].equals("BREG")){
						bwoutput.write("(2)");
						continue;
					}
					else if(line[i].equals("CREG")){
						bwoutput.write("(3)");
						continue;
					}
					// if its a constant
					bwoutput.write("(C,"+line[i]+")\t");
											
//					
//					break;		
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
	String findliteral(String line_i){
		// get the index where the literal is stored in literal table
		for(int i=1 ;i < ltindex ;i++){
			if(LT[i][1].equals(line_i) ){
				return Integer.toString(i);
			}
		}
		return null;
	}
	String findst(String line_i){
		// get the index where the symbol is stored
		for(int i=1 ; i< stindex ;i++){
			if(ST[i][1].equals(line_i)){
				return Integer.toString(i);
			}
		}
		return null;
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
		o1.display_writetofile();//writes symbol table literal table to the file which is to be used
		// for pass 2
		 
		
		o1.close();
	}
}

