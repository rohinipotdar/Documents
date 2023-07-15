package com.Demo.Documents.respository;

import com.Demo.Documents.model.DocumentsDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepo extends JpaRepository<DocumentsDetails,String> {

}
