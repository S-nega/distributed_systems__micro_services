package kz.bitlab.minio.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AttachmentFileDto {

    private Long id;
    private String fileName;
    private String originalName;
    private Long size;
    private LocalDateTime addedTime;
    private String mimeType;

}
