package ca.airmiles.travel.core.services.impl;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class NeuronServiceImplTest {

    private static final String PACKAGE_DETAILS = "{\"Package\": {\"Id\": \"A01\",\"Name\": \"BLUE TEST - AIR ONLY - TEST FILE\",\"Description\": \"\"}";

    NeuronServiceImpl fixture = new NeuronServiceImpl();
    @Mock
    NeuronServiceImpl.Config config;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse tokenResponse;

    @Mock
    private HttpEntity tokenEntity;

    @Mock
    private StatusLine tokenStatusLine;

    @Mock
    private CloseableHttpResponse packageResponse;

    @Mock
    private StatusLine packageStatusLine;

    @Mock
    private HttpEntity packageEntity;



    @BeforeEach
    void setUp() throws UnsupportedEncodingException {
        when(config.baseUrl()).thenReturn("https://neuron2.beta.triseptsolutions.com");
        when(config.clientUserName()).thenReturn("client-user-name");
        when(config.clientPassword()).thenReturn("client-password");
        when(config.packageMapping()).thenReturn(new String[]{"Flights=A01", "Stays=H01", "Rentals=C01"});
        fixture.activate(config);
        this.tokenEntity = new StringEntity("{\"AccessToken\":\"token\"}");
        this.packageEntity = new StringEntity(PACKAGE_DETAILS);
    }

    @Test
    void test_packageID() {
        assertNull(fixture.getPackageID("invalid-package"));
        assertEquals("A01", fixture.getPackageID("Flights"));
    }

    @Test
    void test_packages(){
        when(tokenResponse.getStatusLine()).thenReturn(tokenStatusLine);
        when(tokenStatusLine.getStatusCode()).thenReturn(200);
        when(tokenResponse.getEntity()).thenReturn(tokenEntity);
        when(packageResponse.getStatusLine()).thenReturn(packageStatusLine);
        when(packageStatusLine.getStatusCode()).thenReturn(200);
        when(packageResponse.getEntity()).thenReturn(packageEntity);
        final MockedConstruction.MockInitializer<HttpPost> mockInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Tokens", context.arguments().get(0));
            doReturn(tokenResponse).when(httpClient)
                    .execute(mock);
        };
        final MockedConstruction.MockInitializer<HttpGet> mockGetInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Packages/A01", context.arguments().get(0));
            doReturn(packageResponse).when(httpClient)
                    .execute(mock);
        };
        try (MockedConstruction<HttpPost> construction = mockConstruction(HttpPost.class, mockInitializer);
             MockedConstruction<HttpGet> getConstruction = mockConstruction(HttpGet.class, mockGetInitializer);
             MockedStatic<HttpClients> mockedStatic = mockStatic(HttpClients.class)) {
            mockedStatic.when(HttpClients::createDefault)
                    .thenReturn(httpClient);
            assertEquals(PACKAGE_DETAILS,fixture.getPackages("A01"));
        }
    }

    @Test
    void test_invalid_packages(){
        when(tokenResponse.getStatusLine()).thenReturn(tokenStatusLine);
        when(tokenStatusLine.getStatusCode()).thenReturn(200);
        when(tokenResponse.getEntity()).thenReturn(tokenEntity);
        when(packageResponse.getStatusLine()).thenReturn(packageStatusLine);
        when(packageStatusLine.getStatusCode()).thenReturn(500);
        when(packageResponse.getEntity()).thenReturn(packageEntity);
        final MockedConstruction.MockInitializer<HttpPost> mockInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Tokens", context.arguments().get(0));
            doReturn(tokenResponse).when(httpClient)
                    .execute(mock);
        };
        final MockedConstruction.MockInitializer<HttpGet> mockGetInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Packages/A01", context.arguments().get(0));
            doReturn(packageResponse).when(httpClient)
                    .execute(mock);
        };
        try (MockedConstruction<HttpPost> construction = mockConstruction(HttpPost.class, mockInitializer);
             MockedConstruction<HttpGet> getConstruction = mockConstruction(HttpGet.class, mockGetInitializer);
             MockedStatic<HttpClients> mockedStatic = mockStatic(HttpClients.class)) {
            mockedStatic.when(HttpClients::createDefault)
                    .thenReturn(httpClient);
            assertNull(fixture.getPackages("A01"));
        }
    }

    @Test
    void test_invalid_token(){
        when(tokenResponse.getStatusLine()).thenReturn(tokenStatusLine);
        when(tokenStatusLine.getStatusCode()).thenReturn(500);
        when(tokenResponse.getEntity()).thenReturn(tokenEntity);
        final MockedConstruction.MockInitializer<HttpPost> mockInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Tokens", context.arguments().get(0));
            doReturn(tokenResponse).when(httpClient)
                    .execute(mock);
        };
        try (MockedConstruction<HttpPost> construction = mockConstruction(HttpPost.class, mockInitializer);
             MockedStatic<HttpClients> mockedStatic = mockStatic(HttpClients.class)) {
            mockedStatic.when(HttpClients::createDefault)
                    .thenReturn(httpClient);
            assertNull(fixture.getPackages("A01"));
        }
    }

    @Test
    void test_token_exception(){
        final MockedConstruction.MockInitializer<HttpPost> mockInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Tokens", context.arguments().get(0));
            doThrow(IOException.class).when(httpClient)
                    .execute(mock);
        };
        try (MockedConstruction<HttpPost> construction = mockConstruction(HttpPost.class, mockInitializer);
             MockedStatic<HttpClients> mockedStatic = mockStatic(HttpClients.class)) {
            mockedStatic.when(HttpClients::createDefault)
                    .thenReturn(httpClient);
            assertNull(fixture.getPackages("A01"));
        }

    }
    @Test
    void test_package_exception(){
        when(tokenResponse.getStatusLine()).thenReturn(tokenStatusLine);
        when(tokenStatusLine.getStatusCode()).thenReturn(200);
        when(tokenResponse.getEntity()).thenReturn(tokenEntity);
        final MockedConstruction.MockInitializer<HttpPost> mockInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Tokens", context.arguments().get(0));
            doReturn(tokenResponse).when(httpClient)
                    .execute(mock);
        };
        final MockedConstruction.MockInitializer<HttpGet> mockGetInitializer = (mock, context) -> {
            assertEquals("https://neuron2.beta.triseptsolutions.com/api/v1/Packages/A01", context.arguments().get(0));
            doThrow(IOException.class).when(httpClient)
                    .execute(mock);
        };
        try (MockedConstruction<HttpPost> construction = mockConstruction(HttpPost.class, mockInitializer);
             MockedConstruction<HttpGet> getConstruction = mockConstruction(HttpGet.class, mockGetInitializer);
             MockedStatic<HttpClients> mockedStatic = mockStatic(HttpClients.class)) {
            mockedStatic.when(HttpClients::createDefault)
                    .thenReturn(httpClient);
            assertNull(fixture.getPackages("A01"));
        }
    }

}