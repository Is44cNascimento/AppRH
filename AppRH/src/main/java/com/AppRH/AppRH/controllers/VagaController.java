package com.AppRH.AppRH.controllers;


import com.AppRH.AppRH.repository.CandidatoRepository;
import com.AppRH.AppRH.repository.VagaRepository;
import jakarta.validation.Valid;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;



@Controller
public class VagaController {


    private com.AppRH.AppRH.repository.VagaRepository vr;
    private CandidatoRepository cr;



    //cadastrar vaga
    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
    public String form(){

        return "vaga/formVaga";

    }


    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.POST )
    public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes ){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "verifiquye os campos...");
            return "redirect:/cadastrarVaga";
        }
        vr.save(vaga);
        attributes.addFlashAttribute("mensagem","Vaga cadastrada com sucesso ");
        return "redirect:/cadastrarVaga";




    }

//LISTA VAGA
    @RequestMapping("/vagas")
    public ModelAndView listaVagas(){
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        Iterable<Vaga>vagas = vr.findAll();
        mv.addObject("vagas",vagas);

        return mv;
    }

    //Buscar vaga

    @RequestMapping(value = "/(codigo)", method = RequestMethod.GET)
    public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo){

        Vaga vaga = vr.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vaga",vaga);


        Iterable<Candidato> candidatos = cr.findByVaga(vaga);
        mv.addObject("candidatos",candidatos);
        return mv;

    }


}
