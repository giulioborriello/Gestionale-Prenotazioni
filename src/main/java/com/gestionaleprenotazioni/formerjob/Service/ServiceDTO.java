package com.gestionaleprenotazioni.formerjob.Service;

public interface ServiceDTO<DTO> {

    public Iterable<DTO> getAll();

    public DTO read(Integer id);

    public DTO insert (DTO dto);

    public DTO update (DTO dto);

    public void delete (Integer id);
}
