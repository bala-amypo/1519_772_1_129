

@Entity
public class ProductModel{
    @id
    @GeneratedValue(srategy=GenerationType.IDENTITY)
    private Long id;
    private String ruleName;
    private String requiredProductlds;
    private double discountPercentage;
    private boolean active;
      
}