package bo.custom.impl;

import bo.custom.UserBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.PatientDto;
import dto.UserDto;
import entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public boolean registerUser(UserDto userdto) {
        return userDAO.registerUser(new User(userdto.getUsername(),userdto.getEmail(),userdto.getPassword(),userdto.getRole()));
    }

    @Override
    public UserDto loginUser(String username) {
        User user = userDAO.loginUser(username);
        if (user == null){
            return null;
        }else {
            return new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getPassword(),user.getRole());
        }
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> users = userDAO.getAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole()));
        }
        return userDtos;
    }

    @Override
    public boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(userDto.getId(),userDto.getUsername(),userDto.getEmail(),userDto.getPassword(),userDto.getRole()));
    }

    @Override
    public boolean addUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return userDAO.save(new User(userDto.getUsername(),userDto.getEmail(),userDto.getPassword(),userDto.getRole()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public UserDto searchUser(String id) {
        User user = userDAO.search(id);
        if (user == null){
            return null;
        }else {
            return new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole());
        }
    }

    @Override
    public UserDto getData(String username) {
       User user = userDAO.loginUser(username);
       if (user == null){
           return null;
       }else {
           return new UserDto(user.getId(),user.getUsername(),user.getEmail(),user.getRole());
       }
    }
}
