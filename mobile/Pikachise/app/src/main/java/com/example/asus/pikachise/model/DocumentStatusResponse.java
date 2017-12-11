package com.example.asus.pikachise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by WilliamSumitro on 02/12/2017.
 */

public class DocumentStatusResponse {
    @SerializedName("document_status")
    @Expose
    private DocumentStatus documentStatus;

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }
}
