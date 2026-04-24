package com.gestionaleprenotazioni.formerjob.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUploadResponseDto {
    private String publicId;
    private String secureUrl;
}
