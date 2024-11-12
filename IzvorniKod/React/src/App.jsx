import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Open from './pages/Open';
import Login from './pages/Login';
import Signup from './pages/Signup';
import MainPage from './pages/MainPage';

import Layout from './components/layout';

const navigation = [

  { name: 'Tereni', href: '#', current: true },
  { name: 'Turniri', href: '#', current: false },
  { name: 'Kalendar', href: '#', current: false },
]

function App() {

  return (
    <>
      <Layout navigation={navigation} />

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