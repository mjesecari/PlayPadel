import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { useNavigate } from "react-router-dom";

export default function PaymentSuccess() {
    const [searchParams] = useSearchParams();
    const navigate = useNavigate();

    useEffect(() => {
        const paymentId = searchParams.get("paymentId");
        const payerId = searchParams.get("PayerID");
        const userId = searchParams.get("userId");
      
        console.log("Payment successful!");
        console.log(`Payment ID: ${paymentId}, Payer ID: ${payerId}, User ID: ${userId}`);
        // Here you can trigger additional logic, e.g., notify backend or update UI.
    }, [searchParams]);

    function home(){
        navigate("/Home");
    }
    return (
        <div>
            <h1>Plaćanje godišnje članarine uspješno!</h1>
            <p>Sada možete pristupiti svojoj stranici!</p>
            <button 
                onClick={() => home()}
				className="h-fit text-white ml-10">
                    Početna stranica
            </button>
        </div>
    );
}
