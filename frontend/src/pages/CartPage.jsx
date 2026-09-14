import { useQuery, useQueryClient } from "@tanstack/react-query";
import { getCart, updateCartItem, removeCartItem } from "../api/cartApi";
import { checkout } from "../api/orderApi";
import { useNavigate } from "react-router-dom";
import { productImages } from "../assets/productImages";


export default function CartPage() {

    const cartId = 1;

    const navigate = useNavigate();

    const queryClient = useQueryClient();

    const { data: cart, isLoading, error } = useQuery({
        queryKey: ["cart", cartId],
        queryFn: () => getCart(cartId),
    });


    const handleUpdateQuantity = async (productId, newQuantity) => {

        if (newQuantity < 1) return;

        try {

            await updateCartItem(
                cartId,
                productId,
                newQuantity
            );


            queryClient.invalidateQueries({
                queryKey: ["cart", cartId]
            });


        } catch (err) {
            console.error("Update failed:", err);
        }
    };


    const handleRemoveItem = async (productId) => {

        try {

            await removeCartItem(
                cartId,
                productId
            );


            queryClient.invalidateQueries({
                queryKey: ["cart", cartId]
            });


        } catch (err) {
            console.error("Remove failed:", err);
        }
    };


    const handleCheckout = async () => {

        try {

            const result = await checkout(cartId);

            navigate("/order-success", {
             state: result
            });


            queryClient.invalidateQueries({
                queryKey: ["cart", cartId]
            });


        } catch (err) {
            console.error("Checkout error:", err);
        }
    };


    if (isLoading) {
        return (
            <p style={{ padding: "40px" }}>
                Loading cart...
            </p>
        );
    }


    if (error) {
        return (
            <p style={{ padding: "40px" }}>
                Error loading cart
            </p>
        );
    }

    return (

    <div style={styles.container}>

        <h1 style={styles.title}>
            Shopping Cart
        </h1>

        {cart.items.length === 0 ? (

        <div style={styles.emptyCart}>

        <div style={styles.cartIcon}>
            🛒
        </div>

        <h2 style={styles.emptyTitle}>
            Your cart is empty
        </h2>

        <button
            style={styles.continueButton}
            onClick={() => navigate("/")}
        >
            Continue shopping
        </button>

    </div>

        ) : (

            cart.items.map(item => (

                <div
                    key={item.productId}
                    style={styles.item}
                >

                    <img
                        src={productImages[item.imageName]}
                        alt={item.productName}
                        style={styles.cartImage}
                    />

                    <div style={styles.itemContent}>

                        <h3>{item.productName}</h3>

                        <p>
                            Price: {item.price} PLN
                        </p>

                        <div
                            style={{
                                display: "flex",
                                alignItems: "center",
                                gap: "10px",
                                margin: "15px 0"
                            }}
                        >

                            <button
                                onClick={() =>
                                    handleUpdateQuantity(
                                        item.productId,
                                        item.quantity - 1
                                    )
                                }
                            >
                                -
                            </button>

                            <span>{item.quantity}</span>

                            <button
                                onClick={() =>
                                    handleUpdateQuantity(
                                        item.productId,
                                        item.quantity + 1
                                    )
                                }
                            >
                                +
                            </button>

                        </div>

                        <p>
                            Line total: {item.lineTotal} PLN
                        </p>

                        <button
                            style={styles.removeButton}
                            onClick={() =>
                                handleRemoveItem(item.productId)
                            }
                        >
                            Remove
                        </button>

                    </div>

                </div>

            ))

        )}

        {cart.items.length > 0 && (

            <>
                <hr />

                <h2 style={styles.total}>
                    Total: {cart.totalPrice} PLN
                </h2>

                <button
                    onClick={handleCheckout}
                    style={styles.checkoutButton}
                >
                    Checkout
                </button>
            </>

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

        marginBottom: "30px",

        color: "#808080"

    },


    item: {
    display: "flex",
    gap: "30px",
    alignItems: "center",
    backgroundColor: "white",
    padding: "20px",
    marginBottom: "20px",
    borderRadius: "10px",
    border: "1px solid #ddd"
},

itemContent: {
    flex: 1
},


    total: {

        marginTop: "20px",

        color: "#2c7"

    },

    cartImage: {
    width: "140px",
    height: "140px",
    objectFit: "contain",
    flexShrink: 0
},

removeButton: {
    marginTop: "15px",
    padding: "8px 14px",
    border: "none",
    borderRadius: "6px",
    backgroundColor: "#dc3545",
    color: "white",
    cursor: "pointer"
},

checkoutButton: {
    marginTop: "20px",
    padding: "10px 15px",
    backgroundColor: "#198754",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer"
},

emptyCart: {
    textAlign: "center",
    marginTop: "60px",
},

emptyTitle: {
    margin: "0",
    color: "#979797",
    fontSize: "28px"
},

cartIcon: {
    fontSize: "60px",
    marginBottom: "20px"
},

continueButton: {
    marginTop: "20px",
    padding: "12px 20px",
    backgroundColor: "#007bff",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer"
},
};