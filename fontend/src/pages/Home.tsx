import Navbar from "../components/Navbar";
import Hero from "../components/Hero";
import About from "../components/About";
import Services from "../components/Services";
import Products from "../components/Products";
import AppointmentCTA from "../components/AppointmentCTA";
import Testimonials from "../components/Testimonials";
import Footer from "../components/Footer";

export default function Home() {
  return (
    <div className="min-h-screen">
      <Navbar />
      <Hero />
      <main>
        <About />
        <Services />
        <Products />
        <AppointmentCTA />
        <Testimonials />
      </main>
      <Footer />
    </div>
  );
}
