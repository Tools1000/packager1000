package io.github.javacodesign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotarizerResponse {

    @Getter
    @Setter
    static class NotarizationUpload {

        public boolean isOk() {
            return requestUuid != null;
        }

        @JsonProperty("RequestUUID")
        String requestUuid;
    }

    @Getter
    @Setter
    static class NotarizationInfo {

        public boolean isOk() {
            return status != null && "success".equals(status);
        }

        @JsonProperty("Status")
        String status;

        @JsonProperty("RequestUUID")
        String requestUuid;

        @JsonProperty("Status Message")
        String statusMessage;
    }

    @JsonProperty("notarization-upload")
    NotarizationUpload notarizationUpload;

    @JsonProperty("notarization-info")
    NotarizationInfo notarizationInfo;

    @JsonProperty("success-message")
    String successMessage;
}
