package request;

import java.util.Objects;

public class ProtocolVersion {

    private static final String DELIMITER = "/";
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private final String protocol;
    private final String version;

    public ProtocolVersion(String protocolVersion) {
        String[] splits = protocolVersion.split(DELIMITER);
        this.protocol = splits[PROTOCOL_INDEX];
        this.version = splits[VERSION_INDEX];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getValue() {
        return protocol + DELIMITER + version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProtocolVersion that = (ProtocolVersion) o;
        return Objects.equals(protocol, that.protocol) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, version);
    }
}
