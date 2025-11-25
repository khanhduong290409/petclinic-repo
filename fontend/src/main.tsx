// main.tsx
import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App";
import Home from "./pages/Home";
import OwnerCreate from "./pages/OwnerCreate";
import PetCreate from "./pages/PetCreate";
import AppointmentCreate from "./pages/AppointmentCreate";
import ProductListPage from "./pages/Products/ProductListPage";
import ProductDetailPage from "./pages/Products/ProductDetailPage";
import "./index.css";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />, // layout chung
    children: [
      { index: true, element: <Home /> },
      { path: "owners/new", element: <OwnerCreate /> },
      { path: "pets/new", element: <PetCreate /> },
      { path: "appointments/new", element: <AppointmentCreate /> },

      // ✅ routes cho sản phẩm
      { path: "products", element: <ProductListPage /> },
      { path: "products/:slug", element: <ProductDetailPage /> },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
