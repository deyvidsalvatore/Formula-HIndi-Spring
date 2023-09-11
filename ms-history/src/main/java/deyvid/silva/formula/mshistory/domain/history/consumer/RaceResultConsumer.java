package deyvid.silva.formula.mshistory.domain.history.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import deyvid.silva.formula.mshistory.domain.history.model.Race;
import deyvid.silva.formula.mshistory.domain.history.repository.RaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class RaceResultConsumer {

    private final RaceRepository raceRepository;

    public RaceResultConsumer(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "${rabbitmq.queue.name}"))
    public void consume(String message) {
        log.info(String.format("Received message -> %s", message));

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Race race = objectMapper.readValue(message, Race.class);

            Optional<Race> existingRace = raceRepository.findById(race.getId());
            if (existingRace.isEmpty()) {
                race.setTimestamp(LocalDateTime.now());
                raceRepository.save(race);
                log.info("RaceResultConsumer ::: Saved Race to Database {}", race);
            } else {
                log.info("RaceResultConsumer ::: Race with ID {} already exists in the database.", race.getId());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

