package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterOptions {

    private final List<String> genresNames;
    private final List<String> rolesNames;
    private final List<String> locations;
    private final String title;
    private final String order;

    private FilterOptions(FilterOptionsBuilder builder) {
        this.genresNames = builder.genresNames;
        this.rolesNames = builder.rolesNames;
        this.locations = builder.locations;
        this.title = builder.title;
        this.order = builder.order;
    }

    public static class FilterOptionsBuilder {
        private List<String> genresNames;
        private List<String> rolesNames;
        private List<String> locations;
        private String title;
        private String order;

        public FilterOptionsBuilder() {
            this.order = "DESC";
        }

        public FilterOptionsBuilder withGenres(List<String> genresNames) {
            this.genresNames = genresNames;
            return this;
        }

        public FilterOptionsBuilder withRoles(List<String> rolesNames) {
            this.rolesNames = rolesNames;
            return this;
        }

        public FilterOptionsBuilder withLocations(List<String> locations) {
            this.locations = locations;
            return this;
        }

        public FilterOptionsBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public FilterOptionsBuilder withOrder(String order) {
            if(order.equalsIgnoreCase("ASC"))
                this.order = order;
            else
                this.order = "DESC";
            return this;
        }

        public FilterOptions build() {
            return new FilterOptions(this);
        }
    }

    public List<String> getGenresNames() {
        return genresNames == null ? Collections.emptyList() : genresNames;
    }

    public List<String> getRolesNames() {
        return rolesNames == null ? Collections.emptyList() : rolesNames;
    }

    public List<String> getLocations() {
        return locations == null ? Collections.emptyList() : locations;
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
