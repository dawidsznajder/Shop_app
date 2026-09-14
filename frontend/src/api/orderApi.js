export async function checkout(cartId) {
    const response = await fetch(
        `http://localhost:8080/orders/checkout/${cartId}`,
        {
            method: "POST"
        }
    );

    if (!response.ok) {
        throw new Error("Checkout failed");
    }

    return response.json();
}

export async function getOrders(userId) {

    const response = await fetch(
        `http://localhost:8080/users/${userId}/orders`
    );

    if (!response.ok) {
        throw new Error("Failed to fetch orders");
    }

    return response.json();
}