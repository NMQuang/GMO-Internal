import React, { Component } from "react";
import "react-datepicker/dist/react-datepicker.css";
import "react-widgets/dist/css/react-widgets.css";
import "./App.css";
import "react-picky/dist/picky.css";
import Header from "./shared/layouts/header/header.component";
import Footer from "./shared/layouts/footer/footer.component";
import ErrorBoundaryComponent from "./shared/errors/error-500/error-boundary";
import { routes } from "./config/route.config";
import InitRouteComponent from "./shared/common/component/route.component";
import { connect } from "react-redux";
import LoadingComponent from "./shared/common/component/loading.component";

/**
 * App Component
 */
class App extends Component {
  componentDidMount() {}
  /**
   * render
   */
  render() {
    const { loaderStore } = this.props;

    return (
      <React.Fragment>
        <Header />
        <ErrorBoundaryComponent>
          <InitRouteComponent routes={routes} />
        </ErrorBoundaryComponent>
        <Footer />
        {loaderStore.isLoading && <LoadingComponent />}
      </React.Fragment>
    );
  }
}

/**
 * map state to props of component
 * @param {Object} state
 * @return {Object} props
 */
const mapStateToProps = state => {
  return {
    loaderStore: state.loaderReducer
  };
};

export default connect(mapStateToProps, null)(App);
