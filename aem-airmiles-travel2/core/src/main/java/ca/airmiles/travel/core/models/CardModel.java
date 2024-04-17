package ca.airmiles.travel.core.models;


import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.utils.Utils;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.*;
import org.apache.sling.settings.SlingSettingsService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ca.airmiles.travel.core.constant.ContentConstant.*;
import static org.apache.commons.lang.StringUtils.EMPTY;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class CardModel {

    @Getter
    @Setter
    @ValueMapValue
    private String variant;

    @Getter
    @Setter
    @ValueMapValue
    private String title;

    @Getter
    @Setter
    @ChildResource
    private List<Resource> details;

    @SlingObject
    @Getter
    @Setter
    private ResourceResolver resourceResolver;

    @Getter
    @Setter
    private List<CardDetail> cardDetailList = new ArrayList<>();

    @ScriptVariable
    @Setter
    Page currentPage;

    @Inject
    @Setter
    SlingHttpServletRequest request;

    @OSGiService
    SlingSettingsService slingSettingsService;

    Set<String> runmodes;

    @PostConstruct
    void init() throws ParseException {
        if(null != details) {
            for (Resource detail : details) {
                ValueMap valueMap = detail.getValueMap();
                String pageUrl = valueMap.get(ContentConstant.PAGE_URL, String.class);
                getCardDetails(pageUrl);
            }
        }
    }
    void getCardDetails(String pageUrl) throws ParseException {
        if(null!=resourceResolver && StringUtils.isNotEmpty(pageUrl)){
                Resource pageRes = resourceResolver.getResource(pageUrl+ ContentConstant.JCR_CONTENT);
                final Locale pageLocale = currentPage.getLanguage(false);
                final ResourceBundle resourceBundle = request.getResourceBundle(pageLocale);
                final I18n i18n = new I18n(resourceBundle);

                if(null!=pageRes){
                    ValueMap valueMap = pageRes.getValueMap();
                    CardDetail cardDetail = createCard(valueMap, i18n,pageUrl);
                    cardDetailList.add(cardDetail);
                }
            }
    }

    public CardDetail createCard(ValueMap valueMap, I18n i18n,String pageUrl) throws ParseException {
        CardDetail cardDetail = new CardDetail();
        cardDetail.setCardTitle(valueMap.get(ContentConstant.CARD_TITLE,String.class));
        cardDetail.setCardDestinationCountry(valueMap.get(ContentConstant.CARD_DESTINATION_COUNTRY,String.class));
        cardDetail.setCardDescription(valueMap.get(ContentConstant.CARD_DESCRIPTION,String.class));
        cardDetail.setCardTagIcon(valueMap.get(ContentConstant.CARD_TAGICON,String.class));
        cardDetail.setLinkTarget(valueMap.get(ContentConstant.LINK_TARGET,String.class));
        cardDetail.setCardImageAlt(valueMap.get(ContentConstant.CARD_IMAGEALT,String.class));
        cardDetail.setCardImagePath(valueMap.get(ContentConstant.CARD_IMAGEPATH,String.class));
        cardDetail.setDateText(valueMap.get(ContentConstant.DATE_TEXT,String.class));
        cardDetail.setDate(valueMap.get(DATE, EMPTY));
        cardDetail.setStartDate(valueMap.get(START_DATE, EMPTY));
        cardDetail.setIcon(valueMap.get(ContentConstant.ICON,String.class));

        cardDetail.setDataTrackID(valueMap.get(ContentConstant.DATATRACKID, String.class));
        cardDetail.setDataClickID(valueMap.get(ContentConstant.DATATRACKCLICK, String.class));
        cardDetail.setDataTrackType(valueMap.get(ContentConstant.DATATRACKTYPE, String.class));
        cardDetail.setDealType(i18n.get(valueMap.get(ContentConstant.CARD_DEAL_TYPE, String.class)));

        Date vDate = valueMap.get(ContentConstant.DATE, Date.class);
        if(null!=vDate){
            SimpleDateFormat dateParser = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy");
            Date date = dateParser.parse(vDate.toString());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");
            cardDetail.setDate(dateFormatter.format(date));
        }
        String url = valueMap.get(ContentConstant.URL,String.class);
        if(null!=url) {
            cardDetail.setUrl(url);
        } else {
            pageUrl = (isAuthor()) ? Utils.getFormattedURL(pageUrl) : Utils.getFormattedURL(pageUrl).replace(ROOT_TRAVEL_PATH,"");
            cardDetail.setUrl(pageUrl);
        }
        return  cardDetail;
    }
    public boolean isAuthor(){
        return slingSettingsService.getRunModes().contains("author");
    }
    public String getCurrentPage(){
        return Utils.getExternalizedPath(resourceResolver,currentPage.getPath(),runmodes).concat(".html");
    };
}
