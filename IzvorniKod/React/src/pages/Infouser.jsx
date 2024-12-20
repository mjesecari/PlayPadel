//ovo vrijedi za igrača
import * as React from "react"
import { Link } from 'react-router-dom';
import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"

export default function Infouser() {
    return (

      <Card>
      <CardHeader>
        <CardTitle>Igrač</CardTitle>
        <CardDescription>
         Potrebne su dodatne infomacije za registraciju
        </CardDescription>
      </CardHeader>
      <CardContent className="space-y-2">
        <div className="space-y-1">
          <Label htmlFor="ime">Ime</Label>
          <Input id="ime" placeholder="Unesite ime i prezime" />
        </div>
        <div className="space-y-1">
          <Label htmlFor="brojtelefona">Broj telefona</Label>
          <Input id="brojtelefona" placeholder="Unesite broj telefona" />
        </div>
      </CardContent>
     <CardFooter className="flex justify-between">
         <Link to="/signup">
                  <Button variant="outline">Natrag</Button>
                  </Link>
                  
                  <Link to="/Mainpage">
                  <Button>Prijavi se</Button>
                  </Link>
      </CardFooter>
    </Card>
    )}