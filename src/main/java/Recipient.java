@Table
public class Recipient {

    @Id
    private Long id;

    @Column
    private String userName;

    @Column
    private String email;
    
    private String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }
}
