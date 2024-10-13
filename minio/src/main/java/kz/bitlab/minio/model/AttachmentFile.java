package kz.bitlab.minio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_attachments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalName;
    private String mimeType;
    private Long size;

    @Column(unique = true)
    private String fileName;
    private LocalDateTime addedTime;

    @PrePersist
    private void setTime(){
        addedTime = LocalDateTime.now();
    }

}
