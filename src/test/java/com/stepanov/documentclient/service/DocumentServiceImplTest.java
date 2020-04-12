package com.stepanov.documentclient.service;

import com.stepanov.documentclient.model.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentServiceImplTest {

    @Mock
    RestTemplate restTemplate;
    @InjectMocks
    DocumentServiceImpl documentService;

    @Test
    public void getDocumentsList_Contains() {
        List<Document> documents = new ArrayList<>();
        documents.add(new Document((long) 1, "new document", LocalDate.now(), "Hello!"));
        when(restTemplate.exchange("https://localhost:8443/documents", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Document>>() {
                }))
                .thenReturn(new ResponseEntity<>(documents, HttpStatus.OK));
        List<Document> foundDocuments = documentService.getDocumentsList();
        assertTrue(documents.containsAll(foundDocuments));
        verify(restTemplate, times(1)).exchange("https://localhost:8443/documents",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Document>>() {
                });
    }

    @Test
    public void getDocumentById_Then_Return_Document() {
        Document document = new Document((long) 1, "new document", LocalDate.now(), "Hello!");
        when(restTemplate.getForObject("https://localhost:8443/documents/1", Document.class))
                .thenReturn(document);
        assertEquals(document, documentService.getDocumentById(document.getId()));
        verify(restTemplate, times(1)).getForObject("https://localhost:8443/documents/1", Document.class);
    }

    @Test
    public void saveDocument_Should_Call_Once() {
        Document document = new Document((long) 1, "new document", LocalDate.now(), "Hello!");
        documentService.saveDocument(document);
        verify(restTemplate, times(1))
                .postForObject("https://localhost:8443/documents/save", document, Document.class);
    }

    @Test
    public void deleteDocumentById_Should_Call_Once() {
        documentService.deleteDocumentById((long) 1);
        verify(restTemplate, times(1))
                .delete("https://localhost:8443/documents/delete/1");
    }
}