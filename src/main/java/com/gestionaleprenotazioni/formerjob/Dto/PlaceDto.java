package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PlaceDto {

    private Integer id;
    private String code;
    private boolean status;
    private String type;
    private Integer eventId; // id dell'evento a cui appartiene il posto

    public PlaceDto() {}

    public PlaceDto(Integer id, String code, boolean status, String type, Integer eventId) {
        this.id = id;
        this.code = code;
        this.status = status;
        this.type = type;
        this.eventId = eventId;
    }

    // gt  st
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getEventId() { return eventId; }
    public void setEventId(Integer eventId) { this.eventId = eventId; }
}