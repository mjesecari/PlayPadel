import React, {useState, useEffect} from "react";
import NavBar from "@/components/Navbar";

export default function AdminPage() {
  const [users, setUsers] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [role, setRole] = useState("svi");
  const [clanarina, setClanarina] = useState("");
  const [trenutnaClanarina, setTrenutnaClanarina] = useState("");
  const [korizznik, setKorizznik] = useState({email: "",tip: "",});
  const [membershipData, setMembershipData] = useState({});
  const [editingUser, setEditingUser] = useState(null);

  const options = {
    method: "GET",
    headers: {"Content-type": "application/json"}
  }

  const fetchData = () => {

    fetch("api/admin/users", options)
      .then((response) => response.json())
      .then((data) => {setUsers(data); setFilteredUsers(data)})
      .catch((error) => console.error("Greška pri dohvaćanju korisnika:", error));

    fetch("api/membership/all", options)
      .then((response) => response.json())
      .then((data) => {
        const membershipMap = {};
        data.forEach((membership) => {
          membershipMap[membership.ownerId] = membership.active;
        });
        setMembershipData(membershipMap);
      })
      .catch((error) => console.error("Greška pri dohvaćanju članstva:", error));

      fetch("api/membership/price", options)
      .then((response) => response.json())
      .then((data) => {
        setTrenutnaClanarina(data.cijena); 
      })
      .catch((error) => console.error("Greška pri dohvaćanju članarine:", error));

  };

  useEffect(() => {fetchData()}, []);

  const changeRole = (event) => {
    const novaUloga = event.target.value;
    setRole(novaUloga);
    if (novaUloga === "svi") {
      setFilteredUsers(users);
    } else {
      const validRole = novaUloga === "igrac" ? "igrač" : novaUloga;
      fetch(`api/admin/users/${validRole}`, options)
        .then((response) => response.json())
        .then((data) => {
          setFilteredUsers(data);
        })
        .catch((error) => console.error(`Greška pri dohvaćanju korisnika tipa ${novaUloga}:`, error));
    }
  };

  const updateClanarina = () => {
    const adminId = JSON.parse(sessionStorage.getItem("userInfo"))?.id;
  
    if (!clanarina || clanarina <= 0) {
      alert("Unesite ispravnu cijenu članarine.");
      return;
    }
    console.log(`Slanje zahtjeva za promjenu članarine: ${clanarina}`);
    fetch(`api/admin/${adminId}/membership?clanarina=${clanarina}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          alert("Članarina ažurirana");
          //setTrenutnaClanarina(clanarina);
          setClanarina("");
          fetchData();
        } else {
          return response.text().then((text) => {
            throw new Error(text);
          });
        }
      })
      .catch((err) => {
        alert(`Greška: ${err.message}`);
      });
  }

  const addKorizznik = () => {
    if (!korizznik.email || !korizznik.tip) {
      alert("Unesi sve podatke.");
      return;
    }
    fetch("api/admin/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(korizznik),
    })
      .then((response) => {
        if (response.ok) {
          alert("Korisnik dodan");
          setKorizznik({ email: "", tip: "" });
          fetchData();
        } else {
          return response.text().then((text) => {
            throw new Error(text);
          });
        }
      })
      .catch((err) => {
        alert(`Greška: ${err.message}`);
      });
  }

  const deleteKorizznik = (id) => {
    const currentUser = JSON.parse(sessionStorage.getItem("userInfo"));
    if (currentUser && currentUser.id === id) {
      alert("Ne možete izbrisati sami sebe!");
      return;
    }

    fetch(`api/admin/users/${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          alert("Korisnik je obrisan");
          fetchData();
        } else {
          return response.text().then((text) => {
            throw new Error(text);
          });
        }
      })
      .catch((err) => {
        alert(`Greška: ${err.message}`);
      });
  }

  const renderUserDetails = (user) => {
    switch (user.tip) {
      case "igrac":
        return (
          <>
            <div> <b>Ime:</b> {user.imeIgrac}</div>
            <div> <b>Prezime:</b> {user.prezimeIgrac}</div>
            <div> <b>Broj telefona:</b> {user.brojTel}</div>
          </>
        );
      case "vlasnik":
        return (
          <>
            <div> <b>Naziv kluba:</b> {user.nazivVlasnik}</div>
            <div> <b>Lokacija:</b> {user.lokacija}</div>
            <div> <b>Broj telefona:</b> {user.brojTel}</div>
            <div> <b>Aktivno članstvo:</b> {membershipData[user.id] ? "Da" : "Ne"} </div>
          </>
        );
      case "admin":
        return <div><b>Admin prava:</b> {user.admin ? "Da" : "Ne"}</div>;
      default:
        return <div>Nema dodatnih informacija.</div>;
    }
  }

  const editUser = (user) => {
    setEditingUser({
      id: user.id,
      email: user.email || "",
      brojTel: user.brojTel || "",
      role: user.tip,
      ...(user.tip === "igrac"
        ? { imeIgrac: user.imeIgrac || "", prezimeIgrac: user.prezimeIgrac || "" }
        : {}),
      ...(user.tip === "vlasnik"
        ? { nazivVlasnik: user.nazivVlasnik || "", lokacija: user.lokacija || "" }
        : {}),
    });
  };  

  const saveUserChanges = () => {
    if (!editingUser.email || !editingUser.brojTel) {
      alert("Molimo popunite obavezna polja!");
      return;
    }
  
    const endpoint = editingUser.role === "igrac" ? "igrac" : "vlasnik";
    const userId = editingUser.id; 

    const dataToSend = editingUser.role === "igrac"
    ? {
        imeIgrac: editingUser.imeIgrac,
        prezimeIgrac: editingUser.prezimeIgrac,
        brojTel: editingUser.brojTel,
        role: editingUser.role,
        email: editingUser.email,
      }
    : {
        nazivVlasnik: editingUser.nazivVlasnik,
        lokacija: editingUser.lokacija,
        brojTel: editingUser.brojTel,
        role: editingUser.role,
        email: editingUser.email,
      };

    console.log("Šaljem podatke na:", `/api/admin/${endpoint}/${userId}`);
    console.log("Podaci:", JSON.stringify(dataToSend));
  
    fetch(`/api/admin/${endpoint}/${userId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(dataToSend),
    })
    .then(async (response) => {
      console.log("Response status:", response.status);
      const responseData = await response.json();
      console.log("Server response:", responseData);
  
        if (response.ok) {
          alert("Korisnik ažuriran!");
          setEditingUser(null);
          fetchData();
        } else {
          throw new Error("Greška pri ažuriranju korisnika");
        }
      })
      .catch((error) => alert(error.message));
  };

  const cancelEditing = () => {
    setEditingUser(null);
  };
  
  return (
    <div className="min-h-screen bg-gray-100">
      <NavBar />
      <div className="p-5 mt-20">
        <h1 className="text-4xl font-bold mb-10 text-center text-gray-800">
          Admin Page
        </h1>
        <div className="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-8">
          <h2 className="text-2xl font-semibold mb-6 text-gray-700">
            Popis korisnika:
          </h2>
          <select
            value={role}
            onChange={changeRole}
            className="border border-gray-300 rounded-md p-2 mb-6 w-full bg-gray-800 text-white"
          >
            <option value="svi">Svi</option>
            <option value="admin">Admin</option>
            <option value="vlasnik">Vlasnik</option>
            <option value="igrac">Igrač</option>
          </select>
  
          <ul className="space-y-6">
            {filteredUsers.map((user) => (
              <li
                key={user.id}
                className="border border-gray-300 rounded-md p-6 flex justify-between items-center bg-gray-50"
              >
                <div className="text-gray-700">
                  <div><b>Mail:</b> {user.email}</div>
                  <div><b>Tip:</b> {user.tip}</div>
                  <div><b>ID:</b> {user.id}</div>
                  {renderUserDetails(user)}
                </div>
                <div className="flex gap-4">
                <button
                  className="bg-blue-500 text-white px-5 py-2 rounded hover:bg-blue-600 transition"
                  onClick={() => editUser(user)}
                >
                  Uredi
                </button>
                  <button
                    className="bg-red-500 text-white px-5 py-2 rounded hover:bg-red-600 transition"
                    onClick={() => deleteKorizznik(user.id)}
                  >
                    Obriši
                  </button>
                </div>
              </li>
            ))}
          </ul>
  
          <div className="mt-10">
            <h2 className="text-2xl font-semibold mb-6 text-gray-700">Dodaj novog korisnika:</h2>
            <input
              type="email"
              placeholder="Email"
              value={korizznik.email}
              onChange={(val) => setKorizznik({ ...korizznik, email: val.target.value })}
              className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
            />
            <select
              value={korizznik.tip}
              onChange={(val) => setKorizznik({ ...korizznik, tip: val.target.value })}
              className="border border-gray-300 rounded-md p-2 mb-6 w-full bg-gray-800 text-white"
            >
              <option value="">Odaberite tip</option>
              <option value="vlasnik">Vlasnik</option>
              <option value="igrac">Igrač</option>
            </select>
            <button
              className="bg-green-500 text-white px-5 py-2 rounded hover:bg-green-600 transition w-full"
              onClick={addKorizznik}
            >
              Dodaj korisnika
            </button>
          </div>
  
          {editingUser && (
  <div className="mt-16 border-t pt-8">
    <h2 className="text-2xl font-semibold text-center mb-6 text-gray-700">Uredi korisnika:</h2>

    <label className="block mb-2 text-gray-700">Email:</label>
    <input
      type="email"
      value={editingUser.email}
      onChange={(e) => setEditingUser({ ...editingUser, email: e.target.value })}
      className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
    />

    <label className="block mb-2 text-gray-700">Broj telefona:</label>
    <input
      type="text"
      value={editingUser.brojTel}
      onChange={(e) => setEditingUser({ ...editingUser, brojTel: e.target.value })}
      className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
    />

    {editingUser.role === "igrac" && (
      <>
        <label className="block mb-2 text-gray-700">Ime igrača:</label>
        <input
          type="text"
          value={editingUser.imeIgrac}
          onChange={(e) => setEditingUser({ ...editingUser, imeIgrac: e.target.value })}
          className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
        />

        <label className="block mb-2 text-gray-700">Prezime igrača:</label>
        <input
          type="text"
          value={editingUser.prezimeIgrac}
          onChange={(e) => setEditingUser({ ...editingUser, prezimeIgrac: e.target.value })}
          className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
        />
      </>
    )}

    {editingUser.role === "vlasnik" && (
      <>
        <label className="block mb-2 text-gray-700">Naziv kluba:</label>
        <input
          type="text"
          value={editingUser.nazivVlasnik}
          onChange={(e) => setEditingUser({ ...editingUser, nazivVlasnik: e.target.value })}
          className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
        />

        <label className="block mb-2 text-gray-700">Lokacija:</label>
        <input
          type="text"
          value={editingUser.lokacija}
          onChange={(e) => setEditingUser({ ...editingUser, lokacija: e.target.value })}
          className="border border-gray-300 rounded-md p-2 mb-4 w-full text-black"
        />
      </>
    )}

    <button
      className="bg-green-500 text-white px-6 py-2 rounded hover:bg-green-600 transition"
      onClick={saveUserChanges}
    >
      Spremi promjene
    </button>

    <button
      className="bg-gray-500 text-white px-6 py-2 rounded ml-4 hover:bg-gray-600 transition"
      onClick={cancelEditing}
    >
      Odustani
    </button>
  </div>
)}

  
          <div className="mt-16 border-t pt-8">
            <h2 className="text-2xl font-semibold text-center mb-6 text-gray-700">Postavi globalnu članarinu:</h2>
            <div className="flex justify-center items-center gap-6">
              {/*<span className="text-lg font-medium text-gray-700">
                Trenutna članarina: <b>{trenutnaClanarina ? `${trenutnaClanarina} €` : "N/A"}</b>
              </span>*/}
              <input
                type="number"
                placeholder="Unesite članarinu"
                value={clanarina}
                onChange={(e) => setClanarina(e.target.value)}
                className="border border-gray-300 rounded-md p-2 w-48 text-center text-black"
              />
              <button
                className="bg-blue-500 text-white px-6 py-2 rounded hover:bg-blue-600 transition"
                onClick={updateClanarina}
              >
                Ažuriraj članarinu
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
