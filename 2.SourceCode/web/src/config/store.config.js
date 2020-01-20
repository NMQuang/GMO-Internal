import thunk from "redux-thunk";
import { createStore, applyMiddleware, compose } from "redux";
import { rootReducer } from "../shared/reducers";
import { checkTokenExpiration } from "../shared/middleware/token.middleware.js";

// define composeEnhancer
const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
// define a store for app
const store = createStore(
  rootReducer,
  //   composeEnhancer(applyMiddleware(thunk))
  composeEnhancer(applyMiddleware(checkTokenExpiration, thunk))
);

export default store;
