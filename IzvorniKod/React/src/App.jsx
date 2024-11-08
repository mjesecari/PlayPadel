/* 
import { Button } from './components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Open from './pages/Open';
import Login from './pages/Login';
import Signup from './pages/Signup';

function App() {
  return (
<>
      <Card>

        <CardHeader>
          <CardTitle>PlayPadel</CardTitle>
        </CardHeader>

        <CardContent class="font-mono">
          <Button class="m-5 text-zinc-50">Log in</Button>
          <Button class="m-5 text-zinc-50">Sign up</Button>
        </CardContent>
        
      </Card>

    <BrowserRouter>
      <Routes>
        <Route index element={<Open />} />
        <Route path="/Open" element={<Open/>} />
        <Route path="/Login" element={<Login />} />
        <Route path="/Signup" element={<Signup />} />
      </Routes>
    </BrowserRouter>
    </>
  );
}

export default App;
 */



import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Open from './pages/Open';
import Login from './pages/Login';
import Signup from './pages/Signup';
import MainPage from './pages/MainPage';

import { SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar"
import { AppSidebar } from "@/components/app-sidebar"

function App() {

  return (
    <>
      <SidebarProvider>
      <AppSidebar />
        <main>
          <SidebarTrigger />
          
        </main>
      </SidebarProvider>

       <BrowserRouter>
      <Routes>
        <Route index element={<Open />} />
        <Route path="/Open" element={<Open/>} />
        <Route path="/Login" element={<Login />} />
        <Route path="/Signup" element={<Signup />} />
        <Route path="/MainPage" element={<MainPage />} />
      
      </Routes>
    </BrowserRouter>
     

    </>
  )
}

export default App