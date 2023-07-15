package com.Demo.Documents.controller;

import com.Demo.Documents.model.DocumentsDetails;
import com.Demo.Documents.respository.DocumentRepo;
import com.Demo.Documents.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepo documentRepo;

    @Value("${documents.profile.image.path}")
    private String imageUploadPath;

    private static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    /*
     * @author: rohini
     * @ApiNote: This method is for create file
     * @param: documents
     * return : documentsdetails
     */
    @PostMapping()
    public ResponseEntity<DocumentsDetails> createFile(@RequestBody DocumentsDetails documents){

        DocumentsDetails documentsdetail = this.documentService.createdocumentFile(documents);

        return new ResponseEntity<>(documentsdetail,HttpStatus.CREATED);

    }
    /*
     * @author: rohini
     * @ApiNote: This method is for upload file
     * @param: file, documentId
     * return : string
     */

    @PostMapping("/{documentId}")
    public ResponseEntity<String> uploadFile(
            @RequestParam("document") MultipartFile file,
            @PathVariable("documentId") String documentId) throws IOException {
        logger.info("Request entering for image upload");
        String uploadedDocuments = this.documentService.uploadDocuments(file, imageUploadPath);
        return new ResponseEntity<>(uploadedDocuments,HttpStatus.CREATED);
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for view file
     * @param: documentId,response
     * return : resource
     */
    @GetMapping("/{documentId}")
    public void viewDocument(@PathVariable String documentId, HttpServletResponse response) throws IOException {

        logger.info("user image name: {}",documentId);
        Optional<DocumentsDetails> documentsDetails = this.documentRepo.findById(documentId);
        InputStream resource = documentService.getDocument(imageUploadPath,documentsDetails.get().getDocumentName());
        response.setContentType(String.valueOf(MediaType.ALL));
        StreamUtils.copy(resource,response.getOutputStream());
    }
    /*
     * @author: rohini
     * @ApiNote: This method is for get all file
     * @param: pageNumber,pageSize,sortBy,sortDir
     * return : allDocuments
     */
    @GetMapping("/all")
    public ResponseEntity<List<DocumentsDetails>> getAllDocuments(@RequestParam (value = "pageNumber",defaultValue = "0") int pageNumber,
                                                               @RequestParam (value = "pageSize",defaultValue = "5") int pageSize,
                                                               @RequestParam(value = "sortBy", defaultValue = "documentId") String sortBy,
                                                               @RequestParam (value = "sortDir", defaultValue = "ASC") String sortDir){
        List<DocumentsDetails> allDocuments = this.documentService.getAllDocuments(pageNumber, pageSize, sortBy, sortDir);

    return new ResponseEntity<>(allDocuments,HttpStatus.OK);
    }

    /*
     * @author: rohini
     * @ApiNote: This method is for delete file
     * @param: documentId
     * return : message
     */
    @DeleteMapping("/{documentId}")
    public ResponseEntity<String> deleteDocuments(@PathVariable String documentId){
        String str = this.documentService.deleteDocumentById(documentId);
        return  new ResponseEntity<>(str,HttpStatus.OK);
    }
}
