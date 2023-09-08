package GoncharenkoSS.test.repository;

import GoncharenkoSS.test.model.Tasks;
import GoncharenkoSS.test.model.Workers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class WorkersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WorkersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Workers workers) {
        jdbcTemplate.update("INSERT INTO workers (name, position, avatar) VALUES(?,?,?)",
                workers.getName(), workers.getPosition(), workers.getAvatar());
    }

    public void update(Workers workers) {
        jdbcTemplate.update("UPDATE workers SET name=?, position=?, avatar=? WHERE id=?",
                workers.getName(), workers.getPosition(), workers.getAvatar(), workers.getId());
    }

    public Workers findById(int id) {
        try {
            Workers workers = jdbcTemplate.queryForObject("SELECT * FROM workers WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Workers.class), id);

            List<Tasks> tasks = jdbcTemplate.query("SELECT title, description, status FROM tasks WHERE performer=?",
                    BeanPropertyRowMapper.newInstance(Tasks.class), id);

            assert workers != null;
            workers.setListTasks(tasks);
            return workers;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Workers findByName(String name) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM workers WHERE name=?",
                    BeanPropertyRowMapper.newInstance(Workers.class), name);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public List<Workers> findAll() {
        List<Workers> workersList = jdbcTemplate.query("SELECT * from workers", BeanPropertyRowMapper.newInstance(Workers.class));
        if (!workersList.isEmpty()) {
            for (Workers worker : workersList) {
                List<Tasks> tasks = jdbcTemplate.query("SELECT title, description, status FROM tasks WHERE performer=?",
                        BeanPropertyRowMapper.newInstance(Tasks.class), worker.getId());
                worker.setListTasks(tasks);
            }
            return workersList;
        }
        return null;
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM workers WHERE id=?", id);
    }

    public int deleteAll() {
        return jdbcTemplate.update("DELETE from workers");
    }
}

