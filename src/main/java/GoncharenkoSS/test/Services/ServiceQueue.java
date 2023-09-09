package GoncharenkoSS.test.Services;

import GoncharenkoSS.test.model.Tasks;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public interface ServiceQueue {
    ArrayBlockingQueue<Tasks> tasksQueue = new ArrayBlockingQueue<>(100);
}



