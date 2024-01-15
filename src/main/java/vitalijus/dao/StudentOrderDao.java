package vitalijus.dao;

import vitalijus.domain.StudentOrder;
import vitalijus.exception.DaoException;

import java.util.List;

public interface StudentOrderDao {
    Long saveStudentOrder(StudentOrder studentOrder) throws DaoException;

    List<StudentOrder> getStudentOrder() throws DaoException;
}
