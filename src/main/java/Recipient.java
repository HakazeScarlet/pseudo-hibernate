import annotation.Column;
import annotation.Id;
import annotation.Table;

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

    @Column
    private Double amountOnAccount;

    @Column
    private Boolean vipAccount;
}
