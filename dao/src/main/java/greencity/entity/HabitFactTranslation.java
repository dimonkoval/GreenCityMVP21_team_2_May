package greencity.entity;

import greencity.enums.FactOfDayStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.IntegerJdbcType;

@Entity
@Table(name = "habit_fact_translations")
@SuperBuilder
@EqualsAndHashCode(callSuper = true, exclude = {"factOfDayStatus", "habitFact"})
@NoArgsConstructor
public class HabitFactTranslation extends Translation {
    @Getter
    @Setter
    @Enumerated(value = EnumType.ORDINAL)
    @JdbcType(IntegerJdbcType.class)
    private FactOfDayStatus factOfDayStatus;

    @Getter
    @Setter
    @ManyToOne
    private HabitFact habitFact;
}
