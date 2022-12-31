import java.sql.SQLException;

public interface TokoLukisanLupi { 

    void show() throws SQLException;
    void insert() throws SQLException;
    void edit() throws SQLException;
    void delete();
    void search() throws SQLException;

}