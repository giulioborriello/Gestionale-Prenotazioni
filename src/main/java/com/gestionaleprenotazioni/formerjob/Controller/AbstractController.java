package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Service.ServiceDTO;
import org.springframework.web.bind.annotation.*;

/**
 * Controller base astratto per endpoint CRUD generici.
 *
 * <p>Questa classe espone operazioni standard (getAll, read, insert, update, delete)
 * e delega la logica applicativa al servizio {@link ServiceDTO}.</p>
 *
 * @param <DTO> tipo del Data Transfer Object gestito dal controller
 */
@RestController
public abstract class AbstractController<DTO> {

    /**
     * Service generico che implementa le operazioni CRUD sul DTO.
     */
    private final ServiceDTO<DTO> service;

    /**
     * Costruttore protetto usato dai controller concreti.
     *
     * @param service servizio da delegare per le operazioni CRUD
     */
    protected AbstractController(ServiceDTO<DTO> service) {
        this.service = service;
    }

    /**
     * Restituisce tutte le risorse disponibili.
     *
     * <p>Endpoint: {@code GET /getall}</p>
     *
     * @return collezione di DTO
     */
    @GetMapping("/getall")
    public Iterable<DTO> getAll(){
        return service.getAll();
    }

    /**
     * Elimina una risorsa tramite ID.
     *
     * <p>Endpoint: {@code DELETE /delete?id=...}</p>
     *
     * @param id identificativo della risorsa da eliminare
     */
    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer id) {
        service.delete(id);
    }

    /**
     * Aggiorna una risorsa esistente.
     *
     * <p>Endpoint: {@code PUT /update}</p>
     *
     * @param dto payload con i dati aggiornati
     * @return DTO aggiornato (come ricevuto dal controller)
     */
    @PutMapping("/update")
    public DTO update(@RequestBody DTO dto){
        service.update(dto);
        return dto;
    }

    /**
     * Inserisce una nuova risorsa.
     *
     * <p>Endpoint: {@code POST /insert}</p>
     *
     * @param dto payload da inserire
     * @return DTO inserito (come ricevuto dal controller)
     */
    @PostMapping("/insert")
    public DTO insert (@RequestBody DTO dto) {
        service.insert(dto);
        return dto;
    }

    /**
     * Legge una risorsa tramite ID.
     *
     * <p>Endpoint: {@code GET /read?id=...}</p>
     *
     * @param id identificativo della risorsa
     * @return DTO trovato
     */
    @GetMapping("/read")
    public DTO read(@RequestParam("id") Integer id) {
        return service.read(id);
    }
}
