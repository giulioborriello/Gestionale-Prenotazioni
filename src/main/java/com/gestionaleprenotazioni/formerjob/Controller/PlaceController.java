package com.gestionaleprenotazioni.formerjob.Controller;

import com.gestionaleprenotazioni.formerjob.Dto.PlaceDto;
import com.gestionaleprenotazioni.formerjob.Service.PlaceService;
import org.springframework.web.bind.annotation.*;

//controller per gestione API dei posti
@RestController
@RequestMapping("Place")
public class PlaceController extends AbstractController<PlaceDto> {

    //service dei posti
    private final PlaceService placeService;

    //costruttore
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    //usa i metodi già messi nell'AbstractController
    // GET getall POST insert PUT update DELETE delete GET read
}