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
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/damage-reports/create/{rentalContractId}")
    public String showCreateDamageReportForm(@PathVariable int rentalContractId,
                                             HttpSession session,
                                             Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        if (session.getAttribute("selectedDamages") == null) {
            session.setAttribute("selectedDamages", new ArrayList<DamageCategory>());
        }

        RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);
        if (rentalContract == null) {
            return "redirect:/dashboard";
        }

        List<DamageCategory> selectedDamages = (List<DamageCategory>) session.getAttribute("selectedDamages");
        double totalPrice = 0;
        for (DamageCategory category : selectedDamages) {
            totalPrice += category.getStandardPrice();
        }

        model.addAttribute("rentalContract", rentalContract);
        model.addAttribute("employeeId", employee.getEmployeeId());
        model.addAttribute("createdAt", LocalDate.now());
        model.addAttribute("categories", damageCategoryService.getAllDamageCategories());
        model.addAttribute("selectedDamages", selectedDamages);
        model.addAttribute("totalPrice", totalPrice);

        return "create-damage-report";
    }

    @PostMapping("/damage-reports/add-damage/{rentalContractId}")
    public String addDamageToSession(@PathVariable int rentalContractId,
                                     @RequestParam("damageCategoryId") int damageCategoryId,
                                     HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        DamageCategory category = damageCategoryService.getDamageCategoryById(damageCategoryId);

        List<DamageCategory> selectedDamages = (List<DamageCategory>) session.getAttribute("selectedDamages");
        selectedDamages.add(category);

        return "redirect:/damage-reports/create/" + rentalContractId;
    }

    @PostMapping("/damage-reports/create/{rentalContractId}")
    public String createDamageReport(@PathVariable int rentalContractId,
                                     @RequestParam("employeeId") int employeeId,
                                     @RequestParam("description") String description,
                                     HttpSession session,
                                     Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/";
        }

        List<DamageCategory> selectedDamages = (List<DamageCategory>) session.getAttribute("selectedDamages");

        try {
            damageReportService.createDamageReport(rentalContractId, employeeId, description, selectedDamages);

            session.removeAttribute("selectedDamages");

            return "redirect:/damage-dashboard";

        } catch (IllegalArgumentException e) {
            RentalContract rentalContract = rentalContractService.getRentalContractByContractId(rentalContractId);
            double totalPrice = 0;
            for (DamageCategory category : selectedDamages) {
                totalPrice += category.getStandardPrice();
            }

            model.addAttribute("rentalContract", rentalContract);
            model.addAttribute("employeeId", employeeId);
            model.addAttribute("createdAt", LocalDate.now());
            model.addAttribute("categories", damageCategoryService.getAllDamageCategories());
            model.addAttribute("selectedDamages", selectedDamages);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("fejl", e.getMessage());

            return "create-damage-report";
        }
    }

    @GetMapping("/damage-dashboard")
    public String showDamageDashboard(@RequestParam(required = false) Integer carId, @RequestParam(defaultValue = "afventer") String filter, HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        List<CarOverview> carList = rentalContractService.getReturnedCarsWithContractByFilter(filter);
        model.addAttribute("carList", carList);
        model.addAttribute("filter", filter);
        model.addAttribute("employee",employee);

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