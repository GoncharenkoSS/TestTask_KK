package GoncharenkoSS.test.controller;

import GoncharenkoSS.test.Services.ServiceErrors;
import GoncharenkoSS.test.Services.ServiceQueue;
import GoncharenkoSS.test.model.Tasks;
import GoncharenkoSS.test.model.Workers;
import GoncharenkoSS.test.repository.TasksRepository;
import GoncharenkoSS.test.repository.WorkersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController implements ServiceQueue {

    private final TasksRepository tasksRepository;
    private final WorkersRepository workersRepository;
    private final ServiceErrors serviceErrors;

    @Autowired
    public TaskController(TasksRepository tasksRepository, WorkersRepository workersRepository, ServiceErrors serviceErrors) {
        this.tasksRepository = tasksRepository;
        this.workersRepository = workersRepository;
        this.serviceErrors = serviceErrors;
    }

    //Метод назначает исполнителя на задачу по имени.
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<String> assignTask(@PathVariable("id") int id, @RequestBody Workers workers) {

        if (tasksRepository.findById(id) == null)
            return new ResponseEntity<>("Cannot task with id=" + id, HttpStatus.NOT_FOUND);

        Workers work = workersRepository.findByName(workers.getName());

        if (work != null) {
            tasksRepository.assignWorker(id, work.getId());
            return new ResponseEntity<>(workers.getName() + " successfully assigned to task.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot worker with name=" + workers.getName(), HttpStatus.NOT_FOUND);
        }
    }

    //Метод меняющий задачу по ID (все кроме id и performer).
    @PatchMapping("/change-task/{id}")
    public ResponseEntity<String> changeTask(@PathVariable("id") int id, @RequestBody @Valid Tasks tasks,
                                             BindingResult bindingResult) {
        Tasks task = tasksRepository.findById(id);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(serviceErrors.returnErrors(bindingResult), HttpStatus.FORBIDDEN);
        } else if (task != null) {
            task.setTitle(tasks.getTitle());
            task.setDescription(tasks.getDescription());
            task.setTime(Time.valueOf(LocalTime.now()));
            task.setStatus(tasks.getStatus());
            tasksRepository.update(task);
            return new ResponseEntity<>("Task =" + tasks.getTitle() + "= successfully updated.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot task with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    //Поиск задачи по ID.
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable("id") int id) {
        Tasks tasks = tasksRepository.findById(id);

        if (tasks != null) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Получить список всех задач.
    @GetMapping("/tasks")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        try {
            List<Tasks> tasks = tasksRepository.findAll();

            if (tasks.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NO_CONTENT, "no content");
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Создать задачу
    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@RequestBody @Valid Tasks tasks, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(serviceErrors.returnErrors(bindingResult), HttpStatus.FORBIDDEN);
        } else if (tasks == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            tasksQueue.add(tasks);
            System.out.println(tasksQueue);
            executorService.execute(() -> {
                if (!tasksQueue.isEmpty()) {
                    if (tasksRepository.save(tasksQueue.peek())) tasksQueue.remove();
                    System.out.println(tasksQueue);
                }
            });
            System.out.println(tasksQueue);
            return new ResponseEntity<>("Task was created successfully.", HttpStatus.CREATED);
        }
    }
}
