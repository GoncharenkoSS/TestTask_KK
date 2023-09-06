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
public class TasksRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int assignWorker(int id, int performer) {
        return jdbcTemplate.update("UPDATE tasks SET performer=? WHERE id=?", performer, id);
    }

    public Tasks findById(int id) {
        try {
            Tasks tasks = jdbcTemplate.queryForObject("SELECT * FROM tasks WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tasks.class), id);

            return tasks;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}
