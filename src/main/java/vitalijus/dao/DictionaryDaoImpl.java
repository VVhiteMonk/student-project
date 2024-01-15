package vitalijus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vitalijus.config.Config;
import vitalijus.domain.CountryArea;
import vitalijus.domain.PassportOffice;
import vitalijus.domain.RegisterOffice;
import vitalijus.domain.Street;
import vitalijus.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements  DictionaryDao{

    private static final Logger logger = LoggerFactory.getLogger(DictionaryDao.class);

    private static final String GET_STREET = "select street_code, street_name from jc_street where UPPER(street_name) like UPPER(?)";
    private static final String GET_PASSPORT = "select * from jc_passport_office where p_office_area_id = ?";
    private static final String GET_REGISTER = "select * from jc_register_office where r_office_area_id = ?";
    private static final String GET_AREA = "select * from jc_country_struct where area_id like ? and area_id <> ?";


    private Connection getConnection() throws SQLException {
       return ConnectionBuilder.getConnection();
    }
    public List<Street> findStreet(String pattern) throws DaoException {
        List<Street> result = new LinkedList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_STREET)){

            statement.setString(1, "%" + pattern +"%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Street street = new Street(resultSet.getLong("street_code"), resultSet.getString("street_name"));
                result.add(street);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException (ex);
        }
        return result;
    }

    @Override
    public List<PassportOffice> findPassportOffice(String areaId) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_PASSPORT)){

            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                PassportOffice passportOffice = new PassportOffice(resultSet.getLong("p_office_id"),
                        resultSet.getString("p_office_area_id"),
                        resultSet.getString("p_office_name"));
                result.add(passportOffice);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException (ex);
        }
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffice(String areaId) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_REGISTER)){

            statement.setString(1, areaId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                RegisterOffice registerOffice = new RegisterOffice(resultSet.getLong("r_office_id"),
                        resultSet.getString("r_office_area_id"),
                        resultSet.getString("r_office_name"));
                result.add(registerOffice);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException (ex);
        }
        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_AREA)){

            String param = buildParam(areaId);
            String param2 = areaId;

            statement.setString(1, param);
            statement.setString(2, param2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CountryArea countryArea = new CountryArea(resultSet.getString("area_id"),
                        resultSet.getString("area_name"));
                result.add(countryArea);
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException (ex);
        }
        return result;
    }

    private String buildParam(String areaId) throws SQLException{
        if (areaId == null || areaId.trim().isEmpty()){
            return "__0000000000";
        }else if (areaId.endsWith("0000000000")){
           return areaId.substring(0,2) + "___0000000";
        }else if (areaId.endsWith("0000000")){
            return areaId.substring(0, 5) + "___0000";
        }else if (areaId.endsWith("0000")){
            return areaId.substring(0,8) +"____";
        }
        throw new SQLException("Invalid parameter areaID : " + areaId);
    }
}
