package com.aem.airmiles.web.core.models;

import com.day.cq.wcm.api.Page;
import lombok.Getter;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.Locale;

import static lombok.AccessLevel.NONE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;


/**
 * Sling model to export archive upload form component configurations.
 *
 * @author pabpalac.
 */
@Getter
@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
public class FileUploadModel extends ComponentModel {

    /**
     * Current page from sling framework.
     */
    @ScriptVariable
    @Getter(NONE)
    private Page currentPage;

    /**
     * The unique identifier of the component.
     */
    @ValueMapValue
    private String id;

    /**
     * The text to display for drag-and-drop operations.
     */
    @ValueMapValue
    private String dndTxt;

    /**
     * The text to display for system file upload operations.
     */
    @ValueMapValue
    private String fileUploadTxt;

    /**
     * The minimum number of attachments required.
     */
    @ValueMapValue
    private String minAttachments;

    /**
     * The maximum number of attachments allowed.
     */
    @ValueMapValue
    private String maxAttachments;

    /**
     * The maximum file size allowed for an attachment in MB.
     */
    @ValueMapValue
    private String maxFile;

    /**
     * A comma-separated string listing the allowed formats for attachments. For example, "jpg,png,pdf".
     */
    @ValueMapValue
    private String[] allowedFormats;

    /**
     * Title for the upload section.
     */
    @ValueMapValue
    private String uploadedSectionTitle;

    /**
     * Image upload error status message.
     */
    @ValueMapValue
    private String uploadError;

    /**
     * Image upload completed status message.
     */
    @ValueMapValue
    private String uploadDone;

    /**
     * Image upload WIP status message.
     */
    @ValueMapValue
    private String uploadWip;

    /**
     * The mandatory validation.
     */
    @ValueMapValue
    private boolean required;

    /**
     * The mandatory validation message.
     */
    @ValueMapValue
    private boolean requiredMessage;

    /**
     * Retrieves the short description of the component based on given properties and language.
     *
     * @return short description.
     */
    public String getShortDescription() {
        if (currentPage == null) return EMPTY;

        final Locale locale = currentPage.getLanguage();
        if (null == locale) return EMPTY;

        if ("fr".equals(locale.getLanguage()))
            return String.format("%s uniquement • Taille maximale du fichier %s mb", getFormatsPhrase(" ou "),
                    maxFile);
        return String.format("%s only • Max file size %s mb", getFormatsPhrase(" or "), maxFile);
    }

    /**
     * Constructs a human-readable phrase from the array of allowed formats.
     *
     * @return A string representing the concatenated formats in a human-readable format. Returns an empty string if the
     * array is null or empty.
     */
    private String getFormatsPhrase(final String separator) {
        if (allowedFormats == null || allowedFormats.length == 0) return EMPTY;

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < allowedFormats.length; i++) {
            builder.append(allowedFormats[i].toUpperCase());
            if (i < allowedFormats.length - 2) builder.append(", ");
            if (i == allowedFormats.length - 2) builder.append(separator);
        }
        return builder.toString();
    }
}
