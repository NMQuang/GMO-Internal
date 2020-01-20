import moment from "moment";

const validateUtil = {};

/**
 * Check empty
 */
validateUtil.isEmpty = value => {
  if (value.trim().length === 0) {
    return true;
  }
  return false;
};

/**
 * Check number
 */
validateUtil.checkNumber = value => {
  return validateUtil.patternNumber.test(value);
};

/**
 * Check length
 */
validateUtil.checkLength = (value, length) => {
  return value.length === length;
};

/**
 * Check minlength
 */
validateUtil.minLength = (value, min) => {
  return value.length < min;
};

/**
 * Check maxlength
 */
validateUtil.maxLength = (value, max) => {
  return value.length > max;
};

/**
 * Check in range
 */
validateUtil.inRange = (value, min, max) => {
  return value.length < min || value.length > max;
};

/**
 * Check value is match to other value
 */
validateUtil.matchOther = (value, otherValue) => {
  return value === otherValue;
};

/**
 * Check email
 */
validateUtil.checkEmail = value => {
  return validateUtil.patternEmail.test(value);
};

/**
 * Check password
 */
validateUtil.checkPassword = value => {
  return validateUtil.patternPassword.test(value);
};

validateUtil.checkString = value => {
  return validateUtil.patternWordOnly.test(value);
};

validateUtil.checkPhoneNumber = value => {
  return validateUtil.patternPhone.test(value);
};

validateUtil.checkAddress = value => {
  return validateUtil.patternAddress.test(value);
};

// Pattern number
validateUtil.patternNumber = /^\d*$/;

// Pattern word only
validateUtil.patternWordOnly = /^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ ]+$/;

// Pattern email
validateUtil.patternEmail = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;

// Pattern password
validateUtil.patternPassword = /^[a-zA-Z0-9@%+\/'!#$^?:.(){}\[\]~\-_.]*$/;

validateUtil.patternPhone = /^[0-9]*$/;

validateUtil.patternAddress = /^$|^[a-zA-Z0-9_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ /.,\n]+$/;

/**
 * Is null
 */
validateUtil.isNull = value => {
  if (value == null) {
    return true;
  }
  return false;
};
/**
 * Is not timeOT
 */
validateUtil.isNotTimeOT = value => {
  if (parseFloat(value) < 0 || parseFloat(value) > 24) {
    return true;
  }
  return false;
};
/**
 * Check pattern
 */
validateUtil.checkPattern = (value, pattern) => {
  return pattern.test(value);
};
/**
 * Is not date OT
 */
validateUtil.isNotDateOT = (value, pattern) => {
  let dateValue = moment(value).format(pattern);
  let dateNow = moment().format(pattern);
  if (moment(dateValue, pattern) < moment(dateNow, pattern)) {
    return true;
  }
  return false;
};

validateUtil.checkDateValid = (dateSource, dateTarget, pattern) => {
  let sourceValue = moment(dateSource).format(pattern);
  let targetourceValue = moment(dateTarget).format(pattern);
  return sourceValue > targetourceValue ? false : true;
};

validateUtil.checkStartDateValid = (dateSource, dateTarget, pattern) => {
  let sourceValue = moment(dateSource).format(pattern);
  let targetourceValue = moment(dateTarget).format(pattern);
  return sourceValue < targetourceValue ? false : true;
};

/**
 * Is not date OT update
 */
validateUtil.isNotDateOTUpdate = (value, valueOld, pattern) => {
  if (typeof valueOld === "undefined" || valueOld === "") {
    return false;
  }
  let dateValue = moment(value).format(pattern);
  let dateValueOld = moment(valueOld).format(pattern);
  let dateNow = moment().format(pattern);
  if (moment(dateValue, pattern).diff(moment(dateValueOld, pattern)) == 0) {
    return false;
  }
  if (moment(dateValue, pattern) < moment(dateNow, pattern)) {
    return true;
  }
  return false;
};
validateUtil.inValidYear = (minYear, maxYear, year) => {
  if (year < minYear || year > maxYear) {
    return false;
  }
  return true;
};
export default validateUtil;
