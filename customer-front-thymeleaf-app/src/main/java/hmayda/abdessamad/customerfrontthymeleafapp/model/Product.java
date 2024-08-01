package hmayda.abdessamad.customerfrontthymeleafapp.model;


import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    private String id;
    private String name;
    private double price;
    private int quantity;
}
