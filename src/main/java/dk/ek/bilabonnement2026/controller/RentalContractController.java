package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.repository.CarRepository;
import dk.ek.bilabonnement2026.service.RentalContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class RentalContractController {

    @Autowired
    RentalContractService rentalContractService;
    @Autowired
    CarRepository carRepository;
    @Autowired
    CustomerRepository CustomerRepository;

    @GetMapping("/rental-contracts/create")
    public String showCreateRentalContractForm(Model model){
        List<Car> cars = carRepository.findCarsByStatus("Ledig");
        List<Customer> customers = customerReository.findAll();
        model.addAttribute("cars", cars);
        model.addAttribute("customers", customers);
        return "createRentalContract";
    }

    @PostMapping("/rental-contracts/create")
    public String createRentalContract(@RequestParam("employeeId") int employeeId,
                                       @RequestParam("customerId") int customerId,
                                       @RequestParam("carId") int carId,
                                       @RequestParam("startDate") String startDate,
                                       @RequestParam("endDate") String endDate,
                                       @RequestParam("pickupLocation") String pickupLocation,
                                       @RequestParam("subscriptionType") String subscriptionType,
                                       Model model){
        RentalContract rentalContract = new RentalContract(employeeId,customerId,carId,
                LocalDate.parse(startDate),LocalDate.parse(endDate),
                pickupLocation,"Aktiv",subscriptionType);

        try{
            rentalContractService.createRentalContract(rentalContract);
            return "redirect:/rental-contacts";
        }catch (IllegalArgumentException e){
            model.addAttribute("fejl",e.getMessage());
            List<Car> cars = carRepository.findCarsByStatus("Ledig");
            List<Customer> customers = customerRepository.findAll();
            model.addAttribute("cars", cars);
            model.addAttribute("customers", customers);
            return "createRentalContract";

        }
    }
}
