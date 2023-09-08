package GoncharenkoSS.test.Services;

import GoncharenkoSS.test.model.Tasks;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public interface ServiceQueue {
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    ConcurrentLinkedQueue<Tasks> tasksQueue = new ConcurrentLinkedQueue<>();
}



