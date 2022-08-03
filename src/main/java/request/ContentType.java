package request;

import javax.swing.text.AbstractDocument;
import java.util.Arrays;

public enum ContentType {
    CSS("css", "text/css"),
    JAVASCRIPT("js", "application/javascript"),
    HTML("html", "text/html"),
    TEXT("", "text/plain");


    private String fileExtension;
    private String contentType;

    ContentType(String fileExtension, String contentType) {
        this.fileExtension = fileExtension;
        this.contentType = contentType;
    }

    public static ContentType of(String fileExtension) {
        return Arrays.stream(values())
            .filter(contentType -> contentType.fileExtension.equals(fileExtension))
            .findFirst()
            .orElse(HTML);
    }

    public String getContentType() {
        return contentType;
    }
}
