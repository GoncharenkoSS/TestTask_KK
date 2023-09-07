package GoncharenkoSS.test.Services;

import GoncharenkoSS.test.model.Tasks;
import GoncharenkoSS.test.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ServiceQueue {
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final ConcurrentLinkedQueue<Tasks> tasksQueue = new ConcurrentLinkedQueue<>();
    private final TasksRepository tasksRepository;

    @Autowired
    public ServiceQueue(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public void putInQueue(Tasks tasks) {
        tasksQueue.add(tasks);
        System.out.println(tasksQueue);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fromQueueInDB() {
        executorService.submit(() -> {
            assert tasksQueue.peek() != null;
            if(tasksRepository.save(tasksQueue.peek())) tasksQueue.poll();
        });
    }
}
