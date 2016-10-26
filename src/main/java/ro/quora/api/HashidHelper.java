package ro.quora.api;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HashidHelper {

    @Value("${hashids.salt}")
    private String salt;

    private Hashids hashids;

    @PostConstruct
    public void setup() {
        this.hashids = new Hashids(salt);
    }

    public String encode(long number) {
        return hashids.encode(number);
    }

    public long decode(String hash) {
        return hashids.decode(hash)[0];
    }
}
