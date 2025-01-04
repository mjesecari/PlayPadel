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

  /* useEffect(() => {
    if (userInfo?.email) {
      fetch(`/api/korisnik/igrac/${encodeURIComponent(userInfo.email)}`, {
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
        console.log(userInfo);
    }
  }, [userInfo]); */

  if (!userInfo) {
    return <p>Loading...</p>;
  }

  return (
    <>
      {userInfo.tip === "igrač" && (
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
                <p>Ime: {userInfo.imeIgrac}</p>
                <br></br>
                <p>Prezime: {userInfo.prezimeIgrac}</p>
                <br></br>
                  <p>Email: {userInfo.email}</p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona: {userInfo.brojTel}</p>
                </CardFooter>
              </Card>
              <br />
              <br />
              <DrawerClose>Zatvori</DrawerClose>
            </DrawerFooter>
          </DrawerContent>
        </Drawer>
      )}

      {userInfo.tip === "vlasnik" && (
        <Drawer>
          <DrawerTrigger>Owner Info</DrawerTrigger>
          <DrawerContent>
            <DrawerHeader>
              <DrawerTitle>Podatci o vlasniku</DrawerTitle>
            </DrawerHeader>
            <DrawerFooter>
              <Card>
                <CardHeader>
                  <CardTitle>{userInfo.nazivVlasnik}</CardTitle>
                  <CardDescription>Vlasnik</CardDescription>
                </CardHeader>
                <CardContent>
                  <p>Email: {userInfo.email}</p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona: {userInfo.brojTel}</p>
                  <br></br>

                  <p>Lokacija: {userInfo.lokacija}</p>
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
