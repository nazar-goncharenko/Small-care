package Smallcare.Repositories;

import Smallcare.Models.EventComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCommentRepository extends CrudRepository<EventComment, Long> {
}
