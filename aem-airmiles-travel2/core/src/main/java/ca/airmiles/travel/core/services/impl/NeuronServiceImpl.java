package ca.airmiles.travel.core.services.impl;

import ca.airmiles.travel.core.constant.ContentConstant;
import ca.airmiles.travel.core.services.NeuronService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MIME;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component(service = NeuronService.class, immediate = true)
@Designate(ocd = NeuronServiceImpl.Config.class)
public class NeuronServiceImpl implements NeuronService {


    private static final Logger log = LoggerFactory.getLogger(NeuronServiceImpl.class);

    private final static String PACKAGES_URI = "/api/v1/Packages/%s";

    private final static String TOKEN_URI = "/api/v1/Tokens";
    private final static String ACCESS_TOKEN = "AccessToken";
    private final static String CLIENT_USERNAME = "ClientUserName";


    Map<String, String> packageMap;
    private String baseUrl;
    private String clientUserName;
    private String clientPassword;

    @Activate
    @Modified
    protected void activate(Config config) {
        this.baseUrl = config.baseUrl();
        this.clientUserName = config.clientUserName();
        this.clientPassword = config.clientPassword();
        this.packageMap = Arrays.stream(config.packageMapping()).map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }


    private String getAccessToken() {
        String accessToken = null;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLIENT_USERNAME, clientUserName);
        jsonObject.addProperty("ClientPassword", clientPassword);
        String response = sendPostRequest(baseUrl + TOKEN_URI, jsonObject.toString());
        if (null != response) {
            JsonElement jsonElement = new JsonParser().parse(response);
            JsonObject object = jsonElement.getAsJsonObject();
            if (object.has(ACCESS_TOKEN)) {
                accessToken = object.get(ACCESS_TOKEN).getAsString();
            }
        }
        return accessToken;
    }

    @Override
    public String getPackageID(String title) {
        if (packageMap.containsKey(title)) {
            return packageMap.get(title);
        }
        return null;
    }

    @Override
    public String getPackages(String packageId) {
        String response = null;
        String accessToken = getAccessToken();
        if (accessToken != null) {
            response = sendGetRequest(baseUrl + String.format(PACKAGES_URI, packageId), accessToken);
        }
        return response;
    }

    private String sendPostRequest(String uri, String requestBody) {
        String response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader(MIME.CONTENT_TYPE, ContentConstant.MIMETYPE_JSON);
            if (requestBody != null) {
                httpPost.setEntity(new StringEntity(requestBody));
            }
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse != null) {
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    response = EntityUtils.toString(httpResponse.getEntity());
                } else {
                    log.error("Error Response :{}", EntityUtils.toString(httpResponse.getEntity()));
                }
            }
        } catch (IOException e) {
            log.error("unable to send request {}", uri, e);
        }
        return response;
    }

    private String sendGetRequest(String uri, String accessToken) {
        String response = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(uri);
            httpGet.addHeader("Authorization", "Bearer " + accessToken);
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse != null) {
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    response = EntityUtils.toString(httpResponse.getEntity());
                } else {
                    log.error("Error Response :{}", EntityUtils.toString(httpResponse.getEntity()));
                }
            }
        } catch (IOException e) {
            log.error("unable to send request {}", uri, e);
        }
        return response;
    }

    @ObjectClassDefinition(name = "Neuron Service Configuration", description = "This configuration reads the values to pull the data from neuron APIs")
    public @interface Config {

        @AttributeDefinition(name = "Neuron Base Url", description = "Enter the Neuron Host")
        String baseUrl() default "https://neuron2.triseptsolutions.com";

        @AttributeDefinition(name = "Client User Name", description = "Enter the Client User Name")
        String clientUserName() default "BENCAIAirMiles";

        @AttributeDefinition(name = "Client Password", description = "Enter the Client Password")
        String clientPassword() default "2x6qVmCCXYmrsKFj";

        @AttributeDefinition(name = "Client Password", description = "Enter the Client User Name")
        String[] packageMapping() default {"Flights=A03", "Stays=H18", "Rentals=C16", "Packages=AH05", "Activities=F17"};

    }
}
