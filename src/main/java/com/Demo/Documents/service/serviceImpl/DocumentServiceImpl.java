package com.Demo.Documents.service.serviceImpl;

import com.Demo.Documents.model.DocumentsDetails;
import com.Demo.Documents.respository.DocumentRepo;
import com.Demo.Documents.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;



@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepo documentRepo;

       private Logger logger= LoggerFactory.getLogger(DocumentServiceImpl.class);

       //create file
    @Override
    public DocumentsDetails createdocumentFile(DocumentsDetails documentsDetails) {
        String documentId = UUID.randomUUID().toString();
        documentsDetails.setDocumentId(documentId);
        DocumentsDetails details = this.documentRepo.save(documentsDetails);
        return details;
    }

    //upload file
    @Override
    public String uploadDocuments(MultipartFile file, String path) throws IOException {
        
        String originalFilename = file.getOriginalFilename();
        logger.info("File Name : {}",originalFilename);
        String filename = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=filename+extension;
        String fullPathWithFileName=path+ File.separator+fileNameWithExtension;
        if(extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".pdf")||extension.equalsIgnoreCase(".jpeg")||extension.equalsIgnoreCase(".doc")||extension.equalsIgnoreCase(".docx")){

            File folder=new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }

        Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
       /* DocumentsDetails dc=new DocumentsDetails();
        dc.setFileName(filename);
            DocumentsDetails details = this.documentRepo.save(dc);*/
            logger.info("complete Dao call for upload the documents file");
        return fileNameWithExtension;
        }
        else {
            throw new RuntimeException("File with this"+extension+"not allowed");
        }
    }

//get all documents file
    @Override
    public List<DocumentsDetails> getAllDocuments(int pageNumber, int pageSize, String sortBy, String sortDir) {

        logger.info("Initiating Dao call for get all documents ");
        Sort sort = Sort.by(sortBy);
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<DocumentsDetails> page = this.documentRepo.findAll(pageable);
        List<DocumentsDetails> documents = page.getContent();
         logger.info("complete Dao call for get all documents ");
        return documents;

    }
//get single document
    @Override
    public InputStream getDocument(String path,String name) throws FileNotFoundException {


        String fullPath=path+File.separator+name;

        InputStream inputStream =new FileInputStream(fullPath);

        return inputStream;
    }

    // delete document
    @Override
    public String deleteDocumentById(String documentId) {

        DocumentsDetails documentsDetails = this.documentRepo.findById(documentId).orElseThrow(() -> new RuntimeException("document not found"));

        this.documentRepo.deleteById(documentId);

        return "document deleted";

    }
}
