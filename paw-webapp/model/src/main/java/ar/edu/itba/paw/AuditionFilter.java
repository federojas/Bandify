package ar.edu.itba.paw;

import java.util.List;

public class AuditionFilter {

    private final List<String> genresNames;
    private final List<String> rolesNames;
    private final List<String> locations;
    private final String title;
    private final String order;

    private AuditionFilter(AuditionFilterBuilder builder) {
        this.genresNames = builder.genresNames;
        this.rolesNames = builder.rolesNames;
        this.locations = builder.locations;
        this.title = builder.title;
        this.order = builder.order;
    }

    public static class AuditionFilterBuilder {
        private List<String> genresNames;
        private List<String> rolesNames;
        private List<String> locations;
        private String title;
        private String order;

        public AuditionFilterBuilder withGenres(List<String> genresNames) {
            this.genresNames = genresNames;
            return this;
        }

        public AuditionFilterBuilder withRoles(List<String> rolesNames) {
            this.rolesNames = rolesNames;
            return this;
        }

        public AuditionFilterBuilder withLocations(List<String> locations) {
            this.locations = locations;
            return this;
        }

        public AuditionFilterBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public AuditionFilterBuilder withOrder(String order) {
            if(order.equalsIgnoreCase("ASC"))
                this.order = order;
            else
                this.order = "DESC";
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

    public List<String> getLocations() {
        return locations;
    }

    public String getOrder() {
        return order;
    }

    public String getTitle() {
        if(title == null)
            return "";
        return title;
    }
}