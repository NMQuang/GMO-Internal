import { Route, Redirect, withRouter } from "react-router-dom";
import { connect } from "react-redux";
import { userReducer } from "../../main/modules/user/user.reducer";
import commonUtil from "../utils/common.util";
import { storage } from "../constants/storage.constant";

/**
 * Private Route Component
 */
const PrivateRouteComponent = ({
  component: Component,
  path,
  isInitial,
  isAuthenticated,
  hasAnyAuthorities,
  isAuthorized,
  ...rest
}) => {
  /**
   * check authority of employee
   * @param {*} props
   */
  const checkAuthorities = props => {
    const check = hasAnyAuthority(isAuthorized, hasAnyAuthorities);
    return check ? (
      <Component {...props} />
    ) : (
      <Redirect
        to={{
          pathname: "/permission",
          state: {
            prevLocation: path,
            error: "You are not authorized to access this page."
          }
        }}
      />
    );
  };

  /**
   * check initial route
   * @param {*} props
   */
  const checkInitialRoute = props => {
    return isInitial ? <Redirect to="/login" /> : <Component {...props} />;
  };

  /**
   * check employee had logined or not yet
   * @param {*} props
   */
  const renderRedirect = props => {
    return isAuthenticated ? checkAuthorities(props) : checkInitialRoute(props);
  };

  // Set session for currentUri and PrevUri
  const currentUrl = commonUtil.getPathNameUri();
  const prevUrl = sessionStorage.getItem(storage.CURRENT_URI);

  if (currentUrl !== prevUrl) {
    sessionStorage.setItem(storage.CURRENT_URI, currentUrl);
    sessionStorage.setItem(storage.PREV_URI, prevUrl);
    if (prevUrl === null || !prevUrl.includes("/project")) {
      sessionStorage.removeItem(storage.SEARCH_PROJECT);
    }
    if (prevUrl === null || !prevUrl.includes("/request")) {
      sessionStorage.removeItem(storage.SEARCH_REQUEST);
    }
  }

  return <Route path={path} {...rest} render={renderRedirect} />;
};

/**
 * check grant of employee
 * @param {*} authority
 * @param {*} hasAnyAuthorities
 */
const hasAnyAuthority = (authority, hasAnyAuthorities) => {
  if (hasAnyAuthorities && hasAnyAuthorities.length !== 0) {
    if (hasAnyAuthorities.includes(authority)) {
      return true;
    }
    return false;
  }
  return true;
};

// todo: state
// todo: isAuthorized from employee
const mapStateToProps = state => {
  return {
    isAuthorized: state.userReducer.isAuthenticated
      ? state.userReducer.data.resultData.roleId
      : 0
  };
};

export const PrivateRoute = connect(mapStateToProps, null, null, {
  pure: false
})(PrivateRouteComponent);

export default PrivateRoute;
