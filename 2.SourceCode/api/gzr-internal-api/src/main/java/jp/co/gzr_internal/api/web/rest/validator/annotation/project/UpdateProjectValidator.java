package jp.co.gzr_internal.api.web.rest.validator.annotation.project;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jp.co.gzr_internal.api.domain.ProjectRank;
import jp.co.gzr_internal.api.repository.EmployeeRepository;
import jp.co.gzr_internal.api.repository.ProjectRankRepository;
import jp.co.gzr_internal.api.service.dto.request.UpdateProjectRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import jp.co.gzr_internal.api.web.rest.validator.common.ValidateCommon;

/**
 * The Class CreateBlockSMSValidator
 */
public class UpdateProjectValidator implements HibernateConstraintValidator<UpdateProject, UpdateProjectRequestDto> {

    private final EmployeeRepository employeeRepository;

    private final ProjectRankRepository projectRankRepository;

    /**
     * Instantiates a new creates the block SMS request DTO validator.
     *
     * @param smsTypeRepository the sms type repository
     */
    public UpdateProjectValidator(EmployeeRepository employeeRepository, ProjectRankRepository projectRankRepository) {
        super();
        this.employeeRepository = employeeRepository;
        this.projectRankRepository = projectRankRepository;
    }

    /**
     * Checks if is valid.
     *
     * @param createBlockSMSRequestDTO the create block SMS request DTO
     * @param context the context
     * @return true, if is valid
     */
    @Override
    public boolean isValid(UpdateProjectRequestDto updateProjectRequestDTO, ConstraintValidatorContext context) {

        if (updateProjectRequestDTO == null) {
            return false;
        }

        String projectNameJP = updateProjectRequestDTO.getProjectNameJP();
        String projectNameVN = updateProjectRequestDTO.getProjectNameVN();
        String startDate = updateProjectRequestDTO.getStartDate();
        String endDate = updateProjectRequestDTO.getEndDate();
        String projectRank = updateProjectRequestDTO.getProjectRank();

        // Initial flag valid = true
        boolean isValid = true;

        // Set default constraint violation
        context.disableDefaultConstraintViolation();
        // unwrap context to hibernateContext
        HibernateConstraintValidatorContext hibernateContext = context
            .unwrap(HibernateConstraintValidatorContext.class);

        if (Contants.CONST_STR_BLANK.equals(projectNameJP) && Contants.CONST_STR_BLANK.equals(projectNameVN)) {
            hibernateContext
                .buildConstraintViolationWithTemplate("{" + MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY + "}")
                .addPropertyNode("projectNameJP").addConstraintViolation();
            hibernateContext
                .buildConstraintViolationWithTemplate("{" + MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY + "}")
                .addPropertyNode("projectNameVN").addConstraintViolation();
            isValid = false;
        }

        // Check validate field StartDate and EndDate
        if (!Contants.CONST_STR_BLANK.equals(startDate) && !Contants.CONST_STR_BLANK.equals(endDate)) {

            if (startDate.matches(ValidateCommon.DATE_REGEX) && endDate.matches(ValidateCommon.DATE_REGEX)) {
                // Parse holdeStartDate and startDate to type Date
                LocalDate dateFrom = Utils.convertStringToLocalDate(startDate, Contants.CONST_STR_PATTERN_YYYYMMDD);
                LocalDate dateTo = Utils.convertStringToLocalDate(endDate, Contants.CONST_STR_PATTERN_YYYYMMDD);

                // Check dateFrom > dateTo
                if (dateFrom != null && dateTo != null && dateFrom.compareTo(dateTo) > 0) {
                    hibernateContext
                        .buildConstraintViolationWithTemplate("{" + MessageContants.CONST_VALIDATE_FROM_LARGE_TO + "}")
                        .addPropertyNode("startDate").addConstraintViolation();
                    isValid = false;
                }
            }

        }
        if (Integer.valueOf(projectRank).intValue() != 0) {
            Optional<ProjectRank> optionalRank = projectRankRepository.findByIdAndDeleteFlag(Integer.valueOf(projectRank), 0);

            if (!optionalRank.isPresent()) {
                hibernateContext
                    .buildConstraintViolationWithTemplate("{" + MessageContants.CONST_VALIDATE_VALUE_NOT_EXIST + "}")
                    .addPropertyNode("projectRank").addConstraintViolation();
                isValid = false;
            }

        }
        
        for (String email : updateProjectRequestDTO.getEmailCC()) {
            if (!email.matches(ValidateCommon.EMAIL_REGEX)) {
                hibernateContext.buildConstraintViolationWithTemplate("{" + MessageContants.CONST_VALIDATE_EMAIL + "}")
                    .addPropertyNode("emailCC").addConstraintViolation();
                isValid = false;
            }
        }

        /**
         * check flag isValid if isValid = true then value of Object is valid => return true if isValid = false then
         * value of Obhject is inValid => return false
         */
        if (!isValid) {
            return false;
        }
        return true;
    }
}
