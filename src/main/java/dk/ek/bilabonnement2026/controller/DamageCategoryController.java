package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.DamageCategory;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.DamageCategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DamageCategoryController {

    @Autowired
    DamageCategoryService damageCategoryService;

    //Rune
    @GetMapping("/damage-categories")
    public String showDamageCategoryDashboard(@RequestParam(required = false) Integer damageCategoryId,
                                              HttpSession session, Model model){
        //Tjek om session holder en emplyee
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        List<DamageCategory> list = damageCategoryService.getAllActiveDamageCategories();
        model.addAttribute("damageCategoryList", list);

        if(damageCategoryId != null){
            DamageCategory selectedDamageCategory = null;
            for(DamageCategory dc : list){
                if(dc.getDamageCategoryId() == damageCategoryId){
                    selectedDamageCategory = dc;
                    break;
                }
            }
            model.addAttribute("selectedDamageCategory", selectedDamageCategory);
        }
        return "damage-category-dashboard";
    }

    @GetMapping("/damage-categories/create")
    public String showCreateDamageCategory(HttpSession session){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        return "create-damage-category";
    }

    @PostMapping("/damage-categories/create")
    public String createDamageCategory(@RequestParam("name") String name,
                                       @RequestParam("standardPrice") double standardPrice,
                                       @RequestParam(required = false) String description,
                                       HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        DamageCategory category = new DamageCategory(name.trim(), standardPrice,description);

        String error = damageCategoryService.createDamageCategory(category);
        if(error != null){
            model.addAttribute("error", error);
            model.addAttribute("name", name);
            model.addAttribute("standardPrice", standardPrice);
            model.addAttribute("description", description);

            return "create-damage-category";
        }

        return "redirect:/damage-categories";
    }


    //Rune
    @GetMapping("/damage-categories/edit")
    public String showEditDamageCategory(@RequestParam int damageCategoryId,
                                         HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        DamageCategory category = damageCategoryService.getDamageCategoryById(damageCategoryId);
        if(category == null){
            return "redirect:/damage-categories";
        }

        model.addAttribute("selectedDamageCategory", category);
        return "edit-damage-category";
    }

    //Rune
    @PostMapping("/damage-categories/edit")
    public String updateDamageCategory(@RequestParam int damageCategoryId,
                                       @RequestParam("name") String name,
                                       @RequestParam("standardPrice") double standardPrice,
                                       @RequestParam(required = false) String description,
                                       HttpSession session, Model model, RedirectAttributes redirectAttributes){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        DamageCategory existing = damageCategoryService.getDamageCategoryById(damageCategoryId);
        if(existing == null){
            return "redirect:/damage-categories";
        }

        DamageCategory category = new DamageCategory(damageCategoryId, name.trim(),standardPrice,description,existing.getIsActive());

        String error = damageCategoryService.updateDamageCategory(category);
        if(error != null){
            model.addAttribute("error",error);
            model.addAttribute("selectedDamageCategory", category);
            return "edit-damage-category";
        }

        redirectAttributes.addFlashAttribute("success","Skadekategorien er opdateret");
        redirectAttributes.addFlashAttribute("selectedDamageCategory", category);
        return "redirect:/damage-categories";
    }

    //Rune
    @PostMapping("/damage-categories/delete")
    public String deleteDamageCategory(@RequestParam int damageCategoryId,
                                       HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }

        String error = damageCategoryService.setDamageCategoryInactive(damageCategoryId);
        if(error != null){
            List<DamageCategory> list = damageCategoryService.getAllActiveDamageCategories();
            DamageCategory selectedDamageCategory = null;
            for(DamageCategory dc : list){
                if(dc.getDamageCategoryId() == damageCategoryId){
                    selectedDamageCategory = dc;
                    break;
                }
            }
            model.addAttribute("damageCategoryList",list);
            model.addAttribute("selectedDamageCategory",selectedDamageCategory);
            model.addAttribute("error",error);
            return "damage-category-dashboard";
        }
        return "redirect:/damage-categories";
    }
}
