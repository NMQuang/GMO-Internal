package jp.co.gzr_internal.api.web.rest.commons.enums;

import org.apache.commons.lang3.StringUtils;

public enum PositionEnum {

    BRANCH_DIRECTOR(1, "Branch Director"),
    DEPARTMENT_MANAGER(2, "Department Manager"),
    PROJECT_MANAGER(3, "Project Manager"),
   BRSE(4, "BrSE"),
   QA_EXECUTIVE(5, "QA Executive"),
   TEAM_LEADER(6, "Team Leader"),
   BUSINESS_ANALYST(7, "Business Analyst"),
   DEVELOPER(8, "Developer"),
   TESTER(9, "Tester"),
   COMTOR(10, "Comtor"),
   EDUCATE(11, "Đào tạo"),
   APPRENTICE(12, "Học việc"),
   PROBATION(13, "Thử việc"),
   SALE_EXECUTIVE(14, "Sale Executive"),
   MEBER_BOM(15, "Member of BOM"),
   RD_ENGINEER(16, "R&D Engineer"),
   ADMIN_EXECUTIVE(17, "Admin Executive"),
   HR_EXECUTIVE(18, "HR Executive"),
   CREATIVE_EDITOR(19, "Creative editor"),
   ACCOUNTANT(20, "Accountant"),
   POLICY_TERM_EXECUTIVE(21, "Policy & Terms Executive"),
   NETWORK_ADMIN(22, "Network Administrator"),
   COLLABORATOR(23, "Cộng tác viên"),
   ASSISTANT(24, "Assistant"),
   DOMESTIC_WORKER(25, "Nhân viên tạp vụ");
    private final Integer value;

    /** The name. */
    private final String name;

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private PositionEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
    
    public static PositionEnum get(String name) {
        for (PositionEnum e : PositionEnum.values()) {
            if (StringUtils.equals(name, e.getName())) {
                return e;
            }
        }

        throw new RuntimeException(" [not found] Mode for type contact info >>" + name);
    }
}
