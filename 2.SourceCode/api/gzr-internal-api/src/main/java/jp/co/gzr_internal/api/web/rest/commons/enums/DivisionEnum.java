package jp.co.gzr_internal.api.web.rest.commons.enums;

import org.apache.commons.lang3.StringUtils;

public enum DivisionEnum {

    HCM_BRANCH(1, "Ho Chi Minh Branch"),

    HCM_SMARTGIFT(2, "HCM - Smartgift "),

    ESD(3, "Enterprise Solutions Division (ESD)"),

    HCM_RD(4, "HCM - R&D"),

    HCM_QAQC(5, "HCM - QA/QC"),

    HCM_NIKKO(6, "HCM - Nikko"),

    IID_ACCOUNTING(7, "IID-Accounting HCM"),

    IID_MONEY_MAKER(8, "IID-Money Maker Team (HCM)"),

    IID_POLICY(9, "IID-Policy & Terms (HCM)");

    private final Integer value;

    /** The name. */
    private final String name;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static DivisionEnum get(String name) {
        for (DivisionEnum e : DivisionEnum.values()) {
            if (StringUtils.equals(name, e.getName())) {
                return e;
            }
        }

        throw new RuntimeException(" [not found] Mode for division info >>" + name);
    }

    private DivisionEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
