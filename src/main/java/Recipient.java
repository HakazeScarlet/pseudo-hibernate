@Table
public class Recipient {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String email;
    
    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}
