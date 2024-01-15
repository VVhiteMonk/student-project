package vitalijus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vitalijus.config.Config;
import vitalijus.domain.*;
import vitalijus.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDaoImpl implements StudentOrderDao{

    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    private static final String INSERT_ORDER = "INSERT INTO jc_student_order(" +
            "student_order_status, student_order_date, h_sur_name, h_given_name, h_date_of_birth, h_passport_seria, h_passport_number, h_passport_date, h_passport_office_id, h_post_index, h_street_code, h_building, h_apartment, h_university_id, h_student_number, w_sur_name, w_given_name, w_date_of_birth, w_passport_seria, w_passport_number, w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_apartment, w_university_id, w_student_number, certificate_id, register_office_id, marriage_date)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?);";

    private static final String INSERT_CHILD = "INSERT INTO jc_student_child(" +
            "student_order_id, c_sur_name, c_given_name, c_date_of_birth, c_certificate_number, c_certificate_date, c_register_office_id, c_post_index, c_street_code, c_building, c_apartment)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ORDERS =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "po_h.p_office_area_id as h_p_office_area_id, po_h.p_office_name as h_p_office_name, " +
                    "po_w.p_office_area_id as w_p_office_area_id, po_w.p_office_name as w_p_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?";

    private static final String SELECT_CHILD =
            "SELECT soc.*, ro.r_office_area_id, ro.r_office_name " +
                    "from jc_student_child soc " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id " +
                    "WHERE soc.student_order_id IN ";

    private static final String SELECT_ORDERS_FULL =
            "SELECT so.*, ro.r_office_area_id, ro.r_office_name, " +
                    "po_h.p_office_area_id as h_p_office_area_id, po_h.p_office_name as h_p_office_name, " +
                    "po_w.p_office_area_id as w_p_office_area_id, po_w.p_office_name as w_p_office_name, " +
                    "soc.*, ro_c.r_office_area_id, ro_c.r_office_name " +
                    "FROM jc_student_order so " +
                    "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id " +
                    "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id " +
                    "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id " +
                    "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id " +
                    "INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id " +
                    "WHERE student_order_status = ? ORDER BY so.student_order_date LIMIT ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }
    @Override
    public Long saveStudentOrder(StudentOrder studentOrder) throws DaoException {

        Long result = -1L;

        logger.debug("studentOrder:{}", studentOrder);

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"})){

            connection.setAutoCommit(false);

            try {
                //Header
                preparedStatement.setInt(1, StudentOrderStatus.START.ordinal());
                preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                //Husband
                setParamsForAdult(preparedStatement, 3, studentOrder.getHusband());

                //Wife
                setParamsForAdult(preparedStatement, 16, studentOrder.getWife());

                //Marriage
                preparedStatement.setString(29, studentOrder.getMarriageCertificatedId());
                preparedStatement.setLong(30, studentOrder.getMarriageOffice().getOfficeId());
                preparedStatement.setDate(31, java.sql.Date.valueOf(studentOrder.getMarriageDate()));

                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    result = resultSet.getLong(1);
                }
                resultSet.close();

                saveChildren(connection, studentOrder, result);

                connection.commit();
            }catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }

        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }

        return result;

    }

    private void saveChildren(Connection connection, StudentOrder studentOrder, Long studentOrderId) throws SQLException{
        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHILD)) {
            for (Child child : studentOrder.getChildren()){
                preparedStatement.setLong(1, studentOrderId);
                setParamsForChild(preparedStatement, child);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }

    }

    private static void setParamsForAdult(PreparedStatement preparedStatement, int start, Adult adult) throws SQLException {
        setParamsForPerson(preparedStatement, start, adult);
        preparedStatement.setString(start + 3, adult.getPassportSeries());
        preparedStatement.setString(start + 4, adult.getPassportNumber());
        preparedStatement.setDate(start + 5, Date.valueOf(adult.getIssueDate()));
        preparedStatement.setLong(start + 6, adult.getIssueDepartment().getOfficeId());
        setParamsForAddress(preparedStatement, start + 7, adult);
        preparedStatement.setLong(start + 11, adult.getUniversity().getUniversityId());
        preparedStatement.setString(start + 12, adult.getStudentId());
    }

    private void setParamsForChild(PreparedStatement preparedStatement, Child child) throws SQLException{
        setParamsForPerson(preparedStatement, 2, child);
        preparedStatement.setString(5, child.getCertificateNumber());
        preparedStatement.setDate(6, java.sql.Date.valueOf(child.getIssueDate()));
        preparedStatement.setLong(7, child.getIssuesDepartment().getOfficeId());
        setParamsForAddress(preparedStatement, 8, child);
    }
    private static void setParamsForPerson(PreparedStatement preparedStatement, int start, Person person) throws SQLException {
        preparedStatement.setString(start, person.getSurname());
        preparedStatement.setString(start + 1, person.getGiveName());
        preparedStatement.setDate(start + 2, Date.valueOf(person.getDateOfBirth()));
    }
    private static void setParamsForAddress(PreparedStatement preparedStatement, int start, Person person) throws SQLException {
        Address h_address = person.getAddress();
        preparedStatement.setString(start, h_address.getPostCode());
        preparedStatement.setLong(start + 1, h_address.getStreet().getStreetCode());
        preparedStatement.setString(start + 2, h_address.getBuilding());
        preparedStatement.setString(start + 3, h_address.getApartment());
    }

    @Override
    public List<StudentOrder> getStudentOrder() throws DaoException {
        // return getStudentOrdersOneSelect();
        return getStudentOrdersTwoSelect();
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_FULL)){

            Map<Long, StudentOrder> maps = new HashMap<>();

            preparedStatement.setInt(1, StudentOrderStatus.START.ordinal());
            int limit =  Integer.parseInt(Config.getProperties(Config.DB_LIMIT));
            preparedStatement.setInt(2, limit);


            ResultSet resultSet = preparedStatement.executeQuery();

            int counter = 0;

            while (resultSet.next()){

                Long studentOrderId = resultSet.getLong("student_order_id");

                if (!maps.containsKey(studentOrderId)) {

                    StudentOrder studentOrder = getFullStudentOrder(resultSet);

                    result.add(studentOrder);

                    maps.put(studentOrderId, studentOrder);
                }
                StudentOrder studentOrder = maps.get(studentOrderId);
                studentOrder.addChild(fillChild(resultSet));

                counter++;
            }

            if (counter >= limit){
                result.remove(result.size() - 1);
            }

            resultSet.close();

        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }

        return result;
    }



    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();

        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS)){

            preparedStatement.setInt(1, StudentOrderStatus.START.ordinal());
            preparedStatement.setInt(2, Integer.parseInt(Config.getProperties(Config.DB_LIMIT)));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                StudentOrder studentOrder = getFullStudentOrder(resultSet);

                result.add(studentOrder);
            }

            findChildren(connection, result);

            resultSet.close();

        }catch (SQLException ex){
            logger.error(ex.getMessage(), ex);
            throw new DaoException(ex);
        }

        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet resultSet) throws SQLException {
        StudentOrder studentOrder = new StudentOrder();
        fillStudentOrder(resultSet, studentOrder);
        fillMarriage(resultSet, studentOrder);

        studentOrder.setHusband(fillAdult(resultSet, "h_"));
        studentOrder.setWife(fillAdult(resultSet, "w_"));
        return studentOrder;
    }

    private void fillStudentOrder(ResultSet resultSet, StudentOrder studentOrder) throws SQLException{
        studentOrder.setStudentOrderId(resultSet.getLong("student_order_id"));
        studentOrder.setStudentOrderDate(resultSet.getTimestamp("student_order_date").toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));
    }

    private Adult fillAdult(ResultSet resultSet, String sqlPrefix) throws SQLException{
        Adult adult = new Adult();
        adult.setSurname(resultSet.getString(sqlPrefix + "sur_name"));
        adult.setGiveName(resultSet.getString(sqlPrefix + "given_name"));
        adult.setDateOfBirth(resultSet.getDate(sqlPrefix + "date_of_birth").toLocalDate());
        adult.setPassportSeries(resultSet.getString(sqlPrefix + "passport_seria"));
        adult.setPassportNumber(resultSet.getString(sqlPrefix + "passport_number"));
        adult.setIssueDate(resultSet.getDate(sqlPrefix + "passport_date").toLocalDate());

        Long passportOfficeId = resultSet.getLong(sqlPrefix + "passport_office_id");
        String passportOfficeArea = resultSet.getString(sqlPrefix + "p_office_area_id");
        String passportOfficeName = resultSet.getString(sqlPrefix + "p_office_name");
        PassportOffice passportOffice = new PassportOffice(passportOfficeId, passportOfficeArea, passportOfficeName);
        adult.setIssueDepartment(passportOffice);

        Address address = new Address();
        Street street = new Street(resultSet.getLong(sqlPrefix + "street_code"), "");
        address.setStreet(street);
        address.setPostCode(resultSet.getString(sqlPrefix + "post_index"));
        address.setBuilding(resultSet.getString(sqlPrefix + "building"));
        address.setApartment(resultSet.getString(sqlPrefix + "apartment"));
        adult.setAddress(address);

        University university = new University(resultSet.getLong(sqlPrefix + "university_id"), "");
        adult.setUniversity(university);
        adult.setStudentId(resultSet.getString(sqlPrefix + "student_number"));



        return adult;
    }

    private void fillMarriage(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificatedId(resultSet.getString("certificate_id"));
        studentOrder.setMarriageDate(resultSet.getDate("marriage_date").toLocalDate());

        Long registerOfficeId = resultSet.getLong("register_office_id");
        String areaId = resultSet.getString("r_office_area_id");
        String name = resultSet.getString("r_office_name");
        RegisterOffice registerOffice = new RegisterOffice(registerOfficeId, areaId, name);
        studentOrder.setMarriageOffice(registerOffice);
    }

    private void findChildren(Connection connection, List<StudentOrder> result) throws SQLException{
        String collect = "(" + result.stream().map(studentOrder -> String.valueOf(studentOrder.getStudentOrderId()))
                .collect(Collectors.joining(",")) + ")";

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors
                .toMap(studentOrder -> studentOrder.getStudentOrderId(), studentOrder -> studentOrder));

        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CHILD + collect)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Child child = fillChild(resultSet);
                StudentOrder studentOrderId =  maps.get(resultSet.getLong("student_order_id"));
                studentOrderId.addChild(child);
            }
        }
    }

    private Child fillChild(ResultSet resultSet) throws SQLException{
        String surname = resultSet.getString("c_sur_name");
        String giveName = resultSet.getString("c_given_name");
        LocalDate dateOfBirth = resultSet.getDate("c_date_of_birth").toLocalDate();

        Child child = new Child(surname, giveName, dateOfBirth);

        child.setCertificateNumber(resultSet.getString("c_certificate_number"));
        child.setIssueDate(resultSet.getDate("c_certificate_date").toLocalDate());

        Long registerOfficeId = resultSet.getLong("c_register_office_id");
        String registerOfficeArea = resultSet.getString("r_office_area_id");
        String registerOfficeName = resultSet.getString("r_office_name");
        RegisterOffice registerOffice = new RegisterOffice(registerOfficeId, registerOfficeArea, registerOfficeName);
        child.setIssuesDepartment(registerOffice);

        Address address = new Address();
        Street street = new Street(resultSet.getLong("c_street_code"), "");
        address.setStreet(street);
        address.setPostCode(resultSet.getString( "c_post_index"));
        address.setBuilding(resultSet.getString("c_building"));
        address.setApartment(resultSet.getString("c_apartment"));
        child.setAddress(address);

        return child;
    }
}
