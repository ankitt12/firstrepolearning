package pagingpack;
import java.util.*;
class page
{
	int cpu_demand_s;
	int mm_no_frames; //mm = main memory
	ArrayList<Integer> cpu_demand = new ArrayList<Integer>();
	
	ArrayList<Integer> mm_frame = new ArrayList<Integer>();
	int replace_index;
	
	int stage_count;
	int page_fault_count;
	int flag;
	/*float page_hit_ratio;
	float page_fault_ratio;
	*/Scanner sc;
	public page() {
		// TODO Auto-generated constructor stub
		sc = new Scanner(System.in);
		page_fault_count = 0;
	}
	void take_input()
	{
		System.out.println("Enter page demand size of cpu");
		cpu_demand_s = sc.nextInt();
		System.out.println("Enter respective page numbers in demand by cpu");
		for(int i =0 ; i < cpu_demand_s ;i++)
		{
			cpu_demand.add(sc.nextInt());
		}
		System.out.println("Enter number of frames in main memory");
		mm_no_frames = sc.nextInt();
		for(int i =0 ;i < mm_no_frames ;i++)
		{
			mm_frame.add(-1);
		}
		
	}
	
	void FIFO()
	{
		stage_count = 1;
	
		//keep on adding in sequence the pagenos till the mm_frame is not upto size it can hold
		for(int i =0 ;i < mm_no_frames ;i++)
		{
			mm_frame.set(i, cpu_demand.get(i));
			System.out.println("Main Memory contents for Stage:"+stage_count);
			System.out.println("Page Fault");
			for(int j =0 ; j < mm_no_frames ;j++)
			{
				System.out.println(mm_frame.get(j));
			}
			stage_count++;
			page_fault_count++;
		}
		replace_index = 0;
		
		for(int i = mm_no_frames ; i < cpu_demand_s ;i++)
		{	
			flag = 0;
			if( !mm_frame.contains( cpu_demand.get(i) ) )
			{
				flag = 1;
				mm_frame.set(replace_index , cpu_demand.get(i));
				page_fault_count++;
				replace_index  = (replace_index+1) % mm_no_frames;
			}
			System.out.println("Main Memory contents for Stage:"+stage_count);
			if(flag == 0)
				System.out.println("Page Hit");
			else 
				System.out.println("Page Fault");
			
			for(int j =0 ; j< mm_no_frames ;j++)
			{
				System.out.println(mm_frame.get(j));
			}
			stage_count++;	
		}
		System.out.println("Total page fault count = " + page_fault_count);
		System.out.println("Total page hit count = " + (cpu_demand_s-page_fault_count) );
	}
	
	void LRU()
	{
		ArrayList<Integer> time = new ArrayList<Integer>(mm_no_frames);
		int counter = 1;
		stage_count = 1;
		mm_frame.clear();
		for(int i =0 ;i < mm_no_frames ;i++)
		{
			mm_frame.add(-1);
		}
		page_fault_count = 0;
		//keep on adding in sequence the pagenos till the mm_frame is not upto size it can hold
		for(int i =0 ;i < mm_no_frames ;i++)
		{
			mm_frame.set(i, cpu_demand.get(i));
			System.out.println("Main Memory contents for Stage:"+stage_count);
			System.out.println("Page Fault");
			for(int j =0 ; j < mm_no_frames ;j++)
			{
				System.out.println(mm_frame.get(j));
			}
			stage_count++;
			time.add(counter++);
			page_fault_count++;	
		}
		for(int i = mm_no_frames ; i < cpu_demand_s ;i++)
		{
			flag = 0;
			if( !mm_frame.contains( cpu_demand.get(i) ) )
			{
				flag = 1;
				replace_index = time.indexOf(Collections.min(time));
				time.set(replace_index, counter++);
				mm_frame.set(replace_index , cpu_demand.get(i));
				page_fault_count++;	
			}
			else
				time.set(mm_frame.indexOf(cpu_demand.get(i)), counter++);
			
			System.out.println("Main Memory contents for Stage:"+stage_count);
			if(flag == 0)
				System.out.println("Page Hit");
			else 
				System.out.println("Page Fault");
			for(int j =0 ; j < mm_no_frames ;j++)
			{
				System.out.println(mm_frame.get(j));
			}
			stage_count++;	
		}
		System.out.println("Total page fault count = " + page_fault_count);
		System.out.println("Total page hit count = " + (cpu_demand_s-page_fault_count) );
	}
	
	void optimal_algo()
	{
		mm_frame.clear();
		for(int i =0 ;i < mm_no_frames ;i++)
		{
			mm_frame.add(-1);
		}
		stage_count = 1;
		page_fault_count = 0;
		for(int i =0 ;i < mm_no_frames ;i++)
		{	
			mm_frame.set(i, cpu_demand.get(i));
			page_fault_count++;
			System.out.println("Main Memory contents for Stage:"+stage_count);
			System.out.println("Page Fault");
			for(int j =0 ; j < mm_no_frames ;j++)
			{
				System.out.println(mm_frame.get(j));
			}
			stage_count++;
		}
		int pos = 0;
		int get_i_farthest_page = 0;
		int maximum;
		
		for(int i = mm_no_frames ; i < cpu_demand_s ; i++)
		{
			flag = 0;
			if(!mm_frame.contains(cpu_demand.get(i) ))
			{
				flag = 1;
				maximum = Integer.MIN_VALUE;
				for(int j =0 ; j< mm_no_frames ; j++)
				{
					get_i_farthest_page = getindex(i , mm_frame.get(j));
					if(get_i_farthest_page == -1)
					{
						pos = j;
						break;
					}	
					else
					{
						get_i_farthest_page = Math.max(maximum, get_i_farthest_page);
						maximum = get_i_farthest_page;
						int page_val = cpu_demand.get(get_i_farthest_page);
						pos = mm_frame.indexOf(page_val);
					}		
				}
				mm_frame.set(pos, cpu_demand.get(i));	
			}
			System.out.println("Main Memory contents for Stage:"+stage_count);
			if(flag == 0)
				System.out.println("Page Hit");
			else 
				System.out.println("Page Fault");
			for(int j =0 ; j < mm_no_frames ;j++)
			{
				System.out.println(mm_frame.get(j));
			}
			stage_count++;
		}
		System.out.println("Total page fault count = " + page_fault_count);
		System.out.println("Total page hit count = " + (cpu_demand_s-page_fault_count) );
	}
	
	int getindex(int i , int val)
	{
		for(int j =i ; j < cpu_demand_s ; j++)
		{
			if(cpu_demand.get(j) == val)
				return j;
		}
		return -1;
	}
	
}
public class fisrtalgo {

	/**
	 * @param args
	 */
	
	// 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7
	public static void main(String[] args) {
		// TODO Auto-generated method sub
		page obj1 = new page();
		obj1.take_input();
		System.out.println("FIFO");
		obj1.FIFO();
		System.out.println("LRU");
		obj1.LRU();
		System.out.println("OPTIMAL");
		obj1.optimal_algo();
		
	}

}
