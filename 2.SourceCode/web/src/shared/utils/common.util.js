import { escape } from "../constants/escape.constant";

// define common util
const commonUtil = {};

/**
 * parse message with dynamic param
 * @param {String} message
 * @param {String[]} param
 * @return {String} parsed message
 */
commonUtil.parseMessage = (message, param) => {
  if (message === undefined || message === null || message.indexOf("%p") < 0) {
    return message;
  }
  let i = 0;
  return message.replace(/%p/g, () => param[i++]);
};

/**
 * replace string
 * @param {String} strEscape
 * @param {String} strQuoteEscape
 * @param {String} strQuote
 */
commonUtil.replaceAll = (strEs, strQuoteEscape, strQuote) => {
  let str = strEs;
  if (str === undefined || str === null) {
    return str;
  }
  if ((str + escape.CONST_STR_BLANK).indexOf(strQuoteEscape) === -1) {
    return str;
  }
  return str.replace(new RegExp(strQuoteEscape, "g"), function() {
    return strQuote;
  });
};

/**
 * escape element HTML
 * @param {String} strEscape
 */
commonUtil.escapeHTML = strEscape => {
  let strEs = strEscape;
  if (strEs === undefined || strEs === null) {
    return strEs;
  }
  strEs = commonUtil.replaceAll(
    strEs,
    escape.CONST_STR_QUOTE_ESCAPE,
    escape.CONST_STR_QUOTE
  );
  strEs = commonUtil.replaceAll(
    strEs,
    escape.CONST_STR_LT_ESCAPE,
    escape.CONST_STR_LT
  );
  strEs = commonUtil.replaceAll(
    strEs,
    escape.CONST_STR_GT_ESCAPE,
    escape.CONST_STR_GT
  );
  strEs = commonUtil.replaceAll(
    strEs,
    escape.CONST_STR_AMP_ESCAPE,
    escape.CONST_STR_AMP
  );
  return strEs;
};

commonUtil.unescapeHtmlObject = object => {
  // If object not null then unscape properties value
  if (object !== undefined && object !== null) {
    Object.keys(object).map(key => {
      // If value not null then unscape value
      if (object[key] !== undefined && object[key] !== null) {
        object[key] = commonUtil.escapeHTML(object[key]);
      }
    });
  }
  return object;
};

commonUtil.getPathNameUri = () => window.location.pathname;

export default commonUtil;
