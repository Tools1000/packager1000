package io.github.javacodesign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotarizerResponse {

    public boolean isUploadOk() {
        return notarizationUpload != null && notarizationUpload.requestUuid != null;
    }

    public boolean isInfoOk() {
        return notarizationInfo != null && "success".equals(notarizationInfo.status);
    }

    @Getter
    @Setter
    static class ProductError {

    }

    @Getter
    @Setter
    static class NotarizationUpload {

        @JsonProperty("RequestUUID")
        String requestUuid;
    }

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
