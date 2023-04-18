package labshopcompleted.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import labshopcompleted.OrderApplication;
import labshopcompleted.domain.OrderCancelled;
import labshopcompleted.domain.OrderPlaced;
import lombok.Data;

@Entity
@Table(name = "Order_table")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;

    private Integer qty;

    private String customerId;

    private Double amount;

    private String status;

    private String address;

    @PostPersist
    public void onPostPersist() {
        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {
        // Get request from Order
        //labshopcompleted.external.Order order =
        //    Application.applicationContext.getBean(labshopcompleted.external.OrderService.class)
        //    .getOrder(/** mapping value needed */);

    }

    @PreRemove
    public void onPreRemove() {
        OrderCancelled orderCancelled = new OrderCancelled(this);
        orderCancelled.publishAfterCommit();
    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }
}
