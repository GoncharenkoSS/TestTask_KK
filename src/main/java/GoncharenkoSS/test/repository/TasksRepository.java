package GoncharenkoSS.test.repository;

import GoncharenkoSS.test.model.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;


@Repository
public class TasksRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TasksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignWorker(int id, int performer) {
        jdbcTemplate.update("UPDATE tasks SET performer=? WHERE id=?", performer, id);
    }

    public Tasks findById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tasks.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Tasks> findAll() {
        return jdbcTemplate.query("SELECT id, title, status from tasks", BeanPropertyRowMapper.newInstance(Tasks.class));
    }

    public void update(Tasks tasks) {
        jdbcTemplate.update("UPDATE tasks SET title=?, description=?, time=?, status=? WHERE id=?",
                tasks.getTitle(), tasks.getDescription(), tasks.getTime(), tasks.getStatus(), tasks.getId());
    }

    public boolean save(Tasks tasks) {
        try {
            jdbcTemplate.update("INSERT INTO tasks (title, description, time, status) VALUES(?,?,?,?)",
                    tasks.getTitle(), tasks.getDescription(), Time.valueOf(LocalTime.now()), tasks.getStatus());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
