package ro.quora.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quora.api.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

}
