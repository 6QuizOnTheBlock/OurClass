
import { Button } from "./components/ui/button";
import { Calendar } from "./components/ui/calendar";
import "./index.css";

function App() {

  return (
    <>
      <div className="flex justify-center items-center min-h-screen">
        <Button variant="zosh" size="xl">Code With Zosh</Button>
        <Calendar/>
      </div>
      <p className="read-the-docs">
       
      </p>
    </>
  )
}

export default App
