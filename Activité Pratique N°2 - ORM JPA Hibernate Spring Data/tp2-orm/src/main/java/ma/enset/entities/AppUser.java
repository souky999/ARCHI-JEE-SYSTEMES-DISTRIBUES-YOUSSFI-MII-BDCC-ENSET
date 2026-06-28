package ma.enset.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<AppRole> roles;

    public AppUser() {}

    public AppUser(Long id, String username, String password, String email, List<AppRole> roles) {
        this.id = id; this.username = username; this.password = password;
        this.email = email; this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<AppRole> getRoles() { return roles; }
    public void setRoles(List<AppRole> roles) { this.roles = roles; }

    @Override
    public String toString() {
        return "AppUser{id=" + id + ", username='" + username + "'}";
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id; private String username; private String password;
        private String email; private List<AppRole> roles;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder username(String u) { this.username = u; return this; }
        public Builder password(String p) { this.password = p; return this; }
        public Builder email(String e) { this.email = e; return this; }
        public Builder roles(List<AppRole> r) { this.roles = r; return this; }
        public AppUser build() { return new AppUser(id, username, password, email, roles); }
    }
}
