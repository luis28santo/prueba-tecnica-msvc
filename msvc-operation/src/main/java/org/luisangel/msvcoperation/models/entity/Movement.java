package org.luisangel.msvcoperation.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luisangel.msvcoperation.models.enums.MovementTypeEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "prueba_tecnica", name = "movements")
public class Movement {

    @Id
    private Long id;

    @Column(value = "account_id")
    private Long accountId;

    @Column(value = "registration_date")
    private LocalDateTime registrationDate;

    @Column(value = "movement_type")
    private MovementTypeEnum movementType;

    @Column(value = "balance")
    private BigDecimal balance;

    @Column(value = "amount")
    private BigDecimal amount;

    public void setMovementType(String movementType) {
        this.movementType = MovementTypeEnum.valueOf(movementType);
    }
}
