package ro.project.gateways;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.project.mappers.BookMapper;
import ro.project.model.Book;
import ro.project.service.BookService;
import ro.project.service.impl.BookServiceImpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Requests {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final BookMapper bookMapper = new BookMapper();

    private static BookService bookService = new BookServiceImpl();

    public void saveRequestInfo() {
        try {
            URI uri = new URI("https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=mGR3Y6AZ1399xB6G19ZHIAV18AdXpi60");
            HttpRequest httpRequest = HttpRequest.newBuilder()
                                                 .uri(uri)
                                                 .GET()
                                                 .build();

            var httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonNode content = objectMapper.readTree(httpResponse.body()).get("results").get("books");

            for (JsonNode jsonNode : content) {
                Book book = bookMapper.bookMapper(jsonNode);

                bookService.addBook(book);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
