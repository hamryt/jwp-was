package request;

import java.util.Arrays;

public enum ContentType {
    CSS("css", "text/css"),
    JAVASCRIPT("js", "application/javascript"),
    HTML("html", "text/html"),
    TEXT("", "text/plain");


    private String fileExtension;
    private String mediaType;

    ContentType(String fileExtension, String mediaType) {
        this.fileExtension = fileExtension;
        this.mediaType = mediaType;
    }

    public static ContentType of(String fileExtension) {
        return Arrays.stream(values())
            .filter(contentType -> contentType.fileExtension.equals(fileExtension))
            .findFirst()
            .orElse(HTML);
    }

    public String getMediaType() {
        return mediaType;
    }
}
