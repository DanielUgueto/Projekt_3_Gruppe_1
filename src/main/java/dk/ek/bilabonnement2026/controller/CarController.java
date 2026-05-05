package dk.ek.bilabonnement2026.controller;

import dk.ek.bilabonnement2026.model.Car;
import dk.ek.bilabonnement2026.model.CarModel;
import dk.ek.bilabonnement2026.repository.CarModelRepository;
import dk.ek.bilabonnement2026.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarModelRepository carModelRepository;

    @GetMapping("/cars/create")
    public String showCreateCarForm(Model model) {
        List<CarModel> carModels = carModelRepository.getAllCarModels();
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
                            Model model){
        Car car = new Car(carModelId,vinNumber,licensePlate,monthlyPrice,status,colour);

        try{
            carService.createCar(car);
            return "redirect:/cars";
        }catch (IllegalArgumentException e){
            model.addAttribute("fejl", e.getMessage());
            return "createCar";
        }
    }
}
