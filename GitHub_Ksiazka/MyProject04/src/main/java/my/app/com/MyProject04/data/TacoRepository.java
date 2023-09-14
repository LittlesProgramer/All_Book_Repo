package my.app.com.MyProject04.data;

import my.app.com.MyProject04.domain.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco,Long> {
}
