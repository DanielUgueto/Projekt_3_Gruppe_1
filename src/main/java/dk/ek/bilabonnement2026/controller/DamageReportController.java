package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.model.DamageCategory;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.model.RentalContract;
import dk.ek.bilabonnement2026.service.DamageCategoryService;
import dk.ek.bilabonnement2026.service.DamageReportService;
import dk.ek.bilabonnement2026.service.RentalContractService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DamageReportController {

    @Autowired
    DamageReportService damageReportService;
    @Autowired
    RentalContractService rentalContractService;
    @Autowired
    DamageCategoryService damageCategoryService;

    //Rune
    @GetMapping("/damage-reports/create")
    public String showCreateDamageReportForm(@RequestParam int rentalContractId,
                                             HttpSession session,
                                             Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        //Hvis session ikke har en liste af DamageCategory så laves den og mappes.
        if (session.getAttribute("selectedDamages") == null) {
            session.setAttribute("selectedDamages", new ArrayList<DamageCategory>());
        }
        //Tjekker om der findes en rentalContract med det aktuelle id
        RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);
        if (rentalContract == null) {
            return "redirect:/dashboard";
        }
        //En liste af skader oprettes med skader fra session og loopes igennem med for each for at vise en total pris
        List<DamageCategory> selectedDamages = (List<DamageCategory>) session.getAttribute("selectedDamages");
        double totalPrice = 0;
        for (DamageCategory category : selectedDamages) {
            totalPrice += category.getStandardPrice();
        }

        model.addAttribute("rentalContract", rentalContract);
        model.addAttribute("employeeId", employee.getEmployeeId());
        model.addAttribute("createdAt", LocalDate.now());
        model.addAttribute("categories", damageCategoryService.getAllActiveDamageCategories());
        model.addAttribute("selectedDamages", selectedDamages);
        model.addAttribute("totalPrice", totalPrice);

        return "create-damage-report";
    }

    //Rune
    @PostMapping("/damage-reports/add-damage")
    public String addDamageToSession(@RequestParam int rentalContractId,
                                     @RequestParam("damageCategoryId") int damageCategoryId,
                                     HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        DamageCategory category = damageCategoryService.getDamageCategoryById(damageCategoryId);

        List<DamageCategory> selectedDamages = (List<DamageCategory>) session.getAttribute("selectedDamages");
        selectedDamages.add(category);

        return "redirect:/damage-reports/create?rentalContractId=" + rentalContractId;
    }

    //Rune
    @PostMapping("/damage-reports/create")
    public String createDamageReport(@RequestParam int rentalContractId,
                                     @RequestParam("employeeId") int employeeId,
                                     @RequestParam("description") String description,
                                     HttpSession session,
                                     Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }
        //List fyldes med indholdet fra session listen
        List<DamageCategory> selectedDamages = (List<DamageCategory>) session.getAttribute("selectedDamages");

        // try for at lave skaderapporten og session fjerne så indholdet af selectedDamages
        try {
            damageReportService.createDamageReport(rentalContractId, employeeId, description, selectedDamages);

            session.removeAttribute("selectedDamages");

            return "redirect:/dashboard/damage";

        } catch (IllegalArgumentException e) { // Hvis der sker en fejl under flowet bliver kontrakten fundet og sendt tilbage med alle medhørende modeller.
            RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);
            double totalPrice = 0;
            for (DamageCategory category : selectedDamages) {
                totalPrice += category.getStandardPrice();
            }

            model.addAttribute("rentalContract", rentalContract);
            model.addAttribute("employeeId", employeeId);
            model.addAttribute("createdAt", LocalDate.now());
            model.addAttribute("categories", damageCategoryService.getAllActiveDamageCategories());
            model.addAttribute("selectedDamages", selectedDamages);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("fejl", e.getMessage());

            return "damage-dashboard";
        }
    }

    //Rune
    @GetMapping("/dashboard/damage")
    public String showDamageDashboard(@RequestParam(required = false) Integer carId, @RequestParam(defaultValue = "afventer") String filter, HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        //sender liste af biler som er filtreret samt filter og employee til view
        List<CarOverview> carList = rentalContractService.getReturnedCarsWithContractByFilter(filter);
        model.addAttribute("carList", carList);
        model.addAttribute("filter", filter);
        model.addAttribute("employee",employee);
        //Hvis der er et id sendt med itereres der gennem carList og sætter selectedCar til matchende car
        if(carId != null){
            CarOverview selectedCar = null;
            for(CarOverview car : carList){
                if(car.getCarId() == carId){
                    selectedCar = car;
                    break;
                }
            }
            model.addAttribute("selectedCar", selectedCar);
        }
        return "damage-dashboard";
    }
}