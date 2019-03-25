package pack1;

import java.util.HashMap;


public class MOT_POT  {
	HashMap <String , String[]> motmap;
	HashMap<String, String> potmap;
	public MOT_POT(){
		motmap = new HashMap<String, String[]>();// mnemomic_opcode,mc_code,length
		potmap = new HashMap<String, String>(); //mnemonic_opcode,mc_code
	}
	
	  void create_MOT(){
		motmap.put("STOP", new String[]{"00","1"});
		motmap.put("ADD", new String[]{"01","1"});
		motmap.put("SUB", new String[]{"02","1"});
		motmap.put("MULT", new String[]{"03","1"});
		motmap.put("MOVER", new String[]{"04","1"});
		motmap.put("MOVEM", new String[]{"05","1"});
		motmap.put("COMP", new String[]{"06","1"});
		motmap.put("BC", new String[]{"07","1"});
		motmap.put("DIV", new String[]{"08","1"});
		motmap.put("READ", new String[]{"09","1"});
		motmap.put("PRINT", new String[]{"10","1"});
	}
	  void create_POT(){
		potmap.put("START", "01");
		potmap.put("END", "02");
		potmap.put("ORIGIN", "03");
		potmap.put("EQU", "04");
		potmap.put("LTORG", "05");
		potmap.put("DC", "06");
		potmap.put("DS", "07");
	}
	String[] find_MOT(String key) {
		// TODO Auto-generated method stub
		if(motmap.containsKey(key)){
			return motmap.get(key);
		}
		return null;
	}
	String find_POT(String key){
		if(potmap.containsKey(key)){
			return potmap.get(key);
		}
		return null;
	}
	
}
