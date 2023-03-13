package universidade.ComputacaoParalela.ThreadPool;

import lombok.Getter;
import lombok.Setter;

import java.util.Deque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;






@Getter
@Setter
public class WorkerThread extends Thread {
    // holds tasks
    private LinkedBlockingDeque<Runnable> taskQueue;

    // check if shutdown is initiated
    private CustomThreadPool threadPool;

    public WorkerThread(LinkedBlockingDeque<Runnable> taskQueue, CustomThreadPool threadPool) {
        this.taskQueue = taskQueue;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        try {
            // continue until all tasks finished processing
            while (!threadPool.getIsThreadPoolShutDownInitiated().get() || !taskQueue.isEmpty()) {

                Runnable r;
                // Poll a runnable from the queue and execute it
                while ((r = taskQueue.poll()) != null) {
                   // System.out.println("Task a ser Executada: " + r);
                    r.run();
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






//public class Worker {
//    Thread;
//    Deque<String> deque;

//@Getter
//@Setter
//    public class WorkerThread extends Thread {
//        // holds tasks
//      //  private BlockingQueue<Runnable> taskQueue;
//
//        // check if shutdown is initiated
//        private LinkedBlockingDeque deque;
//
//        public WorkerThread(LinkedBlockingDeque deque) {
//           // this.taskQueue = taskQueue;
//            this.deque = deque;
//        }
//
//    }
