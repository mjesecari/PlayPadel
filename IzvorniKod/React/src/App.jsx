import { useState } from 'react'
import './App.css'
import { Button } from './components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

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

    </>
  )
}

export default App
