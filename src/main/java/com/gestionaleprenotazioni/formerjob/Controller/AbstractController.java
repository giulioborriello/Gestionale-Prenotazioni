package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Service.ServiceDTO;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class AbstractController<DTO> {
    private final ServiceDTO<DTO> service;

    protected AbstractController(ServiceDTO<DTO> service) {
        this.service = service;
    }

    @GetMapping("/getall")
    public Iterable<DTO> getAll(){
        return service.getAll();
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer id) {
        service.delete(id);
    }


    @PutMapping("/update")
    public DTO update(@RequestBody DTO dto){
        service.update(dto);
        return dto;
    }

    @PostMapping("/insert")
    public DTO insert (@RequestBody DTO dto) {
        service.insert(dto);
        return dto;
    }

    @GetMapping("/read")
    public DTO read(@RequestParam("id") Integer id) {
        return service.read(id);
    }
}
