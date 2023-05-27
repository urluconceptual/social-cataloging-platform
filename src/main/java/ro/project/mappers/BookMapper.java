package ro.project.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import ro.project.model.Book;
import ro.project.model.enums.BookGenre;

public class BookMapper {
    public Book bookMapper(JsonNode jsonNode) {
        String author = jsonNode.get("author").asText();
        String title = jsonNode.get("title").asText();
        Integer numberOfPages = Integer.parseInt(jsonNode.get("book_image_width").asText());

        return Book.builder()
                   .author(author)
                   .title(title)
                   .numberOfPages(numberOfPages)
                   .genre(BookGenre.OTHER)
                   .build();
    }
}
