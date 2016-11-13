package ro.quora.api.model;

public class Ballot {

    private Poll poll;
    private Boolean alreadyVoted;

    public Ballot(Poll poll, Boolean alreadyVoted) {
        this.poll = poll;
        this.alreadyVoted = alreadyVoted;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Boolean getAlreadyVoted() {
        return alreadyVoted;
    }

    public void setAlreadyVoted(Boolean alreadyVoted) {
        this.alreadyVoted = alreadyVoted;
    }
}
