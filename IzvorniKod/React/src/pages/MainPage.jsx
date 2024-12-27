//nazivi atributa
//provjera rute
import React, { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { 
  Drawer, DrawerClose, DrawerContent, DrawerFooter, DrawerHeader, DrawerTitle, DrawerTrigger 
} from "@/components/ui/drawer";
import { 
  Card, CardHeader, CardContent, CardFooter, CardTitle, CardDescription 
} from "@/components/ui/card";

export default function MainPage({ userInfo }) {
  const [userDetails, setUserDetails] = useState(null);

  useEffect(() => {
    if (userInfo?.email) {
      fetch(`/api/user/?email=${encodeURIComponent(userInfo.email)}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Failed to fetch user details");
          }
          return response.json();
        })
        .then((data) => {
          setUserDetails(data);
        })
        .catch((error) => {
          console.error("Error fetching user details:", error);
        });
    }
  }, [userInfo]);

  if (!userDetails) {
    return <p>Loading...</p>;
  }

  return (
    <>
      {userDetails.role === "igrač" && (
        <Drawer>
          <DrawerTrigger>Player Info</DrawerTrigger>
          <DrawerContent>
            <DrawerHeader>
              <DrawerTitle>Podatci o igraču</DrawerTitle>
            </DrawerHeader>
            <DrawerFooter>
              <Card>
                <CardHeader>
                  
                  <CardDescription>Igrač</CardDescription>

                 
                </CardHeader>
                <CardContent>
                <p>Ime: {userDetails.imeIgrac}</p>
                <br></br>
                <p>Prezime: {userDetails.prezimeIgrac}</p>
                <br></br>
                  <p>Email: {userDetails.email}</p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona: {userDetails.brojTel}</p>
                </CardFooter>
              </Card>
              <br />
              <br />
              <DrawerClose>Zatvori</DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
      )}

      {userDetails.role === "vlasnik" && (
        <Drawer>
          <DrawerTrigger>Owner Info</DrawerTrigger>
          <DrawerContent>
            <DrawerHeader>
              <DrawerTitle>Podatci o vlasniku</DrawerTitle>
            </DrawerHeader>
            <DrawerFooter>
              <Card>
                <CardHeader>
                  <CardTitle>{userDetails.nazivVlasnik}</CardTitle>
                  <CardDescription>Vlasnik</CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Email: {userDetails.email}</p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona: {userDetails.brojTel}</p>
                  <br></br>

                  <p>Lokacija: {userDetails.lokacija}</p>
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
