import * as React from "react"
import { Link } from 'react-router-dom';
import { Button } from "@/components/ui/button"
import {
  Card,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

export default function Login() {
    return (

        <Card className="w-[350px]">
        <CardHeader>
          <CardTitle>Prijava</CardTitle>
          <CardDescription>Prijavi se sa svojim Google profilom.</CardDescription>
        </CardHeader>
        
        <CardFooter className="flex justify-between">
        <Link to="/Home">
          <Button variant="outline">Natrag</Button>
          </Link>
            <Link > 
           <Button onClick={() => location.href = `/api/oauth2/authorization/google`} >Prijavi se</Button>
          </Link>  
{/*           <Link to="/Mainpage">
          <Button>Dalje</Button>
          </Link> */}
          
        </CardFooter>
      </Card>
    )}