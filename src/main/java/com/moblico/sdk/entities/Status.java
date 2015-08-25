package com.moblico.sdk.entities;

import java.util.Date;

public class Status {

    public enum StatusType {
        SUCCESS(0),
        INVALID_ERROR_TYPE(1),
        UNKNOWN_ERROR(2),
        INVALID_APP_TOKEN(4),
        INVALID_POSTAL_CODE(5),
        INVALID_LATITUDE(6),
        INVALID_LONGITUDE(7),
        EMPTY_FIRST_NAME(8),
        EMPTY_LAST_NAME(9),
        INVALID_EMAIL(10),
        USER_ALREADY_EXISTS(11),
        INVALID_USERNAME(12),
        INVALID_PASSWORD(13),
        INVALID_STORE_ID(14),
        NO_GEO_MATCH(15),
        INVALID_SECURITY_TOKEN(17),
        NO_APPLICATION_SETTINGS_FOUND(18),
        NO_ADS_FOUND(19),
        NO_DEALS_FOUND(20),
        INVALID_DEAL_ID(21),
        DEAL_ALREADY_REDEEMED(22),
        INVALID_USER(27),
        PASSWORD_MISMATCH(28),
        NO_DEAL_IMAGE_FOUND(29),
        INVALID_ACCOUNT(38),
        INVALID_MESSAGE(39),
        INVALID_PHONE(40),
        INVALID_GROUP(41),
        INVALID_PLATFORM(43),
        COULD_NOT_SEND_MESSAGE(44),
        INVALID_PAYLOAD(45),
        UNSUPPORTED_MESSAGE_TYPE(48),
        ALREADY_REDEEMED(49),
        MAX_REDEMPTIONS_REACHED(50),
        NOT_AUTHORIZED(51),
        DEAL_EXPIRED(52),
        UNSUPPORTED_PAYMENT_TYPE(53),
        NO_GEO_MATCH_FOR_EVENTS(54),
        INVALID_AUTH_TOKEN(55),
        USER_REQUIRED(61),
        INVALID_LOCATION_ID(62),
        NO_MEDIA_FOUND(63),
        NO_GEO_MATCHES(66),
        NO_LOCATION_FOUND(67),
        UNSUPPORTED_MEDIA_FORMAT(68),
        USER_SUSPENDED(69),
        USER_ALREADY_RELATED(70),
        USER_NOT_RELATED(71),
        NO_USERS_FOUND(72),
        USER_ALREADY_REGISTERED(73),
        USER_ALREADY_CHECKED_IN(74),
        NO_AFFINITY_FOUND(75),
        INVALID_EVENT(78),
        USER_NOT_FOUND(86),
        INVALID_DATE_OF_BIRTH(88),
        INVALID_DEVICE_ID(89),
        DEVICE_NOT_REGISTERED(90),
        INVALID_NOTIFICATION_ID(91),
        INVALID_DATE(92),
        USER_ACCOUNT_LOCKED(93),
        FORCE_PASSWORD_CHANGE(94),
        INVALID_GENDER(97),
        INVALID_EVENT_ID(98),
        NO_CREDENTIALS_FOUND(99),
        INVALID_LEADERBOARD_TYPE(100),
        ALREADY_PURCHASED(126),
        NOT_ENOUGH_POINTS(127),
        NO_COMMERCE_POINT_TYPE(128),
        NOT_PURCHASABLE(129),
        INVALID_API_KEY(130),
        INVALID_REQUEST(131),
        NO_REWARDS_FOUND(132),
        INVALID_REWARD_ID(133),
        INVALID_STATE(134),
        INVALID_CONTENT_NAME(135),
        NO_CONTENT_FOUND(136),
        INVALID_CONTENT_FIELDS(137),
        NO_EVENTS_FOUND(138),
        NO_GROUPS_FOUND(141),
        INVALID_AMOUNT(142),
        NO_MERCHANT_FOUND(143),
        PHONE_ALREADY_USED(144),
        EMAIL_ALREADY_USED(145);

        private final int value;
        private StatusType(final int value) {
            this.value = value;
        }

        public int toInt() {
            return this.value;
        }

        public static StatusType valueOf(final int statusType) {
            for (StatusType type: StatusType.values()) {
                if (type.toInt() == statusType) {
                    return type;
                }
            }
            return null;
        }

    }

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

    public boolean hasStatus() {
        return status != null;
    }

    public String getMessage() {
        return status.message;
    }

    public StatusType getStatusType() {
        return StatusType.valueOf(status.statusType);
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
