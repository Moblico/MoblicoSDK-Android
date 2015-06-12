package com.moblico.sdk.entities;

import java.util.Date;

public class Status {

    private class InternalStatus {

        private final String message;
        private final int statusType;
        private final String helpUrl;
        private final int httpStatus;
        private final String verboseMessage;

        private InternalStatus(final String message, final int statusType, final String helpUrl,
                               final int httpStatus, final String verboseMessage) {
            this.message = message;
            this.statusType = statusType;
            this.helpUrl = helpUrl;
            this.httpStatus = httpStatus;
            this.verboseMessage = verboseMessage;
        }

    }

    private final InternalStatus status;

    private Status(final InternalStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return status.message;
    }

    public int getStatusType() {
        return status.statusType;
    }

    public String getHelpUrl() {
        return status.helpUrl;
    }

    public int getHttpStatus() {
        return status.httpStatus;
    }

    public String getVerboseMessage() {
        return status.verboseMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(getHttpStatus() > 0) {
            sb.append("HTTP ");
            sb.append(getHttpStatus());
            sb.append(": ");
        }
        if(getMessage() != null && getMessage().length() > 0) {
            sb.append(getMessage());
        }
        if(getVerboseMessage() != null && getVerboseMessage().length() > 0) {
            sb.append('\n');
            sb.append(getVerboseMessage());
        }
        if(getHelpUrl() != null && getHelpUrl().length() > 0) {
            sb.append('\n');
            sb.append("More info at: ");
            sb.append(getHelpUrl());
        }
        return sb.toString();
    }
}
