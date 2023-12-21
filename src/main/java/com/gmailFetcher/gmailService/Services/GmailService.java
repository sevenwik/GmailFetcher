package com.gmailFetcher.gmailService.Services;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import java.util.Collections;



import java.io.IOException;
import java.util.List;


@Service
public class GmailService {
    private final Gmail gmail;

    @Autowired
    public GmailService(Gmail gmail) {
        this.gmail = gmail;
    }

    public List<Email> getLatestEmails() throws IOException {
        try {
            List<Message> messages = gmail.users().messages().list("me").setMaxResults(Long.valueOf(200)).execute().getMessages();

            return messages.stream()
                    .map(message -> {
                        try {
                            return convertToEmail(message);
                        } catch (IOException e) {
                            // Handle the exception or provide a default Email
                            e.printStackTrace();
                            return new Email("Error getting sender", "Error getting subject");
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            // Handle the IOException or log it
            e.printStackTrace();
            return Collections.emptyList(); // Return an empty list or handle it according to your use case
        }
    }

    private Email convertToEmail(Message message) throws IOException {
        try {
            String sender = getEmailHeader(message, "From");
            String subject = getEmailHeader(message, "Subject");

            return new Email(sender, subject);
        } catch (IOException e) {
            // Handle the IOException or log it
            e.printStackTrace();
            return new Email("Error getting sender", "Error getting subject");
        }
    }

    private String getEmailHeader(Message message, String headerName) throws IOException {
        return message.getPayload().getHeaders().stream()
                .filter(header -> headerName.equals(header.getName()))
                .findFirst()
                .map(com.google.api.services.gmail.model.MessagePartHeader::getValue)
                .orElse("");
    }
}
