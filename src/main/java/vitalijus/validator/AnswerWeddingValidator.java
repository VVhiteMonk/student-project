package vitalijus.validator;

import vitalijus.domain.StudentOrder;
import vitalijus.domain.wedding.AnswerWedding;

public class AnswerWeddingValidator {

    private String hostName;

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

    private String login;
    private String password;
    public AnswerWedding checkWedding(StudentOrder studentOrder){
        System.out.println("Weeding is running " + hostName +"," + login+ "," + password);
        AnswerWedding answerWedding = new AnswerWedding();
        answerWedding.success = false;
        return answerWedding;
    }
}
