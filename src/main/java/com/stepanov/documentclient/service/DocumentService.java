package com.stepanov.documentclient.service;

import com.stepanov.documentclient.model.Document;

public interface DocumentService {

    Document getDocumentById(Long id);
    void saveDocument(Document document);
}
