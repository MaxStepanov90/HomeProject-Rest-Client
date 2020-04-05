package com.stepanov.documentclient.controller;

import com.stepanov.documentclient.model.Document;
import com.stepanov.documentclient.service.DocumentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Max")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class DocumentControllerIntegrationTest {

    @Autowired
    private DocumentController documentController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocumentServiceImpl documentService;

    @Test
    public void contextLoads() {
        assertThat(documentController).isNotNull();
    }

    private Document getNewDocument() {
        return new Document((long) 1, "New document", LocalDate.now(), "Hello!");
    }

    @Test
    void getDocumentsList_Should_Get_ListOfDocument() throws Exception {
        when(documentService.getDocumentsList()).thenReturn(Collections.singletonList(getNewDocument()));
        mockMvc.perform(get("/documents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(model().attribute("documents", hasSize(1)))
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(view().name("documents"));

    }

    @Test
    void getDocumentById_Should_Get_DocumentById() throws Exception {
        when(documentService.getDocumentById((long) 1)).thenReturn(getNewDocument());
        mockMvc.perform(get("/documents/1")
                .param("{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("document", instanceOf(Document.class)))
                .andExpect(content().contentType("text/html;charSet = UTF-8"))
                .andExpect(view().name("document"));

    }

    @Test
    void saveDocument_Should_Save_And_Redirect() throws Exception {
        mockMvc.perform(post("/documents/save"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/documents"));

    }

    @Test
    void deleteById_Should_Delete_And_Redirect() throws Exception {
        mockMvc.perform(get("/documents/delete/1")
                .param("{id}", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/documents"));

    }
}