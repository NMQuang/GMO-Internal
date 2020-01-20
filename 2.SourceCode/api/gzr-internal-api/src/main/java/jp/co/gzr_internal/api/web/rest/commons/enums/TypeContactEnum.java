package jp.co.gzr_internal.api.web.rest.commons.enums;

import org.apache.commons.lang3.StringUtils;

public enum TypeContactEnum {

    CONTRACT_DEFINITE(1, "Hợp đồng xác định thời hạn"),
    
    PROBATION(2, "Thử việc"),
    
    CONTRACT_INDEFINITE(3, "Hợp đồng không xác định thời hạn"),
    
    APPREENTICE(4, "Học việc"),
    
    CONTRACT_SEASON(5, "Hợp đồng mùa vụ"),
    
    CONTRACT_SERVICE(6, "Hợp đồng dịch vụ");
    
    private final Integer value;

    /** The name. */
    private final String name;

    private TypeContactEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
    
    public static TypeContactEnum get(String name) {
        for (TypeContactEnum e : TypeContactEnum.values()) {
            if (StringUtils.equals(name, e.getName())) {
                return e;
            }
        }

        throw new RuntimeException(" [not found] Mode for type contact info >>" + name);
    }
    
}
