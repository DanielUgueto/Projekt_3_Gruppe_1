package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.DamageCategoryService;
import dk.ek.bilabonnement2026.service.DamageReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DamageController {

    @Autowired
    DamageReportService damageReportService;
    @Autowired
    DamageCategoryService damageCategoryService;

    @GetMapping("/skader/opret/{damageReportId}")
    public String visSkadeFormular(@PathVariable int damageReportId,
                                   HttpSession session,
                                   Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "/redirect:/";
        }

        model.addAttribute("damageReport", damageReportService.getDamageReportById(damageReportId));
        model.addAttribute("categories", damageCategoryService.getAllDamageCategories());

        return "create-damage";
    }

    @PostMapping("/skader/opret/{damageReportId}")
    public String registrerSkade(@PathVariable int damageReportId,
                                 @RequestParam("damageCategoryId") int damageCategoryId,
                                 HttpSession session,
                                 Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        try{
            damageReportService.registrerDamage(damageReportId,damageCategoryId);
            return "redirect:/skader/opret/"+ damageReportId;

        } catch (IllegalArgumentException e){
            model.addAttribute("damageReportId", damageReportId);
            model.addAttribute("categories", damageCategoryService.getAllDamageCategories());
            model.addAttribute("fejl", e.getMessage());

            return "create_damage";
        }

    }
}
