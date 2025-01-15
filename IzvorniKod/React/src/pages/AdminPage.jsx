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
    else{
      const filtered = users.filter(user => user.tip === role);
      setFilteredUsers(filtered);
    }
  }

  const handleClanarinaChange = (id, value) => {
    setClanarine((prev) => ({
      ...prev,
      [id]: value,
    }));
  };
  
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
  };

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
  };

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
  };
  
  return (
    <div>
      <h1>Admin Page</h1> 
      <div>
        <h2>Popis korisnika:</h2>
          <select value = {role} onChange={changeRole}
          className="border border-gray-300 rounded-md p-2 text-zinc-50">
            <option value="svi">Svi</option>
            <option value="admin">Admin</option>
            <option value="vlasnik">Vlasnik</option>
            <option value="igrac">Igrač</option>
          </select>
          <ul>
            {filteredUsers.map((user) => (
              <li key={user.id}>
                Mail: {user.email} &nbsp;&nbsp; 
                Tip: {user.tip} &nbsp;&nbsp; 
                ID: {user.id} &nbsp;&nbsp; 
                Članarina: {user.cijenaClanarine}
                {user.owner && (
                  <div>
                    <input
                    type="number"
                    placeholder="Unesite članarinu"
                    value={clanarine[user.id] || ""}
                    onChange={(val) => handleClanarinaChange(user.id, val.target.value)}/>
                    <button className="m-5 text-zinc-50" onClick={() => updateClanarina(user.id)}>
                      Promjeni članarinu
                    </button>
                  </div>
                )}
                <button className="m-5 text-zinc-50" onClick={() => deleteKorizznik(user.id)}> Obriši korisnika </button>
              </li>
            ))}
          </ul>
          <div>
            <h2>Dodaj novog korisnika:</h2>  
              <input
                type="email"
                placeholder="email"
                value={korizznik.email}
                onChange={(val) => setKorizznik({ ...korizznik, email: val.target.value })}
              />
              <select
                value={korizznik.tip}
                onChange={(val) => setKorizznik({ ...korizznik, tip: val.target.value })}
                className="border border-gray-300 rounded-md p-2 text-zinc-50" 
              >
                <option value="">Odaberite tip</option>
                <option value="admin">Admin</option>
                <option value="vlasnik">Vlasnik</option>
                <option value="igrac">Igrač</option>
              </select>
              <button className="m-5 text-zinc-50" onClick={addKorizznik}>Dodaj korisnika</button>
          </div>
      </div>
    </div>
  );
}
