import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/*
 * File:	MultithreadedService.java
 * Course: 	21HT - Operating Systems - 1DV512
 * Author: 	jg223fp
 * Date: 	December 2021
 */

// TODO: put this source code file into a new Java package with meaningful name (e.g., dv512.YourStudentID)!

// You can implement additional fields and methods in code below, but
// you are not allowed to rename or remove any of it!

// Additionally, please remember that you are not allowed to use any third-party libraries

public class MultithreadedService {


  private class Task implements Runnable {
    
    private Integer Id;
		private Long burstTime;
		private Long startTime = null;
		private Long finishTime = null;

		private Task() {
			this.Id = taskCount;
			this.burstTime = rndBurstTime(); 	
		}

		public void setStartTime() {
      this.startTime = System.currentTimeMillis() - simStartTime;
		}

		public void setFinishTime() {
			this.finishTime = System.currentTimeMillis() - simStartTime;
		}


		@Override  
    public void run() {
       try {
        setStartTime();
        Thread.sleep(burstTime);
        setFinishTime();
        completed.add(this);

        System.out.println(Id + " finished. Bursttime: " + burstTime);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        interrupted.add(this);
        System.out.println("Error!  " + Thread.currentThread().getName() +  ": Task " + Id + " was interrupted!");
      }
		}	

  }

    // TODO: implement a nested public class titled Task here
    // which must have an integer ID and specified burst time (duration) in milliseconds,
    // see below
    // Add further fields and methods to it, if necessary
    // As the task is being executed for the specified burst time, 
    // it is expected to simply go to sleep every X milliseconds (specified below)


    // Random number generator that must be used for the simulation
	Random rng;
  Long simStartTime;
	Long minBurstTimeMs;
	Long maxBurstTimeMs;
	private Integer taskCount = 0;
  private List<Runnable> completed = new ArrayList<Runnable>();
  private List<Runnable> interrupted = new ArrayList<Runnable>();
  private List<Runnable> waiting = null;

    // ... add further fields, methods, and even classes, if necessary
    

	public MultithreadedService (long rngSeed) {
        this.rng = new Random(rngSeed);
    }


	public void reset() {
		this.taskCount = 0;
    }

	public Long rndBurstTime() {
		long bt = ThreadLocalRandom.current().nextLong(minBurstTimeMs, maxBurstTimeMs+1);  //generates long in given range
		return bt;
	}	
    

    // If the implementation requires your code to throw some exceptions, 
    // you are allowed to add those to the signature of this method
    public void runNewSimulation(final long totalSimulationTimeMs,
        final int numThreads, final int numTasks,
        final long minBurstTimeMs, final long maxBurstTimeMs, final long sleepTimeMs) {

        reset();
				// set up fields for multithreaded service
				this.maxBurstTimeMs = maxBurstTimeMs;
				this.minBurstTimeMs = minBurstTimeMs;

        
				// create cpu
				ExecutorService cpu = Executors.newFixedThreadPool(numThreads);

        // set start time
				this.simStartTime = System.currentTimeMillis();

        // add tasks
        for (int i = 0; i < numTasks; i++) {
          taskCount += 1;
          Task t = new Task();
          cpu.submit(t);  
        }

        // limit simulation time
				while(System.currentTimeMillis() - simStartTime < totalSimulationTimeMs) {
				}

        // shutdown cpu and return list of unprocessed tasks
        try {
          this.waiting = cpu.shutdownNow();
            
          
         } catch (Exception e) {
           //TODO: handle exception
         }
				


        // TODO:
        // 1. Run the simulation for the specified time, totalSimulationTimeMs
        // 2. While the simulation is running, use a fixed thread pool with numThreads
        // (see https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/concurrent/Executors.html#newFixedThreadPool(int) )
        // to execute Tasks (implement the respective class, see above!)
        // 3. The total maximum number of tasks is numTasks, 
        // and each task has a burst time (duration) selected randomly
        // between minBurstTimeMs and maxBurstTimeMs (inclusive)
        // 4. The implementation should assign sequential task IDs to the created tasks (0, 1, 2...)
        // and it should assign them to threads in the same sequence (rather any other scheduling approach)
        // 5. When the simulation time is up, it should make sure to stop all of the currently executing
        // and waiting threads!

    }


    public void printResults() {
        // TODO:
        System.out.println("Completed tasks:");
        // 1. For each *completed* task, print its ID, burst time (duration),
        // its start time (moment since the start of the simulation), and finish time
        for (int i = 0; i < completed.size(); i++) {
          Runnable r = completed.get(i);
          System.out.println("rad 164. Försök printa id och allt från tasks");
          System.out.println(r.toString());          
        }
        
        System.out.println("Interrupted tasks:");
        // 2. Afterwards, print the list of tasks IDs for the tasks which were currently
        // executing when the simulation was finished/interrupted
        
        System.out.println("Waiting tasks:");
        // 3. Finally, print the list of tasks IDs for the tasks which were waiting for execution,
        // but were never started as the simulation was finished/interrupted
	}




    // If the implementation requires your code to throw some exceptions, 
    // you are allowed to add those to the signature of this method
    public static void main(String args[]) {
		// TODO: replace the seed value below with your birth date, e.g., "20001001"
		final long rngSeed = 19910126;  
				
        // Do not modify the code below — instead, complete the implementation
        // of other methods!
        MultithreadedService service = new MultithreadedService(rngSeed);
        
        final int numSimulations = 1;                        /// SHOULD BE 4 !!!!!!!!!!!!!!!!!!!!
        final long totalSimulationTimeMs = 15*1000L; // 15 seconds
        
        final int numThreads = 4;
        final int numTasks = 30;
        final long minBurstTimeMs = 1*1000L; // 1 second  
        final long maxBurstTimeMs = 10*1000L; // 10 seconds
        final long sleepTimeMs = 100L; // 100 ms

        for (int i = 0; i < numSimulations; i++) {
            System.out.println("Running simulation #" + i);

            service.runNewSimulation(totalSimulationTimeMs,
                numThreads, numTasks,
                minBurstTimeMs, maxBurstTimeMs, sleepTimeMs);

            System.out.println("Simulation results:"
					+ "\n" + "----------------------");	
            service.printResults();

            System.out.println("\n");
        }

        System.out.println("----------------------");
        System.out.println("Exiting...");
        
        // If your program has not completed after the message printed above,
        // it means that some threads are not properly stopped! -> this issue will affect the grade
    }
}
