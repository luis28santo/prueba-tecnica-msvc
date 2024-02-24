package org.luisangel.msvcoperation.models.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.luisangel.msvcoperation.models.enums.AccountTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MovementFullDataResponse extends MovementResponse {

    private String clientFullName;

    private String clientNumDocument;
}
