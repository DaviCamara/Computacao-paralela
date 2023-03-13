package universidade.ComputacaoParalela.ThreadPool;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


@Getter
@Setter
public class CustomThreadPool {

    // holds tasks
    private LinkedBlockingDeque<Runnable> runnableQueue;

    // holds the pool of worker threads
    private List<WorkerThread> threads;

    private AtomicBoolean isThreadPoolShutDownInitiated;
    public CustomThreadPool(final int noOfThreads) {
        this.runnableQueue = new LinkedBlockingDeque<>(3);
        this.threads = new ArrayList<>(noOfThreads);
        this.isThreadPoolShutDownInitiated = new AtomicBoolean(false);

        // create worker threads
        for (int i = 1; i <= noOfThreads; i++) {
            WorkerThread thread = new WorkerThread(runnableQueue, this);
            thread.setName("Worker Thread - " + i);
            thread.start();
            threads.add(thread);
        }
    }

    //Da forma que está aqui a lista de Tasks é dividida pelas Threads (?) as Threads executando as tarefas da Pool base
    //ado na existencia de tarefas na Thread, caso ainda tenha tarefas no deque ela continua puxando, caso o deque não
    //esteja cheio a ThreadPool adiciona uma nova tarefa ao deque.
     public void execute (Runnable r) throws InterruptedException {
            if (!isThreadPoolShutDownInitiated.get()) {
                System.out.println("NUMBER OF TASKS ON DEQUE ANTES: " + runnableQueue.size());
                runnableQueue.put(r);
                System.out.println("NUMBER OF TASKS ON DEQUE DEPOIS: " + runnableQueue.size());
            } else {
                throw new InterruptedException("Thread Pool shutdown is initiated, unable to execute task");
            }
        }


    public void shutdown() {
        isThreadPoolShutDownInitiated = new AtomicBoolean(true);
    }

}
//public class CustomThreadPool {
//    //holds Task
//    private BlockingQueue<Runnable> task;
//
//    //holds Pools of workers.
//    private List<WorkerThread> workers;
//    //Timer timer;
//
//    public CustomThreadPool(final int threadNumbers){
//        this.task = new LinkedBlockingQueue<>();
//        this.workers = new ArrayList<WorkerThread>(threadNumbers);
//
////loop creation of workers
//        for (int i = 1; i <= threadNumbers; i++) {
//           // WorkerThread thread = new WorkerThread(task, this);
//            LinkedBlockingDeque deque = new LinkedBlockingDeque();
//            WorkerThread worker = new WorkerThread(deque);
//            worker.setName("Worker Thread - " + i);
//            worker.start();
//            workers.add(worker);
//        }
//    }
//
//
//}
