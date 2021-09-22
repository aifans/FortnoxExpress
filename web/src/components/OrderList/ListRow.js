import styles from './styles/BoxesTable.module.css';

const ListRow = props => {
    return (    
        <tr>
            <td>{props.nameRecv}</td>
            <td className={styles['is-number-cell']}>{props.weightBox}</td>
            <td style={{ backgroundColor: props.colorBox }}></td>
            <td className={styles['is-number-cell']}>{props.costShipment.toFixed(2)} SEK</td>
        </tr>
    );
}

export default ListRow;
