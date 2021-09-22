import {useSelector} from "react-redux";

import ListRow from "./ListRow";

import styles from './styles/BoxesTable.module.css';

const OrderList = () => {

    const orders = useSelector(state => state.orders);
    //console.log(orders);
    const orderListItems = orders.map(order =>

        <ListRow
            key={order.orderId}
            nameRecv={order.nameRecv}
            weightBox={order.weightBox}
            colorBox={order.colorBox}
            costShipment={order.costShipment}
        />
    );

    const totalCost = orders.reduce((accumulator, order) => {
        return accumulator + order.costShipment;
    }, 0).toFixed(2);

    const totalWeight = orders.reduce((accumulator, order) => {
        return accumulator + order.weightBox
    }, 0).toFixed(2);

    return (
        <div className={styles['boxes-table-wrapper']}>
            <table className={styles['boxes-table-wrapper']}>
                <thead>
                    <tr>
                        <th>Receiver Name</th>
                        <th>Weight (KG)</th>
                        <th>Box Colour</th>
                        <th>Shipment Cost</th>
                    </tr>
                </thead>
                <tbody>
                    {orderListItems}
                </tbody>

                <tfoot align={styles['tfoot']}>
                    <tr>
                        <th>Total Weight</th>
                        <th>{totalWeight} KG</th>
                        <th>Total Cost</th>
                        <th>{totalCost} SEK</th>
                    </tr>
                </tfoot>
            </table>

        </div>
    );
        
};

export default OrderList;
