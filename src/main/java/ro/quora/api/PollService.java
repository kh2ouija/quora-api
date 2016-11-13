package ro.quora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.quora.api.model.Answer;
import ro.quora.api.model.Poll;
import ro.quora.api.repository.AnswerRepository;
import ro.quora.api.repository.PollRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Poll getById(long pollId) {
        return pollRepository.findOne(pollId);
    }

    public Poll save(Poll poll) {
        poll.getAnswers().forEach(answer -> {
            answer.setPoll(poll);
            answer.setVotes(0);
        });
        return pollRepository.save(poll);
    }

    @Transactional
    public Poll recordVotes(Poll poll, List<Long> answerIds) {
        List<Long> validAnswerIds = poll.getAnswers().stream().map(Answer::getId).collect(Collectors.toList());
        answerIds.stream()
                .filter(validAnswerIds::contains)
                .forEach(this::vote);
        return this.getById(poll.getId());
    }

    private Answer vote(Long answerId) {
        Answer answer = answerRepository.findOne(answerId);
        answer.setVotes(answer.getVotes() + 1);
        return answerRepository.save(answer);
    }
}
