package org.luisangel.msvcoperation.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClientDto {
    private Long idClient;
    private String name;
    private String genre;
    private Integer age;
    private String numDocument;
    private String address;
    private String phone;
}
