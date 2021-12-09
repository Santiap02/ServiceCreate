package domain;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {

    private Integer idCliente;
    private String nombres;
    private String apellidos;
    private String docType;
    private Integer edad;
    private String ciudad;

}
