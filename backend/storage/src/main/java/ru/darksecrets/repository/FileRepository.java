package ru.darksecrets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.darksecrets.models.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
