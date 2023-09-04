package GoncharenkoSS.test.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class WorkersDAO {
    @Component
    public class PersonDAO {
        private final JdbcTemplate jdbcTemplate;
        @Autowired
        public PersonDAO(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public List<Person> index() {
            return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
        }
        public Person show(int id){
            return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new BeanPropertyRowMapper<>(Person.class),
                    new Object[]{id}).stream().findAny().orElse(null);
        }
        public void save(Person person){
            jdbcTemplate.update("INSERT INTO Person(name, year) VALUES(?,?)",
                    person.getName(), person.getYear());
        }
        public void update(int id, Person person) {
            jdbcTemplate.update("UPDATE Person SET name=?,year=? WHERE id=?",
                    person.getName(), person.getYear(), id);
        }
        public void delete(int id){
            jdbcTemplate.update("DELETE FROM Person WHERE id=?",id);
        }

        public List<Book> getBookByPersonId(int id){
            return jdbcTemplate.query("SELECT * FROM Book WHERE id_person=?",
                    new BeanPropertyRowMapper<>(Book.class), id);
        }
    }
}
