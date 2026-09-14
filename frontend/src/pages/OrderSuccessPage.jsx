import { useLocation, useNavigate } from "react-router-dom";
import { formatDate } from "../utils/dateFormatter";

export default function OrderSuccessPage() {

    const location = useLocation();
    const navigate = useNavigate();

    const order = location.state;

    if (!order) {
        return (
            <div style={styles.container}>
                <h2>No order data found</h2>

                <button
                    onClick={() => navigate("/")}
                    style={styles.button}
                >
                    Back to shop
                </button>
            </div>
        );
    }


    return (

        <div style={styles.container}>

            <h1>
                🎉 Order placed successfully!
            </h1>


            <h2>
                Order #{order.orderId}
            </h2>


            <p>
                Status: {order.status}
            </p>


            <p>
                Total: {order.totalPrice} PLN
            </p>


            <p>
                Created: {formatDate(order.createdAt)}
            </p>



            <h3>
                Items:
            </h3>


            {order.items.map((item, index) => (

                <div key={index}>

                    {item.productName}
                    {" × "}
                    {item.quantity}
                    {" = "}
                    {item.productPrice} PLN

                </div>

            ))}



            <button

                style={styles.button}

                onClick={() => navigate("/")}

            >
                Back to shop

            </button>


        </div>

    );
}


const styles = {

    container: {
        padding: "40px",
        textAlign: "center"
    },

    button: {
        marginTop: "20px",
        padding: "10px 15px",
        cursor: "pointer"
    }

};