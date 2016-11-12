package ro.quora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.quora.api.model.Answer;
import ro.quora.api.model.Poll;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private HashidHelper hashidHelper;

    @GetMapping(value = "/polls/{hash}")
    public Poll getPoll(@PathVariable("hash") String hash) {
        return getPollByHash(hash);
    }

    @PostMapping(value = "/polls")
    public String createPoll(@RequestBody Poll poll) {
        poll.getAnswers().forEach(answer -> answer.setVotes(0));
        poll = pollService.save(poll);
        return hashidHelper.encode(poll.getId());
    }

    @PostMapping(value = "/polls/{hash}/votes")
    public ResponseEntity submitVote(@PathVariable("hash") String hash, @RequestBody List<Long> answerIds) {
        Poll poll = getPollByHash(hash);
        if (answerIds.size() > 1 && !poll.isMultipleChoice()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("multiple votes cast for single option poll");
        }
        recordVotes(poll, answerIds);
        Poll pollUpdated = pollService.getById(poll.getId());
        return ResponseEntity.ok(pollUpdated);
    }

    private Poll getPollByHash(@PathVariable("hash") String hash) {
        Long id = hashidHelper.decode(hash);
        return pollService.getById(id);
    }

    private void recordVotes(Poll poll, List<Long> answerIds) {
        List<Long> validAnswerIds = poll.getAnswers().stream().map(Answer::getId).collect(Collectors.toList());
        answerIds.stream()
                .filter(validAnswerIds::contains)
                .forEach(pollService::vote);
    }

}
