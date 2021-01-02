package Smallcare.Repositories;


import Smallcare.Models.ConfirmedEvent;
import Smallcare.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ConfirmedEventRepository extends CrudRepository<ConfirmedEvent, Long> {

    Set<ConfirmedEvent> getConfirmedEventByCreator(User user);
}
