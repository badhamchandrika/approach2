package com.aem.airmiles.web.core.models;

import com.day.cq.wcm.api.Page;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static junitx.util.PrivateAccessor.setField;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Test class used to cover {@link FileUploadModel model test cases}.
 */
@ExtendWith(MockitoExtension.class)
class FileUploadModelTest {

    /**
     * Allowed formats.
     */
    private static final String png = "png", jpeg = "jpeg";

    /**
     * Model to be tested.
     */
    private static FileUploadModel model;
    
    @Mock
    private Page currentPage;

    /**
     * Initialize model with test values.
     */
    @BeforeAll
    static void setUp() {
        model = new FileUploadModel();
        assertDoesNotThrow(() -> {
            setField(model, "id", "12345");
            setField(model, "dndTxt", "Drop files here!");
            setField(model, "fileUploadTxt", "choose a file");
            setField(model, "minAttachments", "0");
            setField(model, "maxAttachments", "5");
            setField(model, "maxFile", "1");
            setField(model, "allowedFormats", new String[]{jpeg, png});
            setField(model, "uploadedSectionTitle", "Uploaded files");
            setField(model, "uploadError", "Image upload failed");
            setField(model, "uploadDone", "Completed");
            setField(model, "uploadWip", "Uploading");
        });
    }

    /**
     * Asserts that the model is not null and the id is the provided one.
     */
    @Test
    void getId() {
        assertNotNull(model);
        final String id = model.getId();
        assertNotNull(id);
        assertNotEquals(EMPTY, id);
    }

    /**
     * Asserts that the model is not null and the dndTxt is the provided one.
     */
    @Test
    void getDndTxt() {
        assertNotNull(model);
        final String dndText = model.getDndTxt();
        assertNotNull(dndText);
        assertNotEquals(EMPTY, dndText);
    }

    /**
     * Asserts that the model is not null and the id is the provided one.
     */
    @Test
    void getFileUploadTxt() {
        assertNotNull(model);
        final String fileUploadTxt = model.getFileUploadTxt();
        assertNotNull(fileUploadTxt);
        assertNotEquals(EMPTY, fileUploadTxt);
    }

    /**
     * Asserts that the model is not null and the minAttachments field is the provided one.
     */
    @Test
    void getMinAttachments() {
        assertNotNull(model);
        final String minAttachments = model.getMinAttachments();
        assertNotNull(minAttachments);
        assertNotEquals(EMPTY, minAttachments);
    }

    /**
     * Asserts that the model is not null and the maxAttachments field is the provided one.
     */
    @Test
    void getMaxAttachments() {
        assertNotNull(model);
        final String maxAttachments = model.getMaxAttachments();
        assertNotNull(maxAttachments);
        assertNotEquals(EMPTY, maxAttachments);
    }

    /**
     * Asserts that the model is not null and the maxFile field is the provided one.
     */
    @Test
    void getMaxFile() {
        assertNotNull(model);
        final String maxFile = model.getMaxFile();
        assertNotNull(maxFile);
        assertNotEquals(EMPTY, maxFile);
    }

    /**
     * Asserts that the model is not null and the allowedFormats field is the provided one.
     */
    @Test
    void getAllowedFormats() {
        assertNotNull(model);
        final String [] allowedFormats = model.getAllowedFormats();
        assertNotNull(allowedFormats);
        assertEquals(2, allowedFormats.length);
        assertEquals(jpeg, allowedFormats[0]);
        assertEquals(png, allowedFormats[1]);
    }

    /**
     * Assert that the model is not null and the short description is the provided one in French.
     */
    @Test
    void getShortDescriptionFr() {
        assertDoesNotThrow(() -> setField(model, "currentPage", currentPage));
        when(currentPage.getLanguage()).thenReturn(new Locale("fr", "ca"));
        assertNotNull(model);
        final String sd = model.getShortDescription();
        assertNotNull(sd);
        assertEquals("JPEG ou PNG uniquement • Taille maximale du fichier 1 mb", sd);
    }

    /**
     * Assert that the model is not null and the short description is the provided one in English.
     */
    @Test
    void getShortDescriptionEn() {
        assertDoesNotThrow(() -> setField(model, "currentPage", currentPage));
        when(currentPage.getLanguage()).thenReturn(new Locale("en", "ca"));
        assertNotNull(model);
        final String sd = model.getShortDescription();
        assertNotNull(sd);
        assertEquals("JPEG or PNG only • Max file size 1 mb", sd);
    }

    /**
     * Assert that the model is not null and the uploaded section title is the provided one.
     */
    @Test
    void getUploadedSectionTitle(){
        assertNotNull(model);
        final String uploadedSectionTitle = model.getUploadedSectionTitle();
        assertNotNull(uploadedSectionTitle);
        assertNotEquals(EMPTY, uploadedSectionTitle);
    }

    /**
     * Assert that the model is not null and the error message is the provided one.
     */
    @Test
    void getUploadError() {
        assertNotNull(model);
        final String uploadError = model.getUploadError();
        assertNotNull(uploadError);
        assertNotEquals(EMPTY, uploadError);
    }

    /**
     * Assert that the model is not null and the done message is the provided one.
     */
    @Test
    void getUploadDone() {
        assertNotNull(model);
        final String uploadDone = model.getUploadDone();
        assertNotNull(uploadDone);
        assertNotEquals(EMPTY, uploadDone);
    }

    /**
     * Assert that the model is not null and the WIP message is the provided one.
     */
    @Test
    void getUploadWip() {
        assertNotNull(model);
        final String uploadWip = model.getUploadWip();
        assertNotNull(uploadWip);
        assertNotEquals(EMPTY, uploadWip);
    }
}