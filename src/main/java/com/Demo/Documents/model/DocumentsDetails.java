package com.Demo.Documents.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="document_details")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentsDetails {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String documentId;

    private String documentName;

    private  String fileName;

}
