import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * File:	MultithreadedService.java
 * Course: 	21HT - Operating Systems - 1DV512
 * Author: 	jg223fp Johan
 * Date: 	210106
 */

// TODO: put this source code file into a new Java package with meaningful name (e.g., dv512.YourStudentID)!

// You can implement additional fields and methods in code below, but
// you are not allowed to rename or remove any of it!

// Additionally, please remember that you are not allowed to use any third-party libraries

public class MultithreadedService {

    // TODO: implement a nested public class titled Task here
    // which must have an integer ID and specified burst time (duration) in milliseconds,
    // see below
    // Add further fields and methods to it, if necessary
    // As the task is being executed for the specified burst time, 
    // it is expected to simply go to sleep every X milliseconds (specified below)
  private class Task implements Runnable {
    
    private Integer Id;
		private Long burstTime;
		private Long startTime = null;
		private Long finishTime = null;

    /**
    * Creates an instance of a runnable task object.
    */
		private Task() {
			this.Id = taskCount;
			this.burstTime = rndBurstTime(); 	
		}

    /**
    * Sets the start time for the task object.
    */
		public void setStartTime() {
      this.startTime = System.currentTimeMillis() - simStartTime;
		}

    /**
    * Sets the finish time for the task object.
    */
		public void setFinishTime() {
			this.finishTime = System.currentTimeMillis() - simStartTime;
		}

    /**
    * Returns the tasks ID.
    */
    public Integer getId() {
      return Id;
    }

    /**
    * Returns the tasks burst time.
    */
    public Long getBurstTime() {
      return burstTime;
    }
    
    /**
    * Returns the finih time of the task.
    */
    public Long getFinishTime() {
      return finishTime;
    }
    
    /**
    * Returns the start time of the task.
    */
    public Long getStartTime() {
      return startTime;
    }

    /**
    * Runs the task. Sleeps the amount of burt time the task has.
    */
		@Override  
    public void run() {
       try {
        setStartTime();
        interrupted.add(this);
        Thread.sleep(burstTime);
        setFinishTime();
        completed.add(this);
        interrupted.remove(this);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        //System.out.println("Error!  " + Thread.currentThread().getName() +  ": Task " + Id + " was interrupted!");
      }
		}	

  }

    // Random number generator that must be used for the simulation
	Random rng;
  Long simStartTime;
	Long minBurstTimeMs;
	Long maxBurstTimeMs;
	private Integer taskCount = 0;
  private List<Task> completed = new ArrayList<Task>();
  private List<Task> interrupted = new ArrayList<Task>();
  private List<Runnable> waiting = new ArrayList<Runnable>();

    // ... add further fields, methods, and even classes, if necessary
    
  /**
  * Generates a random number generator and puts it in the rng field.
  */  
	public MultithreadedService (long rngSeed) {
        this.rng = new Random(rngSeed);
    }

  /**
  * Resets simulation data from previous simulation.
  */  
	public void reset() {
		this.taskCount = 0;
    this.completed.clear();
    this.interrupted.clear();
    this.waiting.clear();  
    }

  /**
  * Generates a burst time in given span.
  */  
	public Long rndBurstTime() {
    int min = Math.toIntExact(minBurstTimeMs);
    int max = Math.toIntExact(maxBurstTimeMs);
		long bt = rng.nextInt((max - min) + 1) + min;
    //long bt = ThreadLocalRandom.current().nextLong(minBurstTimeMs, maxBurstTimeMs+1);  //generates long in given range, found on stackoverflow
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
          cpu.execute(t);  
        }

        // limit simulation time
				while(System.currentTimeMillis() - simStartTime < totalSimulationTimeMs) {
				}

        // shutdown cpu and return list of unprocessed tasks
        try {
          this.waiting = cpu.shutdownNow();  
         } catch (SecurityException e) {
           System.out.println("Error! An unexpected error has acured while shutting down cpu.");
         }
				
        while (!cpu.isTerminated()) { //wait for all processes to terminate.
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

    /**
    * Print simulation results.
    */
    public void printResults() {
      
        // TODO:
        
        // 1. For each *completed* task, print its ID, burst time (duration),
        // its start time (moment since the start of the simulation), and finish time
        System.out.println("Completed tasks:");
        System.out.printf("%-10s %-25s %-25s %-25s", "ID: ", "Burst time(ms): ", "Start time(ms): ", "Finish time(ms): ");  
        for (int i = 0; i < completed.size(); i++) {
          Task t = completed.get(i);
          System.out.printf("%n   %-10d %-25d %-25d %-25d", t.getId(), t.getBurstTime(), t.getStartTime(), t.getFinishTime());          
        }
        
        // 2. Afterwards, print the list of tasks IDs for the tasks which were currently
        // executing when the simulation was finished/interrupted
        System.out.println("\nInterrupted tasks:");
        System.out.printf("%-10s %-25s %-25s", "ID: ", "Burst time(ms): ", "Start time(ms): ");  
        for (int i = 0; i < interrupted.size(); i++) {
          Task t = interrupted.get(i);
          System.out.printf("%n   %-10d %-25d %-25d", t.getId(), t.getBurstTime(), t.getStartTime());          
        }
        
        // 3. Finally, print the list of tasks IDs for the tasks which were waiting for execution,
        // but were never started as the simulation was finished/interrupted
        System.out.println("\nWaiting tasks:");
        System.out.printf("%-10s %-25s", "ID: ", "Burst time(ms): ");  
        for (int i = 0; i < waiting.size(); i++) {
          Task t = (Task) waiting.get(i);
          System.out.printf("%n   %-10d %-25d", t.getId(), t.getBurstTime());          
        }
	}

    // If the implementation requires your code to throw some exceptions, 
    // you are allowed to add those to the signature of this method
    public static void main(String args[]) {
		// TODO: replace the seed value below with your birth date, e.g., "20001001"
		final long rngSeed = 19910126;  
				
        // Do not modify the code below — instead, complete the implementation
        // of other methods!
        MultithreadedService service = new MultithreadedService(rngSeed);
        
        final int numSimulations = 4;                       
        final long totalSimulationTimeMs = 15*1000L;     
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
        
        // If your program has not completed after  the message printed above,
        // it means that some threads are not properly stopped! -> this issue will affect the grade
    }
}

