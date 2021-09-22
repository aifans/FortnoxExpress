import { HashRouter as Router, Switch, Route, Redirect } from 'react-router-dom'
import { Fragment, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useState } from 'react';

import NavbarView from "./views/NavbarView";
import PlaceOrderView from './views/PlaceOrderView';
import OrderListView from './views/OrderListView';
import Loader from './components/UI/Loader';
import { ToastContainer } from 'react-toastify';

import actionTypes from './store/actionTypes';
import endpoints from './routing/endpoints';
import API_BASE_URL from './store/baseURL';
import './App.css';

function App() {
  const dispatch = useDispatch();
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    console.log("fetch all contry names");

    setIsLoading(true);

    fetch(`${API_BASE_URL}/getallcountrynames`)
        .then(response => response.json()
            .then(ret => {
              console.log("fetch all contry names excuted: %o", ret.result);
              dispatch({ type: actionTypes.Get_Country, countries: ret.result });
            }))

        .finally(() => setIsLoading(false))
  }, [dispatch])

  useEffect(() => {
    console.log("fetch all orders");

    setIsLoading(true);

    fetch(`${API_BASE_URL}/listorders`)
        .then(
            response => response.json(),
        )
        .then(ret => {
            console.log("fetch all orders excuted: %o", ret.result);
            dispatch({type: actionTypes.List_Order, orders: ret.result});
        })

        .finally(() => setIsLoading(false))
  }, [dispatch])

  return (
    <Fragment>
      {
        isLoading ?
        <div className="loader-container">
          <Loader size={30} color="#CCC" />
        </div> 
        :
        <Router>

          <div>
            <NavbarView />
          </div>

          <ToastContainer
              position="top-center"
              autoClose={1800}
              hideProgressBar={false}
              newestOnTop={false}
              closeOnClick
              rtl={false}
              pauseOnFocusLoss
              draggable
              pauseOnHover
          />

          <Switch>
            <Route exact path='/'>
              <Redirect to={endpoints.Place_Order} />
            </Route>
            <Route path={endpoints.Place_Order}>
              <PlaceOrderView />
            </Route>
            <Route path={endpoints.List_Order}>
              <OrderListView />
            </Route>
          </Switch>
        </Router>
      }
    </Fragment>
  );
}

export default App;
