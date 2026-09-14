import { useQuery } from "@tanstack/react-query";
import { getProducts } from "../api/productApi";
import { addToCart } from "../api/cartApi";
import { useQueryClient } from "@tanstack/react-query";
import { productImages } from "../assets/productImages";

export default function ProductsPage() {

    const queryClient = useQueryClient();

    const { data: products, isLoading, error } = useQuery({
    queryKey: ["products"],
    queryFn: getProducts,
});

    const handleAddToCart = async (productId) => {
        try {
            const cartId = 1;

            await addToCart(cartId, productId, 1);

            queryClient.invalidateQueries({
            queryKey: ["cart", cartId]
});

        alert("Added to cart!");
        } catch (err) {
            console.error(err);
            alert("Error adding to cart");
        }
};

    if (isLoading) {
    return <p style={{ padding: "40px" }}>Loading...</p>;
}

if (error) {
    return <p>Error loading products</p>;
}

    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Products</h1>
            <div style={styles.grid}>
                {products.map(product => (
                    <div key={product.id} style={styles.card}>

    <img
        src={productImages[product.imageName]}
        alt={product.name}
        style={styles.image}
    />

    <h3 style={styles.name}>
        {product.name}
    </h3>

    <p style={styles.price}>
    {product.price} PLN
</p>

                        <button
                            style={styles.button}
                            onClick={() => handleAddToCart(product.id)}
                        >
                            Add to cart
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}

const styles = {
    container: {
        padding: "40px",
        fontFamily: "Arial, sans-serif",
        backgroundColor: "#f7f7f7",
        minHeight: "100vh"
    },
    title: {
        marginBottom: "20px",
        color: "#808080",
        fontSize: "32px"
    },
    grid: {
        display: "grid",
        gridTemplateColumns: "repeat(auto-fit, minmax(220px, 1fr))",
        gap: "20px"
    },
    card: {
        border: "1px solid #ddd",
        borderRadius: "10px",
        padding: "15px",
        boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
        backgroundColor: "white"
    },
    name: {
        marginBottom: "10px"
    },
    price: {
        color: "#2c7",
        fontWeight: "bold",
        fontSize: "18px"
    },
    button: {
        marginTop: "10px",
        padding: "8px 12px",
        border: "none",
        backgroundColor: "#007bff",
        color: "white",
        borderRadius: "5px",
        cursor: "pointer"
    },
    image: {
    width: "100%",
    height: "200px",
    objectFit: "contain",
    marginBottom: "15px"
},
};