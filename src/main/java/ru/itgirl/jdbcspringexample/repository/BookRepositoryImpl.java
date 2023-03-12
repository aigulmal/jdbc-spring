package ru.itgirl.jdbcspringexample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.itgirl.jdbcspringexample.model.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public abstract class BookRepositoryImpl implements BookRepository {
    @Autowired
    private DataSource dataSource;


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

        try (Connection connection = dataSource.getConnection();
            ) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books where id =?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery(); {
                while (resultSet.next()) {
                    Book book = convertRowToBook(resultSet);
                    result = book;
                }
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
