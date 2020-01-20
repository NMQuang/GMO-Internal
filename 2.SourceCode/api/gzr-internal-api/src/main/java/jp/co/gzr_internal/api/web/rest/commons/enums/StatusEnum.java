package jp.co.gzr_internal.api.web.rest.commons.enums;

import org.apache.commons.lang3.StringUtils;

public enum StatusEnum {

    WORKING(1, "Đang làm việc"),
    
    JOBLESS(2, "Không có việc làm"),
    
    PROCEDURE_FOR_RESSIGNATION(3, "Đang làm thủ tục thôi việc"),
    
    SUSPEND_WORK(4, "Đình chỉ công tác"),
    
    RETIREMENT(5, "Nghỉ hưu"),
    
    QUIT_WORK(6, "Nghỉ việc"),
    
    OTHER_BREAK(7, "Nghỉ khác"),
    
    LOSING_JOB(8, "Mất việc làm"),
    
    FIRED(9, "Sa thải"),
    
    INTERN(10, "Thực tập sinh"),
    
    APPRENTICE(11, "Học việc"),
    
    PROBATION(12, "Thử việc");
    
    private final Integer value;

    /** The name. */
    private final String name;

    private StatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public Integer getValue() {
        return value;
    }


    public String getName() {
        return name;
    }

    public static StatusEnum get(String name) {
        for (StatusEnum e : StatusEnum.values()) {
            if (StringUtils.equals(name, e.getName())) {
                return e;
            }
        }

        throw new RuntimeException(" [not found] Mode for STATUS info >>" + name);
    }
}
