package GoncharenkoSS.test.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class TasksDAO {
    @Component
    public class BookDAO {
        private final JdbcTemplate jdbcTemplate;
        @Autowired
        public BookDAO(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
        public List<Book> index() {
            return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
        }
        public Book show(int id){
            return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new BeanPropertyRowMapper<>(Book.class),
                    new Object[]{id}).stream().findAny().orElse(null);
        }
        public void save(Book book){
            jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES(?,?,?)",
                    book.getName(), book.getAuthor(), book.getYear());
        }
        public void update(int id, Book book) {
            jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?",
                    book.getName(), book.getAuthor(), book.getYear(), id);
        }
        public void delete(int id){
            jdbcTemplate.update("DELETE FROM Book WHERE id=?",id);
        }

        public Optional<Person> getBookOwner(int id){
            return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.id_person=Person.id " +
                    "WHERE Book.id=?", new BeanPropertyRowMapper<>(Person.class),  new Object[]{id}).stream().findAny();
        }

        public void release(int id){
            jdbcTemplate.update("UPDATE Book SET id_person=NULL WHERE id=?", id);
        }

        public void assign(int id, Person select){
            jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id=?", select.getId(), id);
        }
    }
}
