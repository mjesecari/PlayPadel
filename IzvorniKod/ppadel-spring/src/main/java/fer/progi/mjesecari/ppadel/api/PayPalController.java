package fer.progi.mjesecari.ppadel.api;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.ClanstvoService;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.PayPalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PayPalController {
    private final PayPalService payPalService;
    private final ClanstvoService clanstvoService;
    @Autowired
    private KorisnikService korisnikService;

    @GetMapping("/payment")
    public String home(){return "index";}
    @PostMapping("/payment/create")
    public RedirectView createPayment(@RequestParam Long userId,
                                      @RequestParam Double total,
                                      @RequestParam String currency,
                                      @RequestParam String description){
        try {
            String cancelUrl ="http://localhost:8080/payment/cancel";
            String successUrl = "http://localhost:8080/payment/success?userId=" + userId;
            String method = "PayPal";
            String intent = "sale";
            Payment payment = payPalService.createPayment(
                    total,
                    currency,
                    method,
                    intent,
                    description,
                    cancelUrl,
                    successUrl

            );
            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }
        }catch (PayPalRESTException e){
            log.error("Error creating payment", e);
        }
        return new RedirectView("/payment/error");
    }
    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("PayerID") String payerId,
                                    @RequestParam("userId") Long userId){
        log.info(String.valueOf(userId));
        try{
            Payment payment = payPalService.executePayment(paymentId,payerId);
            if(payment.getState().equals("approved")){
                log.info("Payment successful for user" + userId);
                /**Optional<Korisnik> currentUser = korisnikService.findById(userId);
                Korisnik korisnik = currentUser.orElseThrow(() -> new IllegalArgumentException("korisnik nije pronaden"));
                if(korisnik.isOwner()){}**/
                clanstvoService.UpdateClanstvo(userId,
                        Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal()),
                        "PayPal");

                return "PaymentSuccess";
            }

        }catch (PayPalRESTException e){
            log.error("Error payment success", e);
        }
        return "PaymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "PaymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError(){
        return "PaymentError";
    }

}
