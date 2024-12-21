import React, { useState, useEffect } from "react";
import "../Open.css"; // Ensure this is available for styling
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerFooter,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from "@/components/ui/drawer";
import { Button } from "@/components/ui/button";

export default function Open() {
  const [role, setRole] = useState("");

  // Fetch role from the backend
  useEffect(() => {
    fetch("/api/getRole", { method: "GET" }) // Adjust the endpoint as needed
      .then((response) => response.json())
      .then((data) => {
        console.log("Fetched role:", data.role);
        setRole(data.role); // Assuming the backend returns { role: "igrač" } or { role: "vlasnik" }
      })
      .catch((error) => console.error("Error fetching role:", error));
  }, []);

  // Render based on the role
  return (
    <>
      {role === "igrač" && (
        <Drawer>
          <DrawerTrigger>Player Info</DrawerTrigger>
          <DrawerContent>
            <DrawerHeader>
              <DrawerTitle>Podatci o igraču</DrawerTitle>
            </DrawerHeader>

            <DrawerFooter>
              <Card>
                <CardHeader>
                  <CardTitle>Ime Prezime</CardTitle>
                  <CardDescription>Igrač</CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Email:</p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona:</p>
                </CardFooter>
              </Card>
              <br />
              <br />
              <DrawerClose>Zatvori</DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
      )}

      {role === "vlasnik" && (
        <Drawer>
          <DrawerTrigger>Owner Info</DrawerTrigger>
          <DrawerContent>
            <DrawerHeader>
              <DrawerTitle>Podatci o vlasniku</DrawerTitle>
            </DrawerHeader>

            <DrawerFooter>
              <Card>
                <CardHeader>
                  <CardTitle>Naziv kluba</CardTitle>
                  <CardDescription>Vlasnik</CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Email:</p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona:</p>
                </CardFooter>
              </Card>
              <br />
              <br />
              <DrawerClose>Zatvori</DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
      )}
    </>
  );
}
