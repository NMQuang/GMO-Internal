package jp.co.gzr_internal.api.web.rest.commons.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * The Enum RequestStatusEnum.
 */
public enum RequestStatusEnum {

    /** The verify. */
    VERIFY(1, "Verify"),

    /** The pass. */
    PASS(2, "Pass"),

    /** The approval. */
    APPROVAL(3, "Approval"),

    /** The cancel. */
    CANCEL(4, "Cancel");

    /** The value. */
    private final Integer value;

    /** The name. */
    private final String name;

    /**
     * Instantiates a new request status enum.
     *
     * @param value the value
     * @param name the name
     */
    private RequestStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Integer getValue() {
        return this.value;
    }

    /**
     * Get RequestStatusEnum by name.
     *
     * @param name name enum
     * @return RequestStatusEnum
     */
    public static RequestStatusEnum get(String name) {
        for (RequestStatusEnum e : RequestStatusEnum.values()) {
            if (StringUtils.equals(name.toUpperCase(), e.getName())) {
                return e;
            }
        }

        throw new RuntimeException(" [not found] Mode for request status info >>" + name);
    }

}
