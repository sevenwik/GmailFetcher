package com.gmailFetcher.gmailService.Configurations;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GmailConfig {

    private static final String APPLICATION_NAME = "GmailFetcher"; // Replace with your application name

    @Bean
    public Gmail gmail() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = new NetHttpTransport();

        return new Gmail.Builder(httpTransport, JacksonFactory.getDefaultInstance(), requestInitializer())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Bean
    public HttpRequestInitializer requestInitializer() throws GeneralSecurityException, IOException {
        return new HttpCredentialsAdapter(getCredentials());
    }

    private Credentials getCredentials() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                getClass().getResourceAsStream("/Users/sathwik/Downloads/gmailService/src/main/java/credentials.json")
        );

        if (googleCredentials instanceof ServiceAccountCredentials) {
            List<String> scopes = ImmutableList.of(GmailScopes.GMAIL_READONLY);
            return ((ServiceAccountCredentials) googleCredentials).createScoped(scopes);
        }

        return googleCredentials;
    }
}
