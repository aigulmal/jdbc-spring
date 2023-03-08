package ru.itgirl.jdbcspringexample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.itgirl.jdbcspringexample.controller.BookController;
import ru.itgirl.jdbcspringexample.model.Book;
import org.springframework.stereotype.Service;
import ru.itgirl.jdbcspringexample.repository.BookRepository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    private DataSource dataSource;

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAllBooks() {

        List<Book> result = new ArrayList<>();

        String SQL_findAllBooks = "select * from books;";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {

            while (resultSet.next()) {
                Book book = convertRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public Book findBookById(Long id) {
       Book result = new Book();

        String SQL_findAllBooks = "select * from books where id=id;";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            System.out.println(resultSet);
            while (resultSet.next()) {
                Book book = convertRowToBook(resultSet);
                result = book;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private Book convertRowToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Book(id, name);
    }
}
