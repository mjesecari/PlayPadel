import React, {useState, useEffect} from "react";

export default function AdminPage() {
  const [users, setUsers] = useState([]);
  const [filteredUsers, setFilteredUsers] = useState([]);
  const [role, setRole] = useState('');
  const [clanarine, setClanarine] = useState({});
  const [korizznik, setKorizznik] = useState({
    email: "",
    tip: "",
  });

  const options = {
    method: "GET",
    headers: {"Content-type": "application/json"}
  }

  const fetchData = () => {
    fetch("api/admin/users", options)
    .then((response) => {return response.json()})
    .then((data) => {setUsers(data); setFilteredUsers(data)})
  }

  useEffect(() => {fetchData()}, []);

  const changeRole = (event) => {
    const novaUloga = event.target.value;
    setRole(novaUloga);
    filterUsers(novaUloga);
  }

  const filterUsers = (role) => {
    if(role === "svi") setFilteredUsers(users);
    else {
      const filtered = users.filter(user => user.tip === role);
      setFilteredUsers(filtered);
    }
  }

  const handleClanarinaChange = (id, value) => {
    setClanarine((prev) => ({
      ...prev,
      [id]: value,
    }));
  }

  const updateClanarina = (id) => {
    const novaClanarina = clanarine[id];
    fetch(`api/admin/${id}/membership?clanarina=${novaClanarina}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          alert("Članarina ažurirana");
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

  return (
    <div className="min-h-screen bg-gray-100 p-5">
      <h1 className="text-3xl font-bold mb-6 text-center text-gray-800">
        Admin Page
      </h1>
      <div className="max-w-4xl mx-auto bg-white shadow-md rounded-lg p-5">
        <h2 className="text-xl font-semibold mb-4">Popis korisnika:</h2>
        <select
          value={role}
          onChange={changeRole}
          className="border border-gray-300 rounded-md p-2 mb-4 w-full text-white bg-gray-800">
          <option value="svi">Svi</option>
          <option value="admin">Admin</option>
          <option value="vlasnik">Vlasnik</option>
          <option value="igrac">Igrač</option>
        </select>
        <ul className="space-y-4">
          {filteredUsers.map((user) => (
            <li key={user.id} className="border border-gray-300 rounded-md p-4 flex justify-between items-center">
              <div className="text-gray-700">
                <div> <b>Mail:</b> {user.email}</div>
                <div> <b>Tip:</b> {user.tip} </div>
                <div> <b>ID:</b> {user.id} </div>
                <div> <b>Članarina:</b> {user.cijenaClanarine} </div>
              </div>
              {user.owner && (
                <div className="ml-4">
                  <input
                    type="number"
                    placeholder="Unesite članarinu"
                    value={clanarine[user.id] || ""}
                    onChange={(val) => handleClanarinaChange(user.id, val.target.value)}
                    className="border border-gray-300 rounded-md p-2 mb-2 w-full text-white"/>
                  <button
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
                    onClick={() => updateClanarina(user.id)}> Promjeni članarinu 
                  </button>
                </div>
              )}
              <button
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition ml-4"
                onClick={() => deleteKorizznik(user.id)}
              > Obriši korisnika
              </button>
            </li>
          ))}
        </ul>
        <div className="mt-8">
          <h2 className="text-xl font-semibold mb-4">Dodaj novog korisnika:</h2>
          <input
            type="email"
            placeholder="Email"
            value={korizznik.email}
            onChange={(val) => setKorizznik({ ...korizznik, email: val.target.value })}
            className="border border-gray-300 rounded-md p-2 mb-2 w-full text-white"/>
          <select
            value={korizznik.tip}
            onChange={(val) => setKorizznik({ ...korizznik, tip: val.target.value })}
            className="border border-gray-300 rounded-md p-2 mb-4 w-full text-white bg-gray-800">
            <option value="">Odaberite tip</option>
            <option value="admin">Admin</option>
            <option value="vlasnik">Vlasnik</option>
            <option value="igrac">Igrač</option>
          </select>
          <button
            className="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600 transition w-full"
            onClick={addKorizznik}
          > Dodaj korisnika
          </button>
        </div>
      </div>
    </div>
  );
}
