import {useDispatch, useSelector} from 'react-redux';
import { useState } from 'react';

import useInputValidation from '../../hooks/use-input';
import actionTypes from '../../store/actionTypes';

import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import styles from './styles/AddBoxForm.module.css'
import API_BASE_URL from '../../store/baseURL';
import Loader from '../UI/Loader';

const isNotEmptyString = str => {
    if (str != null && str.length > 0)
        return str.trim() !== '';
    else
        return false;
}

const stringNumValueIsGreaterThanZero = str => {
    return +str >= 0;
}

const isValidColor = str => {
    // Should be HEX Color of length 7.
    if (str.trim().length !== 7) {
        return false;
    }

    // If last two digits (blue digits) of HEX Color are not 00, return false.
    const blue = str.substring(5);
    return blue === '00';
}

const PlaceOrder = () => {
    const dispatch = useDispatch();
    const [isProcessingRequest, setIsProcessingRequest] = useState(false);

    const countries = useSelector(state => state.countries);
    //console.log("countries: %o", countries)

    // Name Field
    const {
        value: enteredName,
        isValid: isValidEnteredName,
        hasError: showNameFieldError,
        valueChangeHandler: nameChangeHandler,
        inputBlurHandler: nameBlurHandler,
        reset: resetName
    } = useInputValidation('', isNotEmptyString);

    // Weight Field
    const {
        value: enteredWeight,
        isValid: isValidEnteredWeight,
        hasError: showWeightFieldError,
        valueChangeHandler: weightChangeHandler,
        inputBlurHandler: weightBlurHandler,
        reset: resetWeight
    } = useInputValidation(0, stringNumValueIsGreaterThanZero);

    // Color Field
    const {
        value: enteredColor,
        isValid: isValidEnteredColor,
        hasError: showColorFieldError,
        valueChangeHandler: colorChangeHandler,
        inputBlurHandler: colorBlurHandler,
        reset: resetColor
    } = useInputValidation('', isValidColor);

    // Country Field
    const {
        value: enteredCountry,
        isValid: isValidEnteredCountry,
        hasError: showCountryFieldError,
        valueChangeHandler: countryChangeHandler,
        inputBlurHandler: countryBlurHandler,
        reset: resetCountry
    } = useInputValidation('', isNotEmptyString);

    const resetForm = () => {
        resetName();
        resetWeight();
        resetColor();
        resetCountry();
    }

    const placeOrderHandler = event => {
        event.preventDefault();
        
        setIsProcessingRequest(true);

        const newOrder = {
            nameRecv: enteredName,
            weightBox: enteredWeight,
            colorBox: enteredColor,
            countryNameDest: enteredCountry
        };

        const notify_ok = () => toast.success('place order success!');
        const notify_err = () => toast.error('place order failure!');

        fetch(`${API_BASE_URL}/placeanorder`, {
            method: "POST",
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify(newOrder)
        })
        .then(response => response.json()
            .then(ret => {
                console.log(ret)
                if (ret.code === '200') {
                    notify_ok();

                    dispatch({type: actionTypes.Place_Order, newOrder: ret.result});
                    resetForm();
                } else {
                    notify_err();
                }
            }))
        .finally(() => setIsProcessingRequest(false))
    }

    const buttonDisabled = 
        !isValidEnteredName || 
        !isValidEnteredWeight || 
        !isValidEnteredColor || 
        !isValidEnteredCountry;

    const nameFieldStyles = showNameFieldError ? styles.invalid : '';
    const weightFieldStyles = showWeightFieldError ? styles.invalid : '';
    const colorFieldStyles = showColorFieldError ? styles.invalid : '';
    const countryFieldStyles = showCountryFieldError ? styles.invalid : '';

    return (
            <div className={styles.container}>
                <div className={styles['form-wrapper']}>
                    <form className={styles.form} onSubmit={placeOrderHandler}>
                        <div className={styles['form-field']}>
                            <label htmlFor="name">Name</label>
                            <input
                                id="name"
                                className={nameFieldStyles} 
                                type="text" 
                                value={enteredName} 
                                onChange={nameChangeHandler} 
                                onBlur={nameBlurHandler} 
                            />
                            {showNameFieldError && <p className={styles['error-text']}>Name is required</p>}
                        </div>
                        <div className={styles['form-field']}>
                            <label htmlFor="weight">Weight</label>
                            <input
                                id="weight"
                                className={weightFieldStyles} 
                                type="number" 
                                placeholder='Weight' 
                                value={enteredWeight} 
                                onChange={weightChangeHandler} 
                                onBlur={weightBlurHandler} 
                            />
                            {showWeightFieldError && <p className={styles['error-text']}>Weight must be non-negative</p>}
                        </div>
                        <div className={styles['form-field']}>
                            <label htmlFor="color">Box Color</label>
                            <input 
                                id="color"
                                className={colorFieldStyles} 
                                type="color" 
                                placeholder='Box colour' 
                                value={enteredColor} 
                                onChange={colorChangeHandler} 
                                onBlur={colorBlurHandler} 
                            />
                            {showColorFieldError && <p className={styles['error-text']}>Color is required and must contain no shade of blue.</p>}
                        </div>
                        <div className={styles['form-field']}>
                            <label htmlFor="country">Country</label>
                            <select
                                id="country"
                                className={countryFieldStyles} 
                                value={enteredCountry} 
                                onChange={countryChangeHandler} 
                                onBlur={countryBlurHandler}
                            >
                                <option key="DEFAULT" value="DEFAULT" >Choose a country ...</option>
                                {countries.map(country => <option key={country.id} value={country.countryDest}>{country.countryDest}</option>)}

                            </select>
                            {showCountryFieldError && <p className={styles['error-text']}>Country is required</p>}
                        </div>
                        <button type='submit' disabled={buttonDisabled}>{isProcessingRequest ? <Loader size={15} color="#CCC" /> : 'Place an Order' }</button>
                    </form>
                </div>
            </div>
    );
};

export default PlaceOrder;
