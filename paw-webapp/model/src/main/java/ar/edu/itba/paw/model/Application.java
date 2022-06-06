package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "applications")
public class Application {

    // TODO: HACE FALTA PATRON BUILDER? ESTA MAL IMPLEMENTADO CREO

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applications_id_seq")
    @SequenceGenerator(name = "applications_id_seq", sequenceName = "applications_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditionId")
    private Audition audition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicantId")
    private User applicant;

    @Enumerated(EnumType.STRING)
    private ApplicationState state;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @Column(length = 300, nullable = false)
    private String message;

    /* Default */ Application() {
        // Just for Hibernate
    }

    public Application(ApplicationBuilder builder) {
        this.applicant = builder.applicant;
        this.audition = builder.audition;
        this.state = builder.state;
        this.creationDate = builder.creationDate;
        this.id = builder.id;
        this.message = builder.message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Audition getAudition() {
        return audition;
    }

    public User getApplicant() {
        return applicant;
    }

    public ApplicationState getState() {
        return state;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) &&
                state == that.state &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audition, applicant, state, creationDate, message);
    }

    public static class ApplicationBuilder {
        private Long id;
        private Audition audition;
        private User applicant;
        private ApplicationState state;
        private LocalDateTime creationDate;
        private String message;

        public ApplicationBuilder(Audition audition, User applicant, ApplicationState state, LocalDateTime creationDate, String message) {
            this.audition = audition;
            this.applicant = applicant;
            this.state = state;
            this.creationDate = creationDate;
            this.message = message;
        }

        public ApplicationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public Audition getAudition() {
            return audition;
        }

        public void setAudition(Audition audition) {
            this.audition = audition;
        }

        public User getApplicant() {
            return applicant;
        }

        public void setApplicant(User applicant) {
            this.applicant = applicant;
        }

        public ApplicationState getState() {
            return state;
        }

        public void setState(ApplicationState state) {
            this.state = state;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
        }

        public Application build() {
            return new Application(this);
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
