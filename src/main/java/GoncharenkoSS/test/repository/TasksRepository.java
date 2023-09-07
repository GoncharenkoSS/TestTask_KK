package GoncharenkoSS.test.repository;

import GoncharenkoSS.test.model.Tasks;
import GoncharenkoSS.test.model.Workers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


@Repository
public class TasksRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int assignWorker(int id, int performer) {
        return jdbcTemplate.update("UPDATE tasks SET performer=? WHERE id=?", performer, id);
    }

    public Tasks findById(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tasks.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int update(Tasks tasks) {
        return jdbcTemplate.update("UPDATE tasks SET title=?, description=?, time=?, status=? WHERE id=?",
                tasks.getTitle(), tasks.getDescription(), tasks.getTime(), tasks.getStatus(), tasks.getId());

    }

    public List<Tasks> findAll() {
        return jdbcTemplate.query("SELECT id, title, status from tasks", BeanPropertyRowMapper.newInstance(Tasks.class));
    }

    public boolean save(Tasks tasks) {
        try {
            jdbcTemplate.update("INSERT INTO tasks (title, description, time, status) VALUES(?,?,?,?)",
                    tasks.getTitle(), tasks.getDescription(), tasks.getTime(), tasks.getStatus());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
