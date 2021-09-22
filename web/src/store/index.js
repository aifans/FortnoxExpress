import { createStore }from 'redux';
import actionTypes from './actionTypes';

const initialState = {
    orders: [],
    countries: ["Japan"],
};

const reducer = (state = initialState, action) => {
    //console.log(state, action);

    switch (action.type)
    {
        case actionTypes.Place_Order:
            //console.log("state before = %o", state);
            //state.orders.concat(action.newOrder);
            //console.log("action = %o", action);
            //console.log("state after = %o", state);
            return {orders: state.orders.concat(action.newOrder), countries: state.countries};
        case actionTypes.List_Order:
            return Object.assign({}, state, { orders: action.orders });
        case actionTypes.Get_Country:
            return Object.assign(state, { countries: action.countries });
        default:
            return initialState;
    }
}

const store = createStore(reducer);

export default store;
