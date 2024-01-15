package vitalijus;

import vitalijus.dao.StudentDaoImpl;
import vitalijus.domain.children.AnswerChildren;
import vitalijus.domain.register.AnswerCityRegister;
import vitalijus.domain.student.AnswerStudent;
import vitalijus.domain.wedding.AnswerWedding;
import vitalijus.domain.StudentOrder;
import vitalijus.exception.DaoException;
import vitalijus.mail.MailSender;
import vitalijus.validator.AnswerChildrenValidator;
import vitalijus.validator.AnswerStudentValidator;
import vitalijus.validator.AnswerWeddingValidator;
import vitalijus.validator.CityRegisterValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {

    private CityRegisterValidator cityRegisterValidator;
    private AnswerWeddingValidator answerWeddingValidator;
    private AnswerChildrenValidator answerChildrenValidator;
    private AnswerStudentValidator answerStudentValidator;
    private MailSender mailSender;

    public StudentOrderValidator(){
        cityRegisterValidator = new CityRegisterValidator();
        answerWeddingValidator = new AnswerWeddingValidator();
        answerChildrenValidator = new AnswerChildrenValidator();
        answerStudentValidator = new AnswerStudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator studentOrderValidator = new StudentOrderValidator();
        studentOrderValidator.checkAll();
    }

    public void checkAll() {
        try {
            List<StudentOrder> studentOrderList = readStudentOrders();

            for (StudentOrder studentOrder : studentOrderList) {
                System.out.println();
                checkOneOrder(studentOrder);
            }
        }catch (Exception ex){
            ex.printStackTrace(System.out);
        }
    }

    public List<StudentOrder> readStudentOrders() throws DaoException {
        return new StudentDaoImpl().getStudentOrder();
    }

    public void checkOneOrder(StudentOrder studentOrder){
        AnswerCityRegister answerCityRegister = checkCityRegister(studentOrder);
        //AnswerWedding answerWedding = checkWedding(studentOrder);
        //AnswerChildren answerChildren = checkChildren(studentOrder);
        //AnswerStudent answerStudent = checkStudent(studentOrder);

        //sendMail(studentOrder);
    }



    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder){
        return cityRegisterValidator.checkCityRegister(studentOrder);
    }

    public AnswerWedding checkWedding(StudentOrder studentOrder){
        return answerWeddingValidator.checkWedding(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder){
        return answerChildrenValidator.checkChildren(studentOrder);
    }

    public AnswerStudent checkStudent(StudentOrder studentOrder){
        return answerStudentValidator.checkStudent(studentOrder);
    }
    public void sendMail(StudentOrder studentOrder){
        mailSender.sendMail(studentOrder);
    }
}
