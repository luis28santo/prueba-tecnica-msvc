package org.luisangel.msvcoperation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MovementRequest {

    private String accountNumber;

    private BigDecimal amount;

    private Long id;

}
