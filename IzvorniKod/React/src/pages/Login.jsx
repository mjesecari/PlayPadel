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
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

export default function Open() {
    return (

        <Card className="w-[350px]">
        <CardHeader>
          <CardTitle>Prijava</CardTitle>
          <CardDescription>Prijavi se sa svojim Google profilom.</CardDescription>
        </CardHeader>
        
        <CardFooter className="flex justify-between">
        <Link to="/Open">
          <Button variant="outline">Natrag</Button>
          </Link>
          <Link >
          <Button onClick={() => location.href = `/api/oauth2/authorization/google`} >Prijavi se</Button>
          </Link>
        </CardFooter>
      </Card>
    )}