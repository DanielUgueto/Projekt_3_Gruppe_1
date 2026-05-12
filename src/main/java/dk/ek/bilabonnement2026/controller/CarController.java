package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarModel;
import dk.ek.bilabonnement2026.model.CarOverview;
import dk.ek.bilabonnement2026.model.Employee;
import dk.ek.bilabonnement2026.service.CarModelService;
import dk.ek.bilabonnement2026.service.CarService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarModelService carModelService;

    @GetMapping("/cars/create")
    public String showCreateCarForm(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee == null){
            return "redirect:/";
        }
        List<CarModel> carModels = carModelService.getAllCarModels();
        model.addAttribute("carModels",carModels);
        return "createCar";
    }

    @PostMapping("/cars/create")
    public String createCar(@RequestParam("carModelId") int carModelId,
                            @RequestParam("vinNumber") String vinNumber,
                            @RequestParam("licensePlate") String licensePlate,
                            @RequestParam("colour") String colour,
                            @RequestParam("status") String status,
                            @RequestParam("monthlyPrice") double monthlyPrice,
                            @RequestParam("registration_date") String registrationDate,
                            Model model){
        Car car = new Car(carModelId,vinNumber,licensePlate,monthlyPrice,status,colour,registrationDate);

        try{
            carService.createCar(car);
            return "redirect:/cars";
        }catch (IllegalArgumentException e){
            model.addAttribute("fejl", e.getMessage());
            List<CarModel> carModels = carModelService.getAllCarModels();
            model.addAttribute("carModels", carModels);
            return "createCar";
        }
    }

    @GetMapping("/dashboard/car")
    public String showCarDashboard(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) Integer carId,
                                   @RequestParam(required = false) String vinNumber,
                                   HttpSession session, Model model){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }

        List<CarOverview> list;

        if (status == null || status.isBlank()) {
            list = carService.findCarsWithDetails();
        } else {
            list = carService.findCarsWithDetailsByStatus(status);
        }

        CarOverview selectedCar = null;

        if (carId != null) {
            for (CarOverview c : list){
                if (c.getCarId() == carId) {
                    selectedCar = c;
                    break;
                }
            }
            model.addAttribute("selectedCar", selectedCar);
        }

        if (vinNumber != null){
            for (CarOverview c : list){
                if (c.getVinNumber().equals(vinNumber)) {
                    selectedCar = c;
                    break;
                }
            }

            if (selectedCar == null){
                model.addAttribute("notFound", "Kunne ikke finde bil med matchende stelnummer");
            }
            model.addAttribute("selectedCar", selectedCar);
        }

        model.addAttribute("employee", employee);
        model.addAttribute("carList", list);

        return "car-dashboard";
    }

    @PostMapping("/dashboard/car/remove")
    public String setCarStatusAsExpired(@RequestParam("carId") int carId, HttpSession session, RedirectAttributes redirectAttributes){
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null){
            return "redirect:/";
        }
        if (!employee.getRole().equalsIgnoreCase("dataregistrering")){
            return "redirect:/dashboard/car";
        }

        boolean statusUpdated = carService.changeCarStatusToExpired(carId);

        if (!statusUpdated) {
            redirectAttributes.addFlashAttribute("error", "Bilens status kunne ikke ændres til udgået!");
        } else {
            redirectAttributes.addFlashAttribute("success", "Bilens status blev ændret til udgået.");
        }

        return "redirect:/dashboard/car?carId=" + carId;
    }
}
