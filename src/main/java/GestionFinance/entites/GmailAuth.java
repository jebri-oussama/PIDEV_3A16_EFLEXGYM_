package GestionFinance.entites;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow.Builder;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import java.io.IOException;
import java.util.Collections;

public class GmailAuth {
    /*private static final String CLIENT_ID = "votre_client_id";
    private static final String CLIENT_SECRET = "votre_client_secret";
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    private static final String APPLICATION_NAME = "Votre_nom_d'application";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static Credential authorize() throws IOException {
        AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(
                new Builder(HTTP_TRANSPORT, JSON_FACTORY, new TokenResponse(), new String[0])
                        .setCredentialDataStore(new FileDataStoreFactory(new java.io.File("tokens")))
                        .setAccessType("offline")
                        .build(),
                new AuthorizationCodeInstalledApp.Browser() {
                    @Override
                    public void browse(String url) throws IOException {
                        System.out.println("Authorize this application to access your Gmail account:");
                        System.out.println(url);
                    }
                }
        ).build();

        return new AuthorizationCodeInstalledApp(flow, new AuthorizationCodeInstalledApp.Browser() {
            @Override
            public void browse(String url) throws IOException {
                System.out.println("Authorize this application to access your Gmail account:");
                System.out.println(url);
            }
        }).authorize("user");
    }*/
}
