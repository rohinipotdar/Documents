package com.Demo.Documents.service;

import com.Demo.Documents.model.DocumentsDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DocumentService {

    // create file

    DocumentsDetails createdocumentFile(DocumentsDetails documentsDtos);

    //upload documents
    String uploadDocuments(MultipartFile file, String path) throws IOException;

    //get all documents
    List<DocumentsDetails> getAllDocuments(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get single document by id
    InputStream getDocument(String path,String name) throws FileNotFoundException;

    //delete document by Id
    String deleteDocumentById(String documentId);

}
