package totem;

import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

public interface DataEntryRepository extends CassandraRepository<DataEntry> {

    @Override
    List<DataEntry> findAll();
    
}
