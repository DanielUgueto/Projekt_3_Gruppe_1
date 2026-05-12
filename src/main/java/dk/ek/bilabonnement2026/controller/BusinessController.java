package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.model.RentalContractOverview;
import dk.ek.bilabonnement2026.service.RentalContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BusinessController {
    
    @Autowired
    RentalContractService rentalContractService;

    @GetMapping("/rental-contracts/overview")
    public String showRentalContractOverview(@RequestParam(required = false) String status,
                                             HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        List<RentalContractOverview>  rentalContractOverviewList = rentalContractService.getAllRentalContractOverviews(status);

        model.addAttribute("rentalContracts", rentalContractOverviewList);
        model.addAttribute("selectedStatus", status);

        return "rental-contract-overview";
    }
}
