package com.aem.airmiles.web.core.models;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.export.json.ExporterConstants;
import com.aem.airmiles.web.core.constants.ResourceTypes;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import javax.annotation.PostConstruct;
import java.util.Objects;

import static com.aem.airmiles.web.core.utils.Utils.getElements;
import static org.apache.sling.models.annotations.DefaultInjectionStrategy.OPTIONAL;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class}, defaultInjectionStrategy = OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class OffersContentFragmentModel {

    @Getter
    @Setter
    @ValueMapValue
    private String fragmentPath;
    @SlingObject
    @Getter
    @Setter
    private ResourceResolver resourceResolver;

    @Getter
    @Setter
    private OffersCarouselDetailsModel offersCarouselDetailsModel;

    @Getter
    @Setter
    private CLOHeroDetailsModel cloHeroDetailsModel;

    @Getter
    @Setter
    private OffersQueryParamModel offersQueryParamModel;

    @Getter
    @Setter
    private OffersCustomCardDetailsModel offersCustomCardDetailsModel;

    @PostConstruct
    public void init() {
        getCarouselDetails();
    }

    private void getCarouselDetails() {
        if (null != fragmentPath && null!=resourceResolver) {
            final Resource cfRes = resourceResolver.getResource(fragmentPath);
            if(null!=cfRes) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
                objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
                getContentFragmentResource(cfRes, objectMapper);
            }
        }
    }

    private void getContentFragmentResource(final Resource resource, final ObjectMapper objectMapper) {
        final ContentFragment contentFragment = resource.adaptTo(ContentFragment.class);
        if (null != contentFragment) {
            ValueMap valueMap = Objects.requireNonNull(resourceResolver.getResource(resource.getPath() + ResourceTypes.JCR_CONTENT_DATA)).getValueMap();
            String path = valueMap.get(ResourceTypes.PROPERTY_CQMODEL, String.class);
            if (Objects.requireNonNull(path).equalsIgnoreCase(ResourceTypes.OFFERS_CAROUSEL_MODEL)) {
                offersCarouselDetailsModel = objectMapper.convertValue(getElements(contentFragment), OffersCarouselDetailsModel.class);
                String offersCustomCardCFPath = offersCarouselDetailsModel.getOffersCustomCardDetails();
               ContentFragment offersCustomCardCF = getDetails(offersCustomCardCFPath);
               if(null!=offersCustomCardCF){
                   this.offersCustomCardDetailsModel = objectMapper.convertValue(getElements(offersCustomCardCF), OffersCustomCardDetailsModel.class);
               }
                String cloQueryParamCfPath = offersCarouselDetailsModel.getCloQueryParamDetails();
                ContentFragment cloQueryParamCF =  getDetails(cloQueryParamCfPath);
                if(null!=cloQueryParamCF){
                    this.offersQueryParamModel = objectMapper.convertValue(getElements(cloQueryParamCF), OffersQueryParamModel.class);

                }
            } else if (Objects.requireNonNull(path).equalsIgnoreCase(ResourceTypes.CLO_HERO_MODEL)) {
                cloHeroDetailsModel = objectMapper.convertValue(getElements(contentFragment), CLOHeroDetailsModel.class);
            }
            else if (Objects.requireNonNull(path).equalsIgnoreCase(ResourceTypes.CLO_QUERY_PARAM_MODEL)) {
                offersQueryParamModel = objectMapper.convertValue(getElements(contentFragment), OffersQueryParamModel.class);
            }
        }
    }

    private ContentFragment getDetails(String cFPath) {
        ContentFragment contentFragment = null;
        Resource res = resourceResolver.getResource(cFPath);
        if (StringUtils.isNoneEmpty(cFPath) && null != res) {
            contentFragment  = res.adaptTo(ContentFragment.class);
        }
        return contentFragment;
    }
}
