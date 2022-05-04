package ar.edu.itba.paw;

import java.util.List;

public class AuditionFilter {

    private final List<String> genresNames;
    private final List<String> rolesNames;
    private final String location;
    private final String title;
    private final int pageSize;
    private final int page;

    private AuditionFilter(AuditionFilterBuilder builder) {
        this.genresNames = builder.genresNames;
        this.rolesNames = builder.rolesNames;
        this.location = builder.location;
        this.title = builder.title;
        this.page = builder.page;
        this.pageSize = builder.pageSize;
    }

    public static class AuditionFilterBuilder {
        private List<String> genresNames;
        private List<String> rolesNames;
        private String location;
        private String title;
        private final int pageSize;
        private final int page;

        public AuditionFilterBuilder(int page, int pageSize) {
            this.page = page;
            this.pageSize = pageSize;
        }

        public AuditionFilterBuilder withGenres(List<String> genresNames) {
            this.genresNames = genresNames;
            return this;
        }

        public AuditionFilterBuilder withRoles(List<String> rolesNames) {
            this.rolesNames = rolesNames;
            return this;
        }

        public AuditionFilterBuilder withLocation(String location) {
            this.location = location;
            return this;
        }

        public AuditionFilterBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public AuditionFilter build() {
            return new AuditionFilter(this);
        }
    }

    public List<String> getGenresNames() {
        return genresNames;
    }

    public List<String> getRolesNames() {
        return rolesNames;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        if(title == null)
            return "";
        return title;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPage() {
        return page;
    }
}
