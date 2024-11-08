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

export default function Signup() {
    return (

        <Card className="w-[350px]">
        <CardHeader>
          <CardTitle>Signup</CardTitle>
          <CardDescription>Sign into your account.</CardDescription>
        </CardHeader>
        <CardContent>
          <form>
            <div className="grid w-full items-center gap-4">
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="name">User Name</Label>
                <Input id="name" placeholder="User Name" />
              </div>
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="password">Password</Label>
                <Input id="name" placeholder="Password" />
               
              </div>
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="password confirm">Confirm Password</Label>
                <Input id="name" placeholder="Confirm Password" />
               
              </div>
              <Select>
                <SelectTrigger id="framework">
                  <SelectValue placeholder="Role" />
                </SelectTrigger>
                <SelectContent position="popper">
                  <SelectItem value="player">Player</SelectItem>
                  <SelectItem value="owner">Owner</SelectItem>
               
                </SelectContent>
              </Select>
            </div>
          </form>
        </CardContent>
        <CardFooter className="flex justify-between">
        <Link to="/Open">
          <Button variant="outline">Back</Button>
          </Link>

          <Link to="/mainpage">
          <Button>Signup</Button>
          </Link>
        </CardFooter>
      </Card>
    )}

    