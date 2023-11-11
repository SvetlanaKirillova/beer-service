package kz.fotya.beerservice.web.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {
    public Timestamp map(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null: Timestamp.valueOf(
                offsetDateTime
                        .atZoneSameInstant(ZoneOffset.UTC)
                        .toLocalDateTime());
    }

    public OffsetDateTime map(Timestamp timestamp){
        return timestamp == null ? null: OffsetDateTime.of(
                timestamp.toLocalDateTime(), ZoneOffset.UTC
        );
    }
}
