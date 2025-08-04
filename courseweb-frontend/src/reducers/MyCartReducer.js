import cookie from 'react-cookies';

const MyCartReducer = (current, action) => {
    if (action.type === 'update') {
        const cartItems = action.payload;
        if (Array.isArray(cartItems)) {
            return cartItems.length; 
        }

        return 0;
    }
    return current;
}

export default MyCartReducer;