import React from "react";

import { useNavigate } from "react-router-dom";

export default function PaymentCancel() {
    const navigate = useNavigate();
    function nazad(){
        navigate("/Membership");
    }
    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>Plaćanje otkazano</h1>
            <p>otkazali ste plaćanje, molimo Vas da pokušate ponovno ukoliko želite pristupiti svojoj stranici</p>
            <button 
                onClick={() => nazad()}
				className="h-fit text-white ml-10">
                    Nazad
            </button>
        </div>
    );
}
