package io.github.javacodesign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class NotarizerResponse {

    public boolean isUploadOk() {
        return notarizationUpload != null && notarizationUpload.requestUuid != null;
    }

    public boolean isInfoOk() {
        return notarizationInfo != null && "success".equals(notarizationInfo.status);
    }

    @ToString
    @Getter
    @Setter
    static class ProductError {

    }

    @ToString
    @Getter
    @Setter
    static class NotarizationUpload {

        @JsonProperty("RequestUUID")
        String requestUuid;
    }

    @ToString
    @Getter
    @Setter
    static class NotarizationInfo {

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

    @JsonProperty("product-errors")
    List<ProductError> productErrors;
}
