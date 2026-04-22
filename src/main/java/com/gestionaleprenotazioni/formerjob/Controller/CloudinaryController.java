package com.gestionaleprenotazioni.formerjob.Controller;
import com.gestionaleprenotazioni.formerjob.Dto.ImageUploadResponseDto;
import com.gestionaleprenotazioni.formerjob.Service.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cloudinary")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Cloudinary", description = "Upload immagini su Cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Operation(summary = "Upload immagine", description = "Carica un file immagine su Cloudinary e restituisce URL pubblico e publicId")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageUploadResponseDto> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageUploadResponseDto response = cloudinaryService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}