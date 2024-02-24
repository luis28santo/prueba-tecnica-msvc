package org.luisangel.msvcoperation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.luisangel.msvcoperation.models.enums.AccountTypeEnum;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MovementResponse {

    private String accountNumber;

    private LocalDateTime registrationDate;

    private String movementType;

    private BigDecimal initialBalance;

    private AccountTypeEnum accountTypeEnum;

    private BigDecimal amount;
}
