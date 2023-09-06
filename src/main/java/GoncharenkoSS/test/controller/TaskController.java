package GoncharenkoSS.test.controller;

import GoncharenkoSS.test.model.Tasks;
import GoncharenkoSS.test.model.Workers;
import GoncharenkoSS.test.repository.TasksRepository;
import GoncharenkoSS.test.repository.WorkersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TasksRepository tasksRepository;
    @Autowired
    WorkersRepository workersRepository;

    @PatchMapping("/tasks/{id}")
    public ResponseEntity<String> assignTask(@PathVariable("id") int id, @RequestBody Workers workers) {
        if(tasksRepository.findById(id)==null)
            return new ResponseEntity<>("Cannot task with id=" + id, HttpStatus.NOT_FOUND);

        Workers work = workersRepository.findByName(workers.getName());

        if (work != null) {
            tasksRepository.assignWorker(id, work.getId());
            return new ResponseEntity<>(  workers.getName() + " successfully assigned to task.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot worker with name=" + workers.getName(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable("id") int id) {
        Tasks tasks = tasksRepository.findById(id);

        if (tasks != null) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }
}
