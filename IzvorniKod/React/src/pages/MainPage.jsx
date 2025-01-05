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

 /*  if (!userInfo) {
    return <p>Loading...</p>;
  } */

  return (
    <>
    <nav style={styles.navbar}>
  <ul style={styles.navList}>
   
    <li style={styles.navItem}>
      {userInfo.tip === "igrač" && (
        <Drawer>
          <DrawerTrigger>Podatci o igraču</DrawerTrigger>
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
          <DrawerTrigger>Podatci o vlasniku</DrawerTrigger>
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
       </li>
       <li style={styles.navItem}></li>
    <li style={styles.navItem}></li>
    <li style={styles.navItem}></li>
  </ul>
</nav>
    </>
  );
}


const styles = {
  navbar: {
    backgroundColor: '#333',
    padding: '10px 0',
    position: 'fixed', // Fixed at the top
    top: '0',
    left: '0',
    width: '100%',
    zIndex: '1000', // Ensures it stays on top
  },
  navList: {
    display: 'flex',
    justifyContent: 'flex-start', // Aligns items to the left
    listStyleType: 'none',
    margin: 0,
    padding: 0,
  },
  navItem: {
    margin: '0 20px',
  },
  link: {
    color: 'white',
    textDecoration: 'none',
    fontSize: '18px',
    transition: 'color 0.3s',
  },
};