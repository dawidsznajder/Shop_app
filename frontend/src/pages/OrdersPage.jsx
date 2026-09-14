import { useQuery } from "@tanstack/react-query";
import { getOrders } from "../api/orderApi";
import { formatDate } from "../utils/dateFormatter";


export default function OrdersPage() {

    const userId = 1;


    const {
        data: orders,
        isLoading,
        error
    } = useQuery({
        queryKey: ["orders", userId],
        queryFn: () => getOrders(userId),
    });



    if (isLoading) {
        return (
            <p style={{ padding: "40px" }}>
                Loading orders...
            </p>
        );
    }


    if (error) {
        return (
            <p style={{ padding: "40px" }}>
                Error loading orders
            </p>
        );
    }



    return (

        <div style={styles.container}>

            <h1 style={styles.title}>
                My Orders
            </h1>



            {orders.length === 0 ? (

                <h2>
                    No orders yet
                </h2>

            ) : (

                orders.map(order => (

                    <div
                        key={order.orderId}
                        style={styles.card}
                    >

                        <h2 style={styles.orderId}>
                            Order #{order.orderId}
                        </h2>


                        <p>
                            Status: {order.status}
                        </p>


                        <p>
                            Date: {formatDate(order.createdAt)}
                        </p>


                        <h3>
                            Items:
                        </h3>


                        {order.items.map((item, index) => (

                            <p key={index}>
                                {item.productName}
                                {" × "}
                                {item.quantity}
                            </p>

                        ))}



                        <h3>
                            Total: {order.totalPrice} PLN
                        </h3>


                    </div>

                ))

            )}


        </div>

    );
}



const styles = {

    container: {

        padding: "40px",

        fontFamily: "Arial",

        backgroundColor: "#f7f7f7",

        minHeight: "100vh"

    },


    title: {

        color: "#808080",

        marginBottom: "30px"

    },


    card: {

        backgroundColor: "white",

        padding: "20px",

        marginBottom: "20px",

        borderRadius: "10px",

        border: "1px solid #ddd"

    },

    orderId: {
            color: "#979797"
    }

};