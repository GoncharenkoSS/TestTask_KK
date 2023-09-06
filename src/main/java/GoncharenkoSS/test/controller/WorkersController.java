package GoncharenkoSS.test.controller;

import GoncharenkoSS.test.model.Workers;
import GoncharenkoSS.test.repository.WorkersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class WorkersController {

    @Autowired
    WorkersRepository workersRepository;

    @GetMapping("/workers")
    public ResponseEntity<List<Workers>> getAllWorkers(@RequestParam(required = false) String name) {
        try {
            List<Workers> workers = new ArrayList<>();

            if (name == null)
                workersRepository.findAll().forEach(workers::add);

            if (workers.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NO_CONTENT, "no content");
            }

            return new ResponseEntity<>(workers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/workers/{id}")
    public ResponseEntity<Workers> getWorkerById(@PathVariable("id") int id) {
        Workers workers = workersRepository.findById(id);

        if (workers != null) {
            return new ResponseEntity<>(workers, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PostMapping("/workers")
    public ResponseEntity<String> createWorker(@RequestBody Workers workers) {
        try {
            workersRepository.save(new Workers(workers.getName(), workers.getPosition(), workers.getAvatar()));
            return new ResponseEntity<>("Worker was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/workers/{id}")
    public ResponseEntity<String> updateWorker(@PathVariable("id") int id, @RequestBody Workers workers) {
        Workers work = workersRepository.findById(id);

        if (work != null) {
            work.setId(id);
            work.setName(workers.getName());
            work.setPosition(workers.getPosition());
            work.setAvatar(workers.getAvatar());

            workersRepository.update(work);
            return new ResponseEntity<>("Worker was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Worker with id=" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/workers/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable("id") int id) {
        try {
            int result = workersRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find worker with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Worker was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete worker.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/workers")
    public ResponseEntity<String> deleteAllWorkers() {
        try {
            int numRows = workersRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Worker(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete workers.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}