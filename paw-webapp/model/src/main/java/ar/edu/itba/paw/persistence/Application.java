package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.Objects;

public class Application {
    private final long auditionId;
    private final long applicantId;
    private final String applicantName;
    private final String applicantSurname;
    private final ApplicationState state;
    private final String auditionTitle;
    private final LocalDateTime creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return getAuditionId() == that.getAuditionId() && getApplicantId() == that.getApplicantId() && Objects.equals(getApplicantName(), that.getApplicantName()) && Objects.equals(getApplicantSurname(), that.getApplicantSurname()) && getState() == that.getState() && Objects.equals(getAuditionTitle(), that.getAuditionTitle()) && Objects.equals(getCreationDate(), that.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuditionId(), getApplicantId(), getApplicantName(), getApplicantSurname(), getState(), getAuditionTitle(), getCreationDate());
    }

    private Application(ApplicationBuilder builder) {
        this.auditionId = builder.auditionId;
        this.applicantId = builder.applicantId;
        this.applicantName = builder.applicantName;
        this.applicantSurname = builder.applicantSurname;
        this.state = builder.state;
        this.auditionTitle = builder.auditionTitle;
        this.creationDate = builder.creationDate;

    }

    public long getAuditionId() {
        return auditionId;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantSurname() {
        return applicantSurname;
    }

    public ApplicationState getState() {
        return state;
    }

    public String getAuditionTitle() {
        return auditionTitle;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public static class ApplicationBuilder {
        private final long auditionId;
        private final long applicantId;
        private String applicantName;
        private String applicantSurname;
        private final ApplicationState state;
        private String auditionTitle;
        private final LocalDateTime creationDate;

        public ApplicationBuilder(long auditionId, long applicantId, ApplicationState state, LocalDateTime creationDate) {
            this.auditionId = auditionId;
            this.applicantId = applicantId;
            this.state = state;
            this.creationDate = creationDate;
        }

        public ApplicationBuilder applicantName(String applicantName) {
            this.applicantName = applicantName;
            return this;
        }

        public ApplicationBuilder applicantSurname(String applicantSurname) {
            this.applicantSurname = applicantSurname;
            return this;
        }

        public ApplicationBuilder auditionTitle(String auditionTitle) {
            this.auditionTitle = auditionTitle;
            return this;
        }

        public LocalDateTime getCreationDate() {
            return creationDate;
        }

        public long getAuditionId() {
            return auditionId;
        }

        public long getApplicantId() {
            return applicantId;
        }

        public String getApplicantName() {
            return applicantName;
        }

        public String getApplicantSurname() {
            return applicantSurname;
        }

        public ApplicationState getState() {
            return state;
        }

        protected Application build() {
            return new Application(this);
        }
    }
}
