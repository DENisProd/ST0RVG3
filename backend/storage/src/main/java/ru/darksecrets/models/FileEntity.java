package ru.darksecrets.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private Integer fileSize;

    @Enumerated(EnumType.STRING)
    private FileStatus status;
}
