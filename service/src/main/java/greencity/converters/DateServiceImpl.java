package greencity.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class DateServiceImpl implements DateService {
    private final ZoneId datasourceTimezone;

    /**
     * Constructor.
     *
     * @param datasourceTimezone - {@link ZoneId} zone id of the datasource
     */
    @Autowired
    public DateServiceImpl(@Qualifier("datasourceTimezone") ZoneId datasourceTimezone) {
        this.datasourceTimezone = datasourceTimezone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime convertToDatasourceTimezone(ZonedDateTime toConvert) {
        return toConvert.withZoneSameInstant(datasourceTimezone);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime getDatasourceZonedDateTime() {
        return ZonedDateTime.now(datasourceTimezone);
    }
}
