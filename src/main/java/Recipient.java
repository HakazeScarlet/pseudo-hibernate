@Table
public class Recipient {

    @Id
    private Long id;

    @Column
    private int age;

    @Column
    private String name;

    @Column
    private String email;
}
