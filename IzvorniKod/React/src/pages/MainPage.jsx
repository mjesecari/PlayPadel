import '../Open.css'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer"
import {Button} from "@/components/ui/button"

export default function Open() {

  return (
    <>
      <Drawer>
  <DrawerTrigger>User Info</DrawerTrigger>
  <DrawerContent>
    <DrawerHeader>
      <DrawerTitle>Podatci o korisniku</DrawerTitle>
    </DrawerHeader>

    <DrawerFooter>
    <Card>
  <CardHeader>
    <CardTitle>Ime Prezime</CardTitle>
    <CardDescription>Role</CardDescription>
  </CardHeader>
  <CardContent>
    <p>Email:</p>
  </CardContent>
  <CardFooter>
    <p>Broj telefona:</p>
  </CardFooter>
</Card>
<br></br>
<br></br>
      <DrawerClose>
      Zatvori
      </DrawerClose>
    </DrawerFooter>
  </DrawerContent>
</Drawer>

    </>
  )
}
