package ca.airmiles.travel.core.utils;

import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.models.CardDetail;
import com.day.cq.commons.Externalizer;
import com.day.cq.i18n.I18n;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static ca.airmiles.travel.core.constant.ContentConstant.*;
import static com.day.cq.tagging.TagConstants.PN_TAGS;
import static org.apache.commons.lang.StringUtils.EMPTY;

public class Utils {

    /**
     * This method return url ending with .html if it is internal link
     * @param url This is Content URL
     * @return Formatted URL
     */
    public static String getFormattedURL(String url){
        if (StringUtils.isNotBlank(url) && url.startsWith(ContentConstant.SLASH)
                && !url.startsWith(ContentConstant.SLASH_CONTENT_DAM) && !url.contains(ContentConstant.DOT_HTML)) {
            return url + ContentConstant.DOT_HTML;
        }else{
            return url;
        }
    }

    public static ResourceResolver getServiceResourceResolver(ResourceResolverFactory resolverFactory) throws LoginException {
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put(ResourceResolverFactory.SUBSERVICE, ContentConstant.TRAVEL_SERVICE_USER);
        return resolverFactory.getServiceResourceResolver(map);
    }

    public static CardDetail getCardDetail(ValueMap valueMap, I18n i18n) {
        CardDetail cardDetail = new CardDetail();
        cardDetail.setCardTitle(valueMap.get(CARD_TITLE, EMPTY));
        cardDetail.setCardDestinationCountry(valueMap.get(CARD_DESTINATION_COUNTRY, EMPTY));
        cardDetail.setCardDescription(valueMap.get(CARD_DESCRIPTION, EMPTY));
        cardDetail.setCardImageAlt(valueMap.get(CARD_IMAGEALT, EMPTY));
        cardDetail.setCardImagePath(valueMap.get(CARD_IMAGEPATH, EMPTY));
        cardDetail.setIcon(valueMap.get(ICON, EMPTY));
        cardDetail.setCardTagIcon(valueMap.get(CARD_TAGICON, EMPTY));
        cardDetail.setDate(valueMap.get(DATE, EMPTY));
        cardDetail.setStartDate(valueMap.get(START_DATE, EMPTY));
        cardDetail.setDateText(valueMap.get(DATE_TEXT, EMPTY));
        cardDetail.setUrl(valueMap.get(URL, EMPTY));
        cardDetail.setTags(valueMap.get(PN_TAGS, new String[]{}));
        cardDetail.setLinkTarget(valueMap.get(LINK_TARGET, EMPTY));
        cardDetail.setHideInListing(valueMap.get(HIDE_IN_LISTING, false));
        cardDetail.setDealType(i18n.get(valueMap.get(CARD_DEAL_TYPE, EMPTY)));
        return cardDetail;
    }
    public static String getExternalizedPath(ResourceResolver resourceResolver, String path, Set<String> runmodes){
        Externalizer externalizer=resourceResolver.adaptTo(Externalizer.class);
        if (runmodes.contains("publish")) {
            return externalizer.publishLink(resourceResolver,path);
        } else if (runmodes.contains("author")) {
            return externalizer.authorLink(resourceResolver,path);
        }else
            return null;
    }
}
