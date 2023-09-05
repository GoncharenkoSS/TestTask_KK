package GoncharenkoSS.test.repository;


import GoncharenkoSS.test.model.ShortTask;
import GoncharenkoSS.test.model.Tasks;
import GoncharenkoSS.test.model.Workers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class WorkersRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Workers workers) {
        return jdbcTemplate.update("INSERT INTO workers (name, position, avatar) VALUES(?,?,?)",
                new Object[]{workers.getName(), workers.getPosition(), workers.getAvatar()});
    }

    public int update(Workers workers) {
        return jdbcTemplate.update("UPDATE workers SET name=?, position=?, avatar=? WHERE id=?",
                new Object[]{workers.getName(), workers.getPosition(), workers.getAvatar(), workers.getId()});
    }

    public Workers findById(int id) {
        try {
            Workers workers = jdbcTemplate.queryForObject("SELECT * FROM workers WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Workers.class), id);

            List<ShortTask> tasks = jdbcTemplate.query("SELECT title, description, status FROM tasks WHERE performer=?",
                    BeanPropertyRowMapper.newInstance(ShortTask.class), id);

            assert workers != null;
            workers.setListTasks(tasks);
            return workers;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM workers WHERE id=?", id);
    }

    public List<Workers> findAll() {
        return jdbcTemplate.query("SELECT * from workers", BeanPropertyRowMapper.newInstance(Workers.class));
    }

    public int deleteAll() {
        return jdbcTemplate.update("DELETE from workers");
    }
}

