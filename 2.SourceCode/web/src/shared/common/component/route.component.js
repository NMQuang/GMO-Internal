import { Switch, Route, Router } from "react-router-dom";
import PrivateRoute from "../../auths/private-route.js";
import { connect } from "react-redux";
import { Component } from "react";
import history from "../../../config/history.config";

/**
 * initial route
 * @param {*} routes
 */
class InitRouteComponent extends Component {
  /**
   * constructor
   * @param {Object} props
   */
  constructor(props) {
    super(props);
  }

  /**
   * initRoute
   * @param {} routes
   */
  initRoute = routes => {
    var result = null;
    // generate route
    result = routes.map((route, index) => {
      if (route.isAuthenticated) {
        return (
          <PrivateRoute
            key={index}
            path={route.path}
            exact={route.exact}
            component={route.main}
            isAuthenticated={this.props.userStore.isAuthenticated}
            hasAnyAuthorities={route.hasAnyAuthorities}
            isInitial={route.isInitial}
          />
        );
      }
      return (
        <Route
          key={index}
          path={route.path}
          exact={route.exact}
          component={route.main}
        />
      );
    });
    return (
      <div>
        <Switch>{result}</Switch>
      </div>
    );
  };

  /**
   * render
   */
  render() {
    const { routes } = this.props;
    return <Router history={history}>{this.initRoute(routes)}</Router>;
  }
}

/**
 * map state to props of component
 * @param {Object} state
 * @return {Object} props
 */
const mapStateToProps = state => {
  return {
    userStore: state.userReducer
  };
};

export default connect(mapStateToProps, null)(InitRouteComponent);
