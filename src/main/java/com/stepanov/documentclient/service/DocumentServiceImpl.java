package com.stepanov.documentclient.service;

import com.stepanov.documentclient.model.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private RestTemplate restTemplate;

    public DocumentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Document> getDocumentsList() {
        ResponseEntity<List<Document>> listEntity = restTemplate.exchange("http://localhost:8080/documents",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Document>>() {
                });
        return listEntity.getBody();
    }

    public Document getDocumentById(Long id) {
        ResponseEntity<Document> response = restTemplate.getForEntity("http://localhost:8080/documents/{id}", Document.class, id);
        return response.getBody();
    }

    public void saveDocument(Document document) {
        restTemplate.postForObject("http://localhost:8080/documents/save", document, Document.class);
    }

    public void deleteDocumentById(Long id) {
        restTemplate.delete("http://localhost:8080/documents/delete" + "/" + id);
    }
}