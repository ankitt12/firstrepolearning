public class JNITest{
	static{
		System.load("/home/ankit/sem_6_prac/DLL/mynativelib.so");
			
	}
	public native void greet();
	public static void main(String[] args){
		JNITest test = new JNITest();
		test.greet();	
	}	
}
