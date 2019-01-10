package com.example.spockgroovy.controller

import com.example.spockgroovy.model.VisitorDetailsDto
import com.example.spockgroovy.model.VisitorForm
import com.example.spockgroovy.service.VisitorService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping
class VisitorsController {
    private VisitorService visitorService

    VisitorsController(VisitorService visitorService) {
        this.visitorService = visitorService
    }

    @GetMapping('/api/{id}')
    @ResponseBody
    VisitorDetailsDto index(@PathVariable Integer id) {
        VisitorDetailsDto.toDto(visitorService.fetchDataWithDetails(id))
    }

    @GetMapping('/visitor/{id}/details')
    String details(@PathVariable Integer id, Model model) {
        model.addAttribute('visitor', visitorService.fetchDataWithDetails(id))
        'details'
    }

    @GetMapping('/visitor/add')
    String addForm() {
        'add'
    }

    @PostMapping('/visitor/add')
    String add(@ModelAttribute VisitorForm visitorForm, RedirectAttributes redirectAttributes) {
        visitorService.addUser(visitorForm.firstName, visitorForm.lastName)
        redirectAttributes.addFlashAttribute('result', 'Wpis dodany')
        'redirect:/'
    }

    @GetMapping('/')
    String listing(Model model) {
        model.addAttribute('listing', visitorService.fetchListing())
        'listing'
    }

    @PostMapping('/visitor/{id}/delete')
    String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        visitorService.deleteVisitor(id)
        redirectAttributes.addFlashAttribute('result', 'Wpis usunięty')
        'redirect:/'
    }

}
