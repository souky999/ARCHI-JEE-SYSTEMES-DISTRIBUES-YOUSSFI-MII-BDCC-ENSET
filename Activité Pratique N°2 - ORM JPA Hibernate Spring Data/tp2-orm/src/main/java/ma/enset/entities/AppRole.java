package ma.enset.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String description;

    @ManyToMany(mappedBy = "roles")
    private List<AppUser> users;

    public AppRole() {}

    public AppRole(Long id, String roleName, String description, List<AppUser> users) {
        this.id = id; this.roleName = roleName; this.description = description; this.users = users;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<AppUser> getUsers() { return users; }
    public void setUsers(List<AppUser> users) { this.users = users; }

    @Override
    public String toString() {
        return "AppRole{id=" + id + ", roleName='" + roleName + "'}";
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private String roleName; private String description; private List<AppUser> users;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder roleName(String r) { this.roleName = r; return this; }
        public Builder description(String d) { this.description = d; return this; }
        public Builder users(List<AppUser> u) { this.users = u; return this; }
        public AppRole build() { return new AppRole(id, roleName, description, users); }
    }
}
