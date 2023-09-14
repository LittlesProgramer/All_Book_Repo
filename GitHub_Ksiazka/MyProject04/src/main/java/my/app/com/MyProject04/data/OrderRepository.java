package my.app.com.MyProject04.data;

import my.app.com.MyProject04.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
