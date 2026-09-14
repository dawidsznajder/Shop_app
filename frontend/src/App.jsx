import { Routes, Route, useLocation } from "react-router-dom";
import ProductsPage from "./pages/ProductsPage";
import CartPage from "./pages/CartPage";
import Navbar from "./components/Navbar";
import OrdersPage from "./pages/OrdersPage";
import OrderSuccessPage from "./pages/OrderSuccessPage";

export default function App() {

    const location = useLocation();
    const hideNavbar = location.pathname === "/order-success";

    return (
        <>
            {!hideNavbar && <Navbar />}

            <Routes>
                <Route path="/" element={<ProductsPage />} />
                <Route path="/cart" element={<CartPage />} />
                <Route path="/orders" element={<OrdersPage />} />
                <Route path="/order-success" element={<OrderSuccessPage />} />
            </Routes>
        </>
    );
}