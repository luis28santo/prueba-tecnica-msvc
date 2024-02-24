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
public class AccountRequest {

    private Long clientId;

    private String accountNumber;

    private String accountType;

    private BigDecimal initialBalance;
}
