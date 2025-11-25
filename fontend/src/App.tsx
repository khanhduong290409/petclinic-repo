// App.tsx
import { Outlet, Link } from "react-router-dom";

function App() {
  return (
    <div>
      {/* Ví dụ layout: header + menu */}
      <header className="p-4 border-b mb-4 flex gap-4">
        <Link to="/">Home</Link>
        <Link to="/owners/new">Tạo Owner</Link>
        <Link to="/pets/new">Tạo Pet</Link>
        <Link to="/appointments/new">Tạo Appointment</Link>
        <Link to="/products">Sản phẩm</Link>
      </header>

      {/* Nơi render các route con */}
      <main className="p-4">
        <Outlet />
      </main>
    </div>
  );
}

export default App;
