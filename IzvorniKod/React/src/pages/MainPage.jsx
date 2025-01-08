import React, { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { 
  Drawer, DrawerClose, DrawerContent, DrawerFooter, DrawerHeader, DrawerTitle, DrawerTrigger 
} from "@/components/ui/drawer";
import { 
  Card, CardHeader, CardContent, CardFooter, CardTitle, CardDescription 
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
export default function MainPage({ userInfo }) {
  const [userDetails, setUserDetails] = useState(null);
  const [isEditing, setIsEditing] = useState(false); // Tracks editing state
  const [form, setForm] = useState({});

  

  const handleEditToggle = () => {
    setIsEditing(!isEditing); // Toggle editing state
  };

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setForm((prevForm) => ({
      ...prevForm,
      [id]: value,
    }));
  };

  const handleSaveChanges = () => {
    const payload = {
      ...userInfo,  // Keep original data if not edited
      ...form,      // Include edited data
    };

    let url = "";
    if (userInfo.tip === "igrač") {
      url = "api/igrac/" + userInfo.id;
    } else if (userInfo.tip === "vlasnik") {
      url = "api/korisnik/vlasnik";
    }

    const options = {
      method: "PUT", // Use PUT for updating existing data
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    };

    fetch(url, options)
    .then((res) => {
      if (res.ok) {
        console.log("Changes saved successfully.");
        alert("Podaci su uspješno spremljeni.");
        setIsEditing(false); // Toggle back to view mode
      } else {
        return res.json().then((error) => {
          alert(`Error: ${error.message || "Failed to save changes"}`);
        });
      }
    })
    .catch((err) => {
      console.error("Error during saving changes:", err);
      alert("Došlo je do greške. Pokušajte ponovo.");
    });
};

useEffect(() => {
  if (userInfo) {
    // Pre-fill the form with existing data if available
    setForm({
      imeIgrac: userInfo.imeIgrac ,
      prezimeIgrac: userInfo.prezimeIgrac ,
      brojTel: userInfo.brojTel,
      nazivVlasnik: userInfo.nazivVlasnik ,
      lokacija: userInfo.lokacija ,
    });
  }
}, [userInfo]);

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
            {!isEditing ? (
              <Card>
                <CardHeader> 
                  <CardDescription>Igrač</CardDescription>
                </CardHeader>
                <CardContent>
                <p>Id: {userInfo.id} </p>
                <br></br>
                <p>Ime: {userInfo.imeIgrac} </p>
                <br></br>
                <p>Prezime:  {userInfo.prezimeIgrac} </p>
                <br></br>
                  <p>Email:  {userInfo.email} </p>
                </CardContent>
                <CardFooter>
                  <p>Broj telefona: {userInfo.brojTel} </p>
                </CardFooter>
              </Card>  ) : (

                <Card>
                <CardHeader> 
                  <CardDescription>Igrač</CardDescription>
                </CardHeader>
                <CardContent>
                <p>Ime </p>
                <Input id="imeIgrac" defaultValue={userInfo.imeIgrac} onChange={handleInputChange}/>
                <br></br>
                <p>Prezime </p>
                 <Input id="prezimeIgrac"  defaultValue={userInfo.prezimeIgrac} onChange={handleInputChange}/>
                <br></br>
                <p>Broj telefona </p>
                 <Input id="brojTel" defaultValue={userInfo.brojTel} onChange={handleInputChange}/>
                </CardContent>
                <CardFooter>
                <Button
              style={{ fontSize: "1.1rem" }} onClick={handleSaveChanges}
              
            >
              Spremi promjene
            </Button>
                </CardFooter>
              </Card>)}
              <br />
              <br />
              <Button
              style={{ fontSize: "1.1rem" }}
              onClick={handleEditToggle}
            >
              Uredi
            </Button>
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
            {!isEditing ? (
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
              </Card>  ) : (
                 <Card>
                 <CardHeader> 
                   <CardDescription>Igrač</CardDescription>
                 </CardHeader>
                 <CardContent>
                 <p>Naziv </p>
                  <Input id="nazivVlasnik" defaultValue={userInfo.nazivVlasnik} onChange={handleInputChange}/>
                 <br></br>
                 <p>Broj telefona </p>
                  <Input id="brojTel" defaultValue={userInfo.brojTel} onChange={handleInputChange}/>
                 <br></br>
                 <p>Lokacijia </p>
                   <Input id="lokacija" defaultValue={userInfo.lokacija} onChange={handleInputChange}/>
                 </CardContent>
                 <CardFooter>
                <Button style={{ fontSize: "1.1rem" }} onClick={handleSaveChanges}>
               Spremi promjene
                </Button>
                 </CardFooter>
               </Card>)}
              <br />
              <br />
              <Button
              style={{ fontSize: "1.1rem" }}
              onClick={handleEditToggle}
            >
              Uredi
            </Button>
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