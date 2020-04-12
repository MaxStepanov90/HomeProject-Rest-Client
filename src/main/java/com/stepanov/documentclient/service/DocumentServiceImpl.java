package com.stepanov.documentclient.service;

import com.stepanov.documentclient.model.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
        return restTemplate.exchange("https://localhost:8443/documents",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Document>>() {
                }).getBody();
    }

    public Document getDocumentById(Long id) {
        return restTemplate.getForObject("https://localhost:8443/documents/" + id, Document.class);
    }

    public void saveDocument(Document document) {
        restTemplate.postForObject("https://localhost:8443/documents/save", document, Document.class);
    }

    public void deleteDocumentById(Long id) {
        restTemplate.delete("https://localhost:8443/documents/delete/" + id);
    }
}