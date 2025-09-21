package com.example.demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteResponse {

    private String idTx;

    private String mensaje;
    
    private String error;
}
