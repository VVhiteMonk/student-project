package vitalijus.validator;

import vitalijus.domain.StudentOrder;
import vitalijus.domain.student.AnswerStudent;

public class AnswerStudentValidator {
    private String hostName;
    private String login;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
    public  AnswerStudent checkStudent(StudentOrder studentOrder){
        System.out.println("Students check is running "  + hostName +"," + login+ "," + password);
        AnswerStudent answerStudent = new AnswerStudent();
        answerStudent.success = false;
        return answerStudent;
    }
}
