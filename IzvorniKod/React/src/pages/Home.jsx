
import '../Open.css'
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import Layout from '@/components/layout';

export default function Home({userInfo}) {
  

  if (!userInfo)
  return (
    <>

      <Card>

        <CardHeader>
          <CardTitle>PlayPadel</CardTitle>
        </CardHeader>

        <CardContent className="font-mono">
        <Link to="/login">
          <Button className="m-5 text-zinc-50">Prijava</Button>
          </Link>

          <Link to="/signup">
          <Button className="m-5 text-zinc-50">Registracija</Button>
          </Link>
        </CardContent>
        
      </Card>

    </>
  )
  else{
    return (
      <>
      <Layout userInfo={userInfo}/>
      
      <Card>

        <CardHeader>
          <CardTitle>PlayPadel</CardTitle>
        </CardHeader>

        <CardContent className="font-mono">
        
          Logged in successfully :)
        </CardContent>
        
      </Card>

      </>

    )
  }
}

