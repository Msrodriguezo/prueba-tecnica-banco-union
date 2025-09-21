package com.example.demo.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.ClienteEntity;
import com.example.demo.Request.ClienteRequest;
import com.example.demo.Response.ClienteResponse;
import com.example.demo.Respository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio encargado de la gestión de clientes en el sistema.
 *
 * Funcionalidades principales:
 * - Registrar un nuevo cliente en la base de datos.
 * - Actualizar la información de un cliente existente.
 * - Consultar clientes por tipo y número de documento.
 *
 * Este servicio actúa como capa de negocio, validando reglas básicas antes de
 * interactuar con el repositorio de persistencia.
 *
 * @author Michael Steven Rodriguez Ortiz
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository repository;

    /**
     * Registra un cliente en la base de datos.
     *
     * Antes de almacenar, se valida que no exista un cliente con el mismo
     * tipo y número de documento.
     *
     * @param request objeto {@link ClienteRequest} que contiene los datos del cliente a registrar.
     * @return {@link ClienteResponse} con el resultado de la operación:
     *         - Mensaje de éxito si el cliente fue almacenado correctamente.
     *         - Mensaje de error si el cliente ya existe en la base de datos.
     */
    public ClienteResponse guardarCliente(ClienteRequest request) {
        log.info("Request recibido para guardarCliente: {}", request);

        if (repository.existsByTipoDocumentoAndNumeroDocumento(request.getTipoDocumento(), request.getNumeroDocumento())) {
            log.warn("Cliente ya registrado: {} {}", request.getTipoDocumento(), request.getNumeroDocumento());
            return new ClienteResponse(request.getIdTx(),
                    null,
                    "Cliente " + request.getTipoDocumento() + " " + request.getNumeroDocumento() + ". Ya se encuentra registrado.");
        }

        ClienteEntity cliente = ClienteEntity.builder()
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .primerNombre(request.getPrimerNombre())
                .segundoNombre(request.getSegundoNombre())
                .primerApellido(request.getPrimerApellido())
                .segundoApellido(request.getSegundoApellido())
                .telefono(request.getTelefono())
                .correoElectronico(request.getCorreoElectronico())
                .build();

        repository.save(cliente);
        log.info("Cliente almacenado en BD: {}", cliente);

        return new ClienteResponse(request.getIdTx(),
                "Cliente " + request.getNumeroDocumento() + " almacenado de forma exitosa.",
                null);
    }

     /**
     * Actualiza la información de un cliente existente.
     *
     * La búsqueda se realiza mediante el tipo y número de documento.
     * Si el cliente no existe, se devuelve un mensaje de error.
     *
     * @param request objeto {@link ClienteRequest} con los nuevos datos del cliente.
     * @return {@link ClienteResponse} con el resultado de la operación:
     *         - Mensaje de éxito si el cliente fue actualizado.
     *         - Mensaje de error si no se encuentra el cliente.
     */
    public ClienteResponse actualizarCliente(ClienteRequest request) {
        log.info("Request recibido para actualizarCliente: {}", request);

        Optional<ClienteEntity> clienteOpt = repository.findByTipoDocumentoAndNumeroDocumento(
                request.getTipoDocumento(), request.getNumeroDocumento());

        if (clienteOpt.isEmpty()) {
            log.warn("Cliente no encontrado: {} {}", request.getTipoDocumento(), request.getNumeroDocumento());
            return new ClienteResponse(request.getIdTx(),
                    null,
                    "Cliente " + request.getTipoDocumento() + " " + request.getNumeroDocumento() + ". No se encuentra registrado.");
        }

        ClienteEntity cliente = clienteOpt.get();
        cliente.setPrimerNombre(request.getPrimerNombre());
        cliente.setSegundoNombre(request.getSegundoNombre());
        cliente.setPrimerApellido(request.getPrimerApellido());
        cliente.setSegundoApellido(request.getSegundoApellido());
        cliente.setTelefono(request.getTelefono());
        cliente.setCorreoElectronico(request.getCorreoElectronico());

        repository.save(cliente);
        log.info("Cliente actualizado en BD: {}", cliente);

        return new ClienteResponse(request.getIdTx(),
                "Cliente " + request.getNumeroDocumento() + " actualizado de forma exitosa.",
                null);
    }
    
    /**
     * Consulta un cliente en la base de datos.
     *
     * La búsqueda se realiza mediante el tipo y número de documento.
     *
     * @param tipoDocumento tipo de documento del cliente (por ejemplo: "CC", "TI", "NIT").
     * @param numeroDocumento número de documento del cliente.
     * @return {@link Optional} con la entidad {@link ClienteEntity} si existe, vacío en caso contrario.
     */
    public Optional<ClienteEntity> consultarCliente(String tipoDocumento, String numeroDocumento) {
        log.info("Request recibido para consultarCliente: {} {}", tipoDocumento, numeroDocumento);
        return repository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }
}
