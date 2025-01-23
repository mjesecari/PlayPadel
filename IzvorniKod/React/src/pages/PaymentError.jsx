import React from "react";

import { useNavigate } from "react-router-dom";

export default function PaymentError() {
    const navigate = useNavigate();
        function nazad(){
            navigate("/Membership");
        }
    return (
        <div style={{ textAlign: "center", marginTop: "50px" }}>
            <h1>Pogreška!</h1>
            <p>Došlo je do pogreške prilikom procesiranja plaćanja, molimo pokušajte ponovno</p>
            <button 
                onClick={() => nazad()}
				className="h-fit text-white ml-10">
                    Nazad
            </button>
        </div>
    );
}