package fer.progi.mjesecari.ppadel.api;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import fer.progi.mjesecari.ppadel.domain.Korisnik;
import fer.progi.mjesecari.ppadel.service.AdminService;
import fer.progi.mjesecari.ppadel.service.ClanstvoService;
import fer.progi.mjesecari.ppadel.service.KorisnikService;
import fer.progi.mjesecari.ppadel.service.PayPalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PayPalController {
    private final PayPalService payPalService;
    private final ClanstvoService clanstvoService;
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private AdminService adminService;
    @GetMapping("/payment")
    public String home(){return "index";}

    /**@PostMapping("/payment/create")
    public RedirectView createPayment(@RequestParam Long userId,
                                      @RequestParam String description){
        Double total;
        if (Objects.equals(description, "membership")){
            total = adminService.getClanarina();
        }
        else{
            total = 0.0;
        }
        String currency = "EUR";
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
    }**/
    @PostMapping("/payment/create")
    public ResponseEntity<Map<String, String>> createPayment(@RequestParam Long userId,
                                                             @RequestParam String description) {
        Double total = 0.0;
        if ("membership".equals(description)) {
            total = adminService.getClanarina();
        }

        String currency = "EUR";
        try {
            String cancelUrl = "http://localhost:8080/payment/cancel";
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

            for (Links links : payment.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    Map<String, String> response = new HashMap<>();
                    response.put("approvalUrl", links.getHref());
                    System.out.println("tu1");
                    return ResponseEntity.ok(response); // Send the approval URL to frontend
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error creating payment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/payment/success")
    public RedirectView paymentSuccess(@RequestParam("paymentId") String paymentId,
                                 @RequestParam("PayerID") String payerId,
                                    @RequestParam("userId") Long userId){
        log.info(String.valueOf(userId));
        try{
            System.out.println("tu2");
            Payment payment = payPalService.executePayment(paymentId,payerId);
            System.out.println("tu3");

            if(payment.getState().equals("approved")){
                log.info("Payment successful for user" + userId);
                Optional<Korisnik> currentUser = korisnikService.findById(userId);
                Korisnik korisnik = currentUser.orElseThrow(() -> new IllegalArgumentException("korisnik nije pronaden"));
                System.out.println(korisnik);
                if(korisnik.isOwner()) {
                    clanstvoService.UpdateClanstvo(userId,
                            Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal()),
                            "PayPal");
                }
                return new RedirectView("http://localhost:5173/payment/success");
            }

        }catch (PayPalRESTException e){
            log.error("Error payment success", e);
        }
        return new RedirectView("http://localhost:5173/payment/error");
    }

    @GetMapping("/payment/cancel")
    public RedirectView paymentCancel(){
        return new RedirectView("http://localhost:5173/payment/cancel");
    }

    @GetMapping("/payment/error")
    public RedirectView paymentError(){
        return new RedirectView("http://localhost:5173/payment/error");
    }

}
