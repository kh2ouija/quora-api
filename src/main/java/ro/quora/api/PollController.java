package ro.quora.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.quora.api.model.Ballot;
import ro.quora.api.model.Poll;

import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollService pollService;

    @Autowired
    private HashidHelper hashidHelper;

    @GetMapping(value = "/polls/{hash}")
    public Ballot getBallot(@PathVariable("hash") String hash) {
        Poll poll = getPollByHash(hash);
        return new Ballot(poll, false);
    }

    @PostMapping(value = "/polls")
    public String createPoll(@RequestBody Poll poll) {
        poll = pollService.save(poll);
        return hashidHelper.encode(poll.getId());
    }

    @PostMapping(value = "/polls/{hash}/votes")
    public ResponseEntity submitVote(@PathVariable("hash") String hash, @RequestBody List<Long> answerIds) {
        Poll poll = getPollByHash(hash);
        if (answerIds.size() > 1 && !poll.isMultipleChoice()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("multiple votes cast for single option poll");
        }
        poll = pollService.recordVotes(poll, answerIds);
        return ResponseEntity.ok(new Ballot(poll, true));
    }

    private Poll getPollByHash(@PathVariable("hash") String hash) {
        Long id = hashidHelper.decode(hash);
        return pollService.getById(id);
    }

}
