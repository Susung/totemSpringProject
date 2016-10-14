package totem;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface DataDAO extends CrudRepository<GraphData, Long> {


}
