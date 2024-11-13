
import '../Open.css'
import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export default function Open() {

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
}

