package ar.edu.itba.paw.webapp.form;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ApplicationForm {

    @NotBlank
    @Size(max = 300)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
