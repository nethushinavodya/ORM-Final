package bo.custom;

import bo.SuperBO;
import dto.PatientDto;
import dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBO extends SuperBO {
    boolean registerUser(UserDto userdto);

    UserDto loginUser(String username);

    List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException;

    boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException;

    boolean addUser(UserDto userDto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    UserDto searchUser(String id);

    UserDto getData(String username);
}
