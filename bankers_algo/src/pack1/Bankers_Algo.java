package pack1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Bankers_Algo {
	Integer p; // no of process
	Integer r; // no of resources
	ArrayList < ArrayList<Integer> > allocated; // no of units allocated to each process
	ArrayList < ArrayList<Integer> > max_need; // no of units needed by eadch process
	ArrayList <Integer> available; // max no of units of each resources available
	ArrayList < ArrayList<Integer> > need; // resources need for each process to complete its 
	ArrayList<Integer> max_resources;
	ArrayList<Boolean> processed;
	public static Scanner sc;
	public Bankers_Algo(int p , int r){
		sc = new Scanner(System.in);
		this.p = p;
		this.r = r;
		available = new ArrayList<Integer>();
		allocated = new ArrayList<ArrayList<Integer> >();
		max_need =  new ArrayList<ArrayList<Integer> >();
		need =  new ArrayList<ArrayList<Integer> >();
		processed = new ArrayList<Boolean>();
		max_resources = new ArrayList<Integer>();
		for(int i=0 ;i < p ;i++){
			processed.add(false);
		}
	}
	void take_input(){
		//System.out.println("enter max no of units available for each resources");
		for(int i=0 ;i < r; i++){
			max_resources.add(sc.nextInt());
		}
		for(int i=0 ;i < p ; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			//System.out.println("enter requirements of process"+ i +"for each resources");
			for(int j=0 ; j < r ;j++){
				temp.add(sc.nextInt());
			}
			max_need.add(temp);
		}
		
		for(int i=0 ;i < p ; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			//System.out.println("enter allocated unit for"+i+"process for each resources");
			for(int j=0 ; j < r ;j++){
				temp.add(sc.nextInt());
			}
			allocated.add(temp);
		}
		
		Integer sum = 0;
		for(int i=0 ;i < r ;i++){
			sum = 0;
			for(int j=0 ; j<p ;j++){
				sum += allocated.get(j).get(i); // this give the toatal of each resources allocated 
				// to all the processes
			}
			available.add(max_resources.get(i)-sum);
		}
	}
	void sequence_step(){
		// first decide which process will be executed first based on need matrix
		// make need matrix
		for(int i=0 ;i < p ; i++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j=0 ; j < r ;j++){
				Integer temp1 = max_need.get(i).get(j) - allocated.get(i).get(j);
				temp.add(temp1);
			}
			need.add(temp);
		}
		// now check step wise which process will be executed
		System.out.println("INITIAL STATE");
		display();
		ArrayList<Integer> zero = new ArrayList<Integer>();
		for(int i=0 ;i < r; i++){
			zero.add(0);
		}
		int i;
		for(i =0 ;i < p ;i++){
			if(processed.get(i))
				continue;
			else{
				// check whether the need matrix of corresponding arraylist is less than 
				// the available matrix;
				int j;
				for(j=0 ;j < r; j++){
					if(need.get(i).get(j) > available.get(j))
						break;
				}
				if(j == r){
					System.out.println("PROCESS "+(i+1)+" IS GETTING EXECUTED");
					
					processed.set(i, true);
					need.set(i, zero);
					for(int k=0 ;k < r; k++){
						available.set(k, available.get(k)+allocated.get(i).get(k));
					}
					allocated.set(i,zero );
					display();
					// once the process is executed again start the loop from beginning 
					i = -1;
				}
			}
			
		}	
		if(i == p){
			int j;
			for(j=0 ;j < p; j++){
				if( !processed.get(j) )
					break;
			}
			if(j != p)
				System.out.println("System is not in safe state");
		}
		
}
	void display(){
		System.out.println("Max Resources Matrix ");	
		for(int i =0 ;i < r ;i++){
			System.out.print(max_resources.get(i)+ "\t");
		}
		System.out.println();
		System.out.println("Max_Need Matrix");
		for(int i =0 ;i < p ;i++){
			for(int j=0 ; j< r; j++){
				System.out.print(max_need.get(i).get(j)+ "\t");
			}
			System.out.println();
		}
		System.out.println("Allocated Matrix");
		for(int i =0 ;i < p ;i++){
			for(int j=0 ; j< r; j++){
				System.out.print(allocated.get(i).get(j)+ "\t");
			}
			System.out.println();
		}
		System.out.println("Need Matrix");
		for(int i =0 ;i < p ;i++){
			for(int j=0 ; j< r; j++){
				System.out.print(need.get(i).get(j)+ "\t");
			}
			System.out.println();
		}
		System.out.println("Available Resources Matix");	
		for(int i =0 ;i < r ;i++){
			System.out.print(available.get(i)+ "\t");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.println("enter no of process and no of resources");
		Integer temp1  = sc.nextInt();
		Integer temp2 = sc.nextInt();
		Bankers_Algo o1 = new Bankers_Algo(temp1, temp2);
		o1.take_input();
		o1.sequence_step();
		
	}

}
/*
3
2
15
8
5
6
8
5
4
8
2
1
3
2
3
0
*/
/*
13
7
10
4
3
3
7
2
4
4
2
5
5
3
3
2
1
1
7
2
3
3
2
2
1
1
1
 */
