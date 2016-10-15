package totem.mysql;

import javax.transaction.Transactional;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;

@Transactional
public interface DataDAO extends CrudRepository<GraphData, Long> {
	
	@Override
	List<GraphData> findAll(); 
}
