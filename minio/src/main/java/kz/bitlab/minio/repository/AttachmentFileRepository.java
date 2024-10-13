package kz.bitlab.minio.repository;

import kz.bitlab.minio.model.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Long> {

    AttachmentFile findByFileName(String fileName);

}
