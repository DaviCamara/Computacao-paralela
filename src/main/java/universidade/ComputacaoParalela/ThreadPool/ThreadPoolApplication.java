package universidade.ComputacaoParalela.ThreadPool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Timer;

@SpringBootApplication
public class ThreadPoolApplication {

    public static void main(String[] args) throws InterruptedException {

            Runnable r = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " is executing task. --- " + "THREAD TO STRING: " + Thread.currentThread().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };


        CustomThreadPool threadPool = new CustomThreadPool(2);


        for (int i = 0; i <= 50; i++) {
        threadPool.execute(r);
        }
        threadPool.shutdown();

    }

}
