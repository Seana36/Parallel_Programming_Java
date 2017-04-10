	// GUI-related imports

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

	public class  MTAlgorithms extends Frame implements ActionListener
	{
		String[] description = new String[] {
				"comments on your program goes here"
		};
		static int threshold = 1000;
		// Retrieved command code
		boolean arrayInitialized = false;
		int NDataItems = 10000000;
		int[] a = new int[NDataItems];
		long times[] = new long[8];
		
		int maximumSerial;
		int maximumParallel;
		
		MenuItem miAbout,
		         miInitArr,
		         miSerialSort,
		         miMultiThreadedMergeSort,
		         miMultiThreadedQuickSort,
		         miJavaParallelSort, 
		         miMultiQuickSortAnalyze, 
		         miMultiMergeSortAnalyze,
		         miQuickSortAnalyze,
		         miMergeSortAnalyze,
		         miJavaParAnalyze,
		         miQuickSortAnalyzeThreads,
		         miMergeSortAnalyzeThreads
		         ;
		
		long start, 
					elapsedTimeSerialSort, elapsedTimeParallelMergeSort,
					elapsedTimeParallelQuickSort,elapsedTimeJavaParallelSort;
		String command = "";
			
		public static void main(String[] args)
		{
			Frame frame = new  MTAlgorithms();
			
			frame.setResizable(true);
			frame.setSize(800,500);
			frame.setVisible(true);
			
		}
		
		public  MTAlgorithms()
		{
			setTitle("Parallel Algorithms");
			
			
			// Create Menu Bar
			   			
			MenuBar mb = new MenuBar();
			setMenuBar(mb);
						
			Menu menu = new Menu("Operations");
			
			// Add it to Menu Bar
						
			mb.add(menu);
			
			// Create Menu Items
			// Add action Listener 
			// Add to "File" Menu Group
			
			miAbout = new MenuItem("About");
			miAbout.addActionListener(this);
			menu.add(miAbout);
			
		    miInitArr = new MenuItem("Initialize Array");
			miInitArr.addActionListener(this);
			menu.add(miInitArr);
			
			//Serial Sort = Merge Sort 
			miSerialSort = new MenuItem("Serial Sort");
			miSerialSort.addActionListener(this);
			miSerialSort.setEnabled(false);
			menu.add(miSerialSort);
			
			miMultiThreadedMergeSort = new MenuItem("MultiThreaded MergeSort");
			miMultiThreadedMergeSort.addActionListener(this);
			miMultiThreadedMergeSort.setEnabled(false);
			menu.add(miMultiThreadedMergeSort);
			
			miMultiThreadedQuickSort = new MenuItem("MultiThreaded QuickSort");
			miMultiThreadedQuickSort.addActionListener(this);
			miMultiThreadedQuickSort.setEnabled(false);
			menu.add(miMultiThreadedQuickSort);
			
			miJavaParallelSort = new MenuItem("Java Parallel Sort");
			miJavaParallelSort.addActionListener(this);
			miJavaParallelSort.setEnabled(false);
			menu.add(miJavaParallelSort);
			
			MenuItem miExit = new MenuItem("Exit");
			miExit.addActionListener(this);
			menu.add(miExit);
			
											
			Menu analyzeMenu = new Menu("Analyze");
			// Add it to Menu Bar
			mb.add(analyzeMenu);
						
			miMultiMergeSortAnalyze = new MenuItem("Analyze Parallel Merge Sort");
			miMultiMergeSortAnalyze.addActionListener(this);
			miMultiMergeSortAnalyze.setEnabled(false);
			analyzeMenu.add(miMultiMergeSortAnalyze);	
			
			miMultiQuickSortAnalyze = new MenuItem("Analyze Parallel Quick Sort");
			miMultiQuickSortAnalyze.addActionListener(this);
			miMultiQuickSortAnalyze.setEnabled(false);
			analyzeMenu.add(miMultiQuickSortAnalyze);
			
			miMergeSortAnalyzeThreads = new MenuItem("Analyze Parallel Merge Sort Threads");
			miMergeSortAnalyzeThreads.addActionListener(this);
			miMergeSortAnalyzeThreads.setEnabled(false);
			analyzeMenu.add(miMergeSortAnalyzeThreads);
	    
			miQuickSortAnalyzeThreads = new MenuItem("Analyze Parallel Quick Sort Threads");
			miQuickSortAnalyzeThreads.addActionListener(this);
			miQuickSortAnalyzeThreads.setEnabled(false);
			analyzeMenu.add(miQuickSortAnalyzeThreads);

			// End program when window is closed
			
			WindowListener l = new WindowAdapter()
			{
							
				public void windowClosing(WindowEvent ev)
				{
					System.exit(0);
				}
				
				public void windowActivated(WindowEvent ev)
				{
					repaint();
				}
				
				public void windowStateChanged(WindowEvent ev)
				{
					repaint();
				}
			
			};
			
			ComponentListener k = new ComponentAdapter()
			{
				public void componentResized(ComponentEvent e) 
				{
	        		repaint();           
	    		}
			};
			
			// register listeners
				
			this.addWindowListener(l);
			this.addComponentListener(k);

		}
		
	//******************************************************************************
	//  called by windows manager whenever the application window performs an action
	//  (select a menu item, close, resize, ....
	//******************************************************************************

		public void actionPerformed (ActionEvent ev) 
			{
				// figure out which command was issued
				
				command = ev.getActionCommand();
				
				// take action accordingly
				
				
				if("About".equals(command))
				{
					
					repaint();
				}
				else
				if("Initialize Array".equals(command))
				{
					InitializeArrays();
					arrayInitialized = true;
					 miMultiQuickSortAnalyze.setEnabled(true);
					 miMultiMergeSortAnalyze.setEnabled(true);
			         miSerialSort.setEnabled(true);
			         miMultiThreadedMergeSort.setEnabled(true);
			         miMultiThreadedQuickSort.setEnabled(true);
			         miJavaParallelSort.setEnabled(true);
			         miQuickSortAnalyzeThreads.setEnabled(true);
			         miMergeSortAnalyzeThreads.setEnabled(true);
					repaint();
				}
				else
					//Merge Sort 
					if("Serial Sort".equals(command))
					{
						MergeSort k = new MergeSort();
						int[] b = new int[a.length];
						System.arraycopy(a, 0, b, 0, a.length);
						
						start = System.currentTimeMillis();
						k.mergeSort(b);
						elapsedTimeSerialSort = System.currentTimeMillis() - start;
						System.out.println("Serial Sort (merge sort) " + elapsedTimeSerialSort);
						
						repaint();
					}
				else
					
					if("MultiThreaded MergeSort".equals(command))
					{
						// create a new array, copy original array to it
						int[] b = new int[a.length];
						System.arraycopy(a, 0, b, 0, a.length);
						
						start = System.currentTimeMillis();
						ParallelMergeSort.startMainTask(b,threshold);
						elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
						System.out.println("Multithreaded merge sort: " + elapsedTimeParallelMergeSort);
						if (isSorted(b))
							repaint();
						else
							System.out.println("Array is not sorted ---- multiThreaded MergeSort");
					}
					else
						//serial sort 
						if("MultiThreaded QuickSort".equals(command))
						{
							// create a new array, copy original array to it
							int[] b = new int[a.length];
							System.arraycopy(a, 0, b, 0, a.length);
							
							start = System.currentTimeMillis();
							ParallelQuickSort.startMainTask(b,threshold);
							
							elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
							System.out.println("Multithreaded QuickSort: " + elapsedTimeParallelQuickSort);
							if (isSorted(b))
								repaint();
							else
								System.out.println("Array is not sorted ---- multiThreaded QuickSort");
						}
				else
					if("Java Parallel Sort".equals(command))
					{
						//create a new array, copy original array to it
						int[] b = new int[a.length];
						System.arraycopy(a, 0, b, 0, a.length);
						
						start = System.currentTimeMillis();
						Arrays.parallelSort(b);
						elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
						
						System.out.println("Java Parallel Sort: " + elapsedTimeJavaParallelSort);
						repaint();
					}
				else if("Analyze Parallel Merge Sort".equals(command)){
						// create a new array, copy original array to it
						System.out.println("analyze merge sort");
						int[] b = new int[a.length];
						int count = 0; 
						//for(int i = 1000; i <=2500000; i *=10){
						//1000
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 1000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " " );
							count++; 
						//5000
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 5000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " " );
							count++; 

						//100,000	
						b = new int[a.length];
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 100000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " "  );
							count++;

						//500,000
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 500000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " " );
							count++; 
						//1,000,000	
						b = new int[a.length];
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 1000000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " "  );
							count++; 
						//2,000,000	
						b = new int[a.length];
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 2000000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " "  );
							count++; 

						//2,500,000	
						b = new int[a.length];
							System.arraycopy(a, 0, b, 0, a.length);
							threshold = 2500000; 
							start = System.currentTimeMillis();
							ParallelMergeSort.startMainTask(b,threshold);
							elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
							times[count] = elapsedTimeParallelMergeSort; 
							System.out.println("Times: "  + threshold +" " +times[count] + " "  );
							count++; 
							
						//3,000,000	
							b = new int[a.length];
								System.arraycopy(a, 0, b, 0, a.length);
								threshold = 3000000; 
								start = System.currentTimeMillis();
								ParallelMergeSort.startMainTask(b,threshold);
								elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
								times[count] = elapsedTimeParallelMergeSort; 
								System.out.println("Times: "  + threshold +" " +times[count] + " "  );
								count++;
					
//						for(int i = 0; i<times.length; i++){
//							System.out.println(times[i]);
//						}
								
						//java parallel sort 
								
						int[] c = new int[a.length];
						System.arraycopy(a, 0, c, 0, a.length);
						
						start = System.currentTimeMillis();
						Arrays.parallelSort(c);
						elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
												
						if (isSorted(b))
							repaint();
						else
							System.out.println("Array is not sorted ---- multiThreaded MergeSort");
					
					}
				else
					if("Analyze Parallel Quick Sort".equals(command)){
						System.out.println("analyze quick sort");
						// create a new array, copy original array to it
						int[] b = new int[a.length];
						 
						int count = 0; 
					//1,000
						threshold = 1000; 
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++; 
					//5,000
						threshold = 5000; 
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++;
					//500,000
						b = new int[a.length]; 
						threshold = 500000;
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++; 
					//1,000,000
						b = new int[a.length];
						threshold = 1000000; 
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++; 
					//2,000,000
						b = new int[a.length];
						threshold = 2000000; 
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++; 
					//2,500,000
						b = new int[a.length];
						threshold = 2500000; 
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++; 
					//5,000,000
						b = new int[a.length];
						threshold = 3000000; 
						System.arraycopy(a, 0, b, 0, a.length);
						start = System.currentTimeMillis();
						ParallelQuickSort.startMainTask(b,threshold);
						elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
						times[count] = elapsedTimeParallelQuickSort;
						System.out.println("Length: " + threshold + " " + times[count]);
						count++; 
						
						//java parallel sort 
						
						int[] c = new int[a.length];
						System.arraycopy(a, 0, c, 0, a.length);
						
						start = System.currentTimeMillis();
						Arrays.parallelSort(c);
						elapsedTimeJavaParallelSort = System.currentTimeMillis()-start;
				
						if (isSorted(b))
							repaint();
						else
							System.out.println("Array is not sorted ---- multiThreaded QuickSort");
					}
					else 
						if("Analyze Parallel Merge Sort Threads".equals(command)){
							//2,500,000
							int[] b = new int[a.length];
							threshold = 2500000; 
							int threadSize = 0; 
							int count = 0; 
						for(int i = 2; i <=4; i ++){
							threadSize = i; 
								System.arraycopy(a, 0, b, 0, a.length);
								start = System.currentTimeMillis();
								ParallelMergeSort.startMainTask(b,threshold, threadSize);
								elapsedTimeParallelMergeSort = System.currentTimeMillis()-start;
								times[count] = elapsedTimeParallelMergeSort;
								System.out.println("MERGE: " +  " " + elapsedTimeParallelMergeSort);
						
							count++; 
						}
						repaint(); 
						}
					else 
						if("Analyze Parallel Quick Sort Threads".equals(command)){
							//2,500,000
							int[] b = new int[a.length];
							threshold = 2500000;
							int threadSize = 0; 
							int count = 0; 
							for(int i = 2; i <=4; i ++){
								threadSize = i; 
								System.arraycopy(a, 0, b, 0, a.length);
								start = System.currentTimeMillis();
								ParallelQuickSort.startMainTask(b,threshold);
								elapsedTimeParallelQuickSort = System.currentTimeMillis()-start;
								times[count] = elapsedTimeParallelQuickSort;
								System.out.println("QUICK: " + threadSize + " " + elapsedTimeParallelQuickSort);
								count++; 
							}
							repaint(); 
						}
				else
					if("Exit".equals(command))
					{
						System.exit(0);
					}

			}
	//********************************************************
	// called by repaint() to redraw the screen
	//********************************************************
			
			public void paint(Graphics g)
			{
				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				
				if( "Serial Sort".equals(command ) 
					|| "MultiThreaded MergeSort".equals(command)
					|| "MultiThreaded QuickSort".equals(command)
					|| "Java Parallel Sort".equals(command))
				{
					
					g.drawString(
							"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
						g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
						g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
					
					g.drawString("Serial Sort (Merge Sort) "	,100, 200);
					g.drawString(elapsedTimeSerialSort   +""	,500, 200);
					
					g.drawString("MultiThreaded Merge Sort "	,100, 250);
					g.drawString(elapsedTimeParallelMergeSort+"",500, 250);
					
					g.drawString("MultiThreaded QuickSort " 	,100, 300);
					g.drawString(elapsedTimeParallelQuickSort+"",500, 300);
					
					g.drawString("Java Parallel Sort "			,100, 350);
					g.drawString(elapsedTimeJavaParallelSort+"" ,500, 350);
				}	
				else
					if("Analyze Parallel Merge Sort".equals(command)){
						
						g.drawString(
								"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
						g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
						g.drawString("Parallel Merge Sort ",300, 170);
						
						
						int x = 200; 
						int y = 200; 
						
						int[] threshold_merge = {1000, 5000, 100000, 500000,1000000,2000000,2500000,3000000}; 
						
						g.setColor(Color.red);
						g.drawString("Java Parallel Sort "			,200, 400);
						g.drawString(elapsedTimeJavaParallelSort+"" ,400, 400);
						g.setColor(Color.black);
						g.drawString("Thresholds", x, y);
						g.drawString("Times", 400, y);
						
						for(int i =0; i<times.length; i++){
							y+=20;
							g.drawString("" + threshold_merge[i], x, y);
							g.drawString("" + times[i] , 400, y);
						}
					}
					else
						if("Analyze Parallel Quick Sort".equals(command)){
							
							g.drawString(
									"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
							g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
							g.drawString("Parallel Quick Sort ",300, 170);
							
							int x = 200; 
							int y = 200; 
							
							int[] threshold_quick = {1000, 5000,500000, 1000000,2000000,2500000,3000000};
							g.setColor(Color.red);
							g.drawString("Java Parallel Sort "			,200, 400);
							g.drawString(elapsedTimeJavaParallelSort+"" ,400, 400);
							g.setColor(Color.black);
							g.drawString("Thresholds", x, y);
							g.drawString("Times", 400, y);
							
							
							for(int i =0; i<times.length-1; i++){
								y+=20;
								g.drawString("" + threshold_quick[i], x, y);
								g.drawString("" + times[i] , 400, y);
							}
						}
				else 
				if ("Analyze Parallel Merge Sort Threads".equals(command)){
					int x = 200; 
					int y = 200; 
					g.drawString(
							"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
					g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
					g.drawString("Parallel Merge Sort ",300, 170);
					
					g.drawString("Number of Threads" , x, y);
					g.drawString("Execution Time", 400, y);
					for(int i =0; i <3; i++){
						y+=20;
						g.drawString("" + (i+2), x, y);
						g.drawString(times[i] + "", 400, y);
						 
					}
				}
				else
				if ("Analyze Parallel Quick Sort Threads".equals(command)){
					int x = 200; 
					int y = 200; 
					g.drawString(
							"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
					g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
					g.drawString("Parallel Quick Sort ",300, 170);
					
					g.drawString("Number of Threads" , x, y);
					g.drawString("Execution Time", 400, y);
					for(int i =0; i <3; i++){
						y+=20;
						g.drawString("" + (i+2), x, y);
						g.drawString(times[i] + "", 400, y);
					}
				}
				else	
				if("About".equals(command))
				{
					int x = 200;
					int y = 200;
					for(int i = 0; i < description.length; i++)
					{
						g.drawString(description[i], x, y);
						y = y +25;
					}
				}
				else
					if("Initialize Array".equals(command))
					{
						g.drawString(
								"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
							g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
							g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
						g.drawString("Array Initialized",200, 100);
					}	
					else {
						g.drawString(
								"Number of processors is "+Integer.toString( Runtime.getRuntime().availableProcessors() ),300,130);
							g.drawString("Number of Data Items = "+Integer.toString(NDataItems),300, 150);
							g.drawString("Threshold = "+Integer.toString(threshold),300, 170);
					}
			}

public void InitializeArrays ()
{
	maximumSerial=	maximumParallel = -1;
	
	long elapsedTimeSerialMax;
	long elapsedTimeParallelMax;
	start = elapsedTimeSerialMax = elapsedTimeParallelMax =
				elapsedTimeSerialSort =  elapsedTimeParallelMergeSort = 
						elapsedTimeParallelQuickSort = elapsedTimeJavaParallelSort = 0;
	for (int i=0; i<a.length; i++)
		a[i] = (int) (Math.random()*400000000);
}
public boolean isSorted(int[] list)
{
	boolean sorted = true;
	int index = 0;
	while (sorted & index<list.length-1)
	{
		if (list[index] > list[index+1])
			sorted = false;
		else
			index++;	
	}
	return sorted;
}
}
		


