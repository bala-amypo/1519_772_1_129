

@Entity
public class ProductModel{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String ruleName;
    private String requiredProductids;
    private double discountPercentage;
    private boolean active;

}
