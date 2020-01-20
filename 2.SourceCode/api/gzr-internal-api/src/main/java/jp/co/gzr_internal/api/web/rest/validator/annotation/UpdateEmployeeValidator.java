package jp.co.gzr_internal.api.web.rest.validator.annotation;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import jp.co.gzr_internal.api.repository.CustomEmployeeRepository;
import jp.co.gzr_internal.api.repository.MDivisionRepository;
import jp.co.gzr_internal.api.repository.MPositionRepository;
import jp.co.gzr_internal.api.repository.MStatusRepository;
import jp.co.gzr_internal.api.repository.MTypeContractRepository;
import jp.co.gzr_internal.api.service.dto.request.UpdateEmployeeRequestDto;

public class UpdateEmployeeValidator implements HibernateConstraintValidator<UpdateEmployee, UpdateEmployeeRequestDto> {

    private final CustomEmployeeRepository customEmployeeRepository;

    private final MDivisionRepository mDivisionRepository;

    private final MPositionRepository mPositionRepository;

    private final MStatusRepository mStatusRepository;

    private final MTypeContractRepository mTypeContractRepository;

    public UpdateEmployeeValidator(CustomEmployeeRepository customEmployeeRepository,
        MDivisionRepository mDivisionRepository, MPositionRepository mPositionRepository,
        MStatusRepository mStatusRepository, MTypeContractRepository mTypeContractRepository) {
        super();
        this.customEmployeeRepository = customEmployeeRepository;
        this.mDivisionRepository = mDivisionRepository;
        this.mPositionRepository = mPositionRepository;
        this.mStatusRepository = mStatusRepository;
        this.mTypeContractRepository = mTypeContractRepository;
    }

    @Override
    public boolean isValid(UpdateEmployeeRequestDto requestDto, ConstraintValidatorContext context) {

        if (requestDto == null) {
            return false;
        }

        // Initial flag valid = true
        boolean isValid = true;
        // Set default constraint violation
        context.disableDefaultConstraintViolation();
        // unwrap context to hibernateContext
        HibernateConstraintValidatorContext hibernateContext = context
            .unwrap(HibernateConstraintValidatorContext.class);
        return true;
    }

}
