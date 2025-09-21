package com.example.demo.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Request.ClienteRequest;
import com.example.demo.Response.ClienteResponse;
import com.example.demo.Service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
    
    private final ClienteService service;

    @PostMapping("/guardarCliente")
    public ResponseEntity<ClienteResponse> guardar(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = service.guardarCliente(request);
        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/actualizarCliente")
    public ResponseEntity<ClienteResponse> actualizar(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse response = service.actualizarCliente(request);
        if (response.getError() != null) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/consultarCliente/{tipoDocumento}_{numeroDocumento}")
    public ResponseEntity<?> consultar(@PathVariable String tipoDocumento, @PathVariable String numeroDocumento) {
        return service.consultarCliente(tipoDocumento, numeroDocumento)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body(
                        new ClienteResponse(null, null,
                                "Cliente " + tipoDocumento + " " + numeroDocumento + ". No se encuentra registrado.")));
    }
}
