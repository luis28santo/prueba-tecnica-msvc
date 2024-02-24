package org.luisangel.msvcoperation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luisangel.msvcoperation.models.enums.AccountTypeEnum;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AccountResponse {

    private Long accountId;

    private Long clientId;

    private String accountNumber;

    private AccountTypeEnum accountType;

    private BigDecimal initialBalance;

    private String clientName;

    private String clientNumDocument;

}
