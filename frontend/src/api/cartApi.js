export async function addToCart(cartId, productId, quantity = 1) {
    const response = await fetch(`http://localhost:8080/carts/${cartId}/items`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            productId,
            quantity
        })
    });

    if (!response.ok) {
        throw new Error("Failed to add to cart");
    }

    return response.json();
}

export async function getCart(cartId) {
    const response = await fetch(`http://localhost:8080/carts/${cartId}`);

    if (!response.ok) {
        throw new Error("Failed to fetch cart");
    }

    return response.json();
}

export async function updateCartItem(cartId, productId, quantity) {
    const response = await fetch(
        `http://localhost:8080/carts/${cartId}/items/${productId}`,
        {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ quantity })
        }
    );

    if (!response.ok) {
        throw new Error("Failed to update cart item");
    }
}

export async function removeCartItem(cartId, productId) {
    const response = await fetch(
        `http://localhost:8080/carts/${cartId}/items/${productId}`,
        {
            method: "DELETE"
        }
    );

    if (!response.ok) {
        throw new Error("Failed to remove item");
    }
}