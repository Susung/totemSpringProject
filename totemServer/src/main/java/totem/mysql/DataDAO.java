package totem.mysql;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface DataDAO extends CrudRepository<GraphData, Long> {
	
	@Override
	List<GraphData> findAll(); 
	
}
