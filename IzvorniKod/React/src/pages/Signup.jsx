/* import * as React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState } from "react";

export default function Signup() {
  const [selectedRole, setSelectedRole] = useState("");
  const [form, setForm] = useState({ email: "", role: "" });
  const navigate = useNavigate();

  const emailRegex = /^[\w\-.]+@(gmail+\.)+[\w-]{2,}$/gm;
  // from https://regex101.com/r/lHs2R3/1

  const onChange = (event) => {
    const { name, value } = event.target;
    setForm((oldForm) => ({ ...oldForm, [name]: value }));
  };

  const handleSelectChange = (value) => {
    setForm((oldForm) => ({ ...oldForm, role: value }));
    setSelectedRole(value); // Update the selected role to trigger the display of the additional card
    console.log("-- Selected Role: ", value);
  };

  const onSubmit = () => {
    if (!emailRegex.test(form.email)) {
      alert("Unesite ispravnu gmail adresu.");
      return;
    }
    if (form.role == "") {
      alert("Odaberite vrstu računa.");
      return;
    }

    const data = JSON.stringify(form);
    console.log(data);

    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: data,
    };

    const url = `/api/register/${form.role}`; // e.g., /api/register/igrač or /api/register/vlasnik

    return fetch(url, options).then((res) => {
      console.log(res);
      navigate("/Login");
    });
  };

  return (
    <>
      <Card className="w-[350px]">
        <CardHeader>
          <CardTitle>Registriraj se</CardTitle>
          <CardDescription>Napravi svoj korisnički račun</CardDescription>
        </CardHeader>

        <form onSubmit={(e) => e.preventDefault()}>
          <CardContent>
            <div className="grid w-full items-center gap-4">
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="name">Gmail</Label>
                <Input
                  id="name"
                  placeholder="Gmail"
                  name="email"
                  onChange={onChange}
                  value={form.email}
                />
              </div>

              <Select onValueChange={handleSelectChange}>
                <SelectTrigger id="framework">
                  <SelectValue placeholder="Izaberi vrstu računa" />
                </SelectTrigger>
                <SelectContent position="popper">
                  <SelectItem value="igrač">Igrač</SelectItem>
                  <SelectItem value="vlasnik">Vlasnik terena</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </CardContent>
          <CardFooter className="flex justify-between">
            <Link to="/Home">
              <Button variant="outline">Natrag</Button>
            </Link>

          </CardFooter>
        </form>
      </Card>

      {selectedRole && (
        <Card className="w-[350px] mt-4">
          <CardHeader>
            <CardTitle>
              {selectedRole === "igrač" ? "Player info" : "Owner info"}
            </CardTitle>
          </CardHeader>
          <CardContent>
            {selectedRole === "igrač" && (
            <div> <Label htmlFor="name">Name</Label>
              <Input id="name" placeholder="Unesite ime i prezime" />
              <br></br>
              <Label htmlFor="broj">Broj telefona</Label>
              <Input id="broj" placeholder="Unesite broj telefona" />
              <br></br>
              <CardFooter className="flex justify-end">
            
            <Link to="/login">
              <Button>Dalje</Button>
            </Link>
          </CardFooter>
              </div>
              
            )}
            {selectedRole === "vlasnik" && (
             <div> <Label htmlFor="naziv">Naziv</Label>
             <Input id="naziv" placeholder="Unesite naziv kluba" />
             <br></br>
             <Label htmlFor="broj">Broj telefona</Label>
             <Input id="broj" placeholder="Unesite broj telefona" />
             <br></br>
              <CardFooter className="flex justify-end">
            
            <Link to="/login">
              <Button>Dalje</Button>
            </Link>
          </CardFooter>
             </div>
            )}
          </CardContent>
        </Card>
      )}
    </>
  );
}
 */

import * as React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState } from "react";

export default function Signup() {
  const [selectedRole, setSelectedRole] = useState("");
  const [form, setForm] = useState({ email: "", role: "" });
  const navigate = useNavigate();

  const emailRegex = /^[\w\-.]+@(gmail+\.)+[\w-]{2,}$/gm;

  const onChange = (event) => {
    const { name, value } = event.target;
    setForm((oldForm) => ({ ...oldForm, [name]: value }));
  };

  const handleSelectChange = (value) => {
    setForm((oldForm) => ({ ...oldForm, role: value }));
    setSelectedRole(value);
  };

  const onSubmit = () => {
    if (!emailRegex.test(form.email)) {
      alert("Unesite ispravnu gmail adresu.");
      return;
    }
    if (!form.role) {
      alert("Odaberite vrstu računa.");
      return;
    }
    let url = "";
    const additionalData = {};
    if (form.role === "igrač") {
      const imeIgrac = document.getElementById("imeIgrac").value;
      const prezimeIgrac = document.getElementById("prezimeIgrac").value;
      const brojTel = document.getElementById("brojTel").value;
      url = "api/register/igrac";

      if (!imeIgrac || !brojTel || !prezimeIgrac) {
        alert("Unesite sve podatke za igrača.");
        return;
      }

      additionalData.imeIgrac = imeIgrac;
      additionalData.prezimeIgrac = prezimeIgrac;
      additionalData.brojTel = brojTel;

    } else if (form.role === "vlasnik") {
      const nazivVlasnik = document.getElementById("nazivVlasnik").value;
      const lokacija = document.getElementById("lokacija").value;
      const brojTel = document.getElementById("brojTel").value;
      url = "api/register/vlasnik";


      if (!nazivVlasnik || !brojTel || !lokacija) {
        alert("Unesite sve podatke za vlasnika.");
        return;
      }

      additionalData.nazivVlasnik = nazivVlasnik;
      additionalData.lokacija = lokacija;
      additionalData.brojTel = brojTel;
    }

    const payload = {
      ...form,
      ...additionalData,
    };

    console.log("Payload:", payload);

    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(payload),
    };

    fetch(url, options)
      .then((res) => {
        if (res.ok) {
          console.log("Registration successful:", res);
          navigate("/Login");
        } else {
          return res.json().then((error) => {
            alert(`Error: ${error.message || "Registration failed"}`);
          });
        }
      })
      .catch((err) => {
        console.error("Error during registration:", err);
        alert("Došlo je do greške. Pokušajte ponovo.");
      });
  };

  return (
    <>
      <Card className="w-[350px]">
        <CardHeader>
          <CardTitle>Registriraj se</CardTitle>
          <CardDescription>Napravi svoj korisnički račun</CardDescription>
        </CardHeader>

        <form onSubmit={(e) => e.preventDefault()}>
          <CardContent>
            <div className="grid w-full items-center gap-4">
              <div className="flex flex-col space-y-1.5">
                <Label htmlFor="email">Gmail</Label>
                <Input
                  id="email"
                  placeholder="Gmail"
                  name="email"
                  onChange={onChange}
                  value={form.email}
                />
              </div>

              <Select onValueChange={handleSelectChange}>
                <SelectTrigger id="role">
                  <SelectValue placeholder="Izaberi vrstu računa" />
                </SelectTrigger>
                <SelectContent position="popper">
                  <SelectItem value="igrač">Igrač</SelectItem>
                  <SelectItem value="vlasnik">Vlasnik terena</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </CardContent>
          <CardFooter className="flex justify-between">
            <Link to="/Home">
              <Button variant="outline">Natrag</Button>
            </Link>
            <Button onClick={onSubmit}>Dalje</Button>
          </CardFooter>
        </form>
      </Card>

      {selectedRole && (
        <Card className="w-[350px] mt-4">
          <CardHeader>
            <CardTitle>
              {selectedRole === "igrač" ? "Player Info" : "Owner Info"}
            </CardTitle>
          </CardHeader>
          <CardContent>
            {selectedRole === "igrač" && (
              <div>
                <Label htmlFor="imeIgrac">Ime</Label>
                <Input id="imeIgrac" placeholder="Unesite ime" />
                <br />
                <Label htmlFor="prezimeIgrac">Prezime</Label>
                <Input id="prezimeIgrac" placeholder="Unesite  prezime" />
                <br></br>
                <Label htmlFor="brojTel">Broj telefona</Label>
                <Input id="brojTel" placeholder="Unesite broj telefona" />
              </div>
            )}
            {selectedRole === "vlasnik" && (
              <div>
                <Label htmlFor="nazivVlasnik">Naziv kluba</Label>
                <Input id="nazivVlasnik" placeholder="Unesite naziv kluba" />
                <br />
                <Label htmlFor="brojTel">Broj telefona</Label>
                <Input id="brojTel" placeholder="Unesite broj telefona" />
                <br></br>

                <Label htmlFor="lokacija">Lokacija</Label>
                <Input id="lokacija" placeholder="Unesite lokaciju" />
              </div>
            )}
          </CardContent>
        </Card>
      )}
    </>
  );
}