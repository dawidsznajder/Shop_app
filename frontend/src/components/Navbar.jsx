import { NavLink } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getCart } from "../api/cartApi";


export default function Navbar() {

    const cartId = 1;


    const { data: cart } = useQuery({
        queryKey: ["cart", cartId],
        queryFn: () => getCart(cartId),
    });



    const cartCount = cart?.items.reduce(
        (sum, item) => sum + item.quantity,
        0
    ) ?? 0;



    return (

        <nav style={styles.nav}>


            <div style={styles.logo}>
                🛍️ Shop App
            </div>



            <div style={styles.links}>


                <NavLink
                    to="/"
                    style={styles.link}
                >
                    Products
                </NavLink>



                <NavLink
                    to="/cart"
                    style={styles.link}
                >

                    🛒 Cart

                    <span style={styles.badge}>
                        {cartCount}
                    </span>

                </NavLink>



                <NavLink
                    to="/orders"
                    style={styles.link}
                >
                    📦 Orders
                </NavLink>



            </div>


        </nav>

    );
}



const styles = {


    nav: {

        display: "flex",

        justifyContent: "space-between",

        alignItems: "center",

        padding: "20px 40px",

        backgroundColor: "#222"

    },


    logo: {

        color: "white",

        fontSize: "22px",

        fontWeight: "bold"

    },


    links: {

        display: "flex",

        gap: "25px",

        alignItems: "center"

    },


    link: {

        color: "white",

        textDecoration: "none",

        fontSize: "18px",

        fontWeight: "bold"

    },


    badge: {

        marginLeft: "8px",

        backgroundColor: "red",

        color: "white",

        borderRadius: "50%",

        padding: "3px 8px",

        fontSize: "14px"

    }

};