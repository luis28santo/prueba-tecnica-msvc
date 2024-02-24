package org.luisangel.msvcoperation.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.luisangel.msvcoperation.models.enums.AccountTypeEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(schema = "prueba_tecnica", name = "accounts")
public class Account {

    @Id
    private Long id;

    @Column(value = "client_id")
    private Long clientId;

    @Column(value = "account_number")
    private String accountNumber;

    @Column(value = "account_type")
    private AccountTypeEnum accountType;

    @Column(value = "initial_balance")
    private BigDecimal initialBalance;

    @Column(value = "status")
    private Integer status;

    public void setAccountType(String accountType) {
        this.accountType = AccountTypeEnum.valueOf(accountType);
    }
}
