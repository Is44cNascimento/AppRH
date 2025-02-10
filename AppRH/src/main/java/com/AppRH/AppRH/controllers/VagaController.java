package com.AppRH.AppRH.controllers;


import com.AppRH.AppRH.repository.CandidatoRepository;
import com.AppRH.AppRH.repository.VagaRepository;
import jakarta.validation.Valid;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Candidato;
import com.AppRH.AppRH.models.Vaga;



@Controller
public class VagaController {

    @Autowired
    private VagaRepository vr;

    @Autowired
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
        Iterable<Vaga> vagas = vr.findAll();
        mv.addObject("vagas",vagas);

        return mv;
    }

    // Buscar vaga por código

    @GetMapping("detalhes/{codigo}")
    public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo) {
        Vaga vaga = vr.findByCodigo(codigo);

        if (vaga == null) {
            ModelAndView errorView = new ModelAndView("error");
            errorView.addObject("message", "Vaga não encontrada!");
            return errorView;
        }

        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vaga", vaga);

        Iterable<Candidato> candidatos = cr.findByVaga(vaga);
        mv.addObject("candidatos", candidatos);

        return mv;
    }

    // Deletar vaga por código
    @GetMapping("/deletar/{codigo}")
    public String deletarVaga(@PathVariable("codigo") long codigo, RedirectAttributes attributes) {
        Vaga vaga = vr.findByCodigo(codigo);

        if (vaga != null) {
            vr.delete(vaga);
            attributes.addFlashAttribute("success", "Vaga deletada com sucesso!");
        } else {
            attributes.addFlashAttribute("error", "Vaga não encontrada!");
        }

        return "redirect:/vagas";
    }


    //Adicionar Candidato
    //@PostMapping
    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public  String detalhesVagaPost(@PathVariable("codigo") long codigo, @Valid Candidato candidato,
                                    BindingResult result, RedirectAttributes attributes){

        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "verifique os campos");
            return "redirect:/{codigo}";
        }
        //Rg duplicadp

        if(cr.findByRg(candidato.getRg()) != null){
            attributes.addFlashAttribute("mensagem erro", "Rg duplicado");
            return "redirect:/{codigo}";
        }


        Vaga vaga = vr.findByCodigo(codigo);
        candidato.setVaga(vaga);
        cr.save(candidato);
        attributes.addFlashAttribute("mensagem", "cadidato adicionado com sucesso !!!");
        return "redirect:/{codigo}";
    }


    //


    // Deleta Candidato
    @GetMapping("/deletarCandidato/{rg}")
    public String deletarCandidato(@PathVariable("rg") String rg) {
        Candidato candidato = cr.findByRg(rg);

        if (candidato != null) {
            Vaga vaga = candidato.getVaga();
            cr.delete(candidato);

            return "redirect:/vaga/detalhes/" + vaga.getCodigo();
        }

        return "redirect:/vagas"; // Redireciona para a lista de vagas caso o candidato não seja encontrado
    }





    //Metodos que atualizam a vaga
    //form edicao



    @GetMapping("/editar-vaga/{codigo}")
    public ModelAndView editarVaga(@PathVariable("codigo") long codigo) {
        Vaga vaga = vr.findByCodigo(codigo);

        if (vaga == null) {
            return new ModelAndView("error").addObject("message", "Vaga não encontrada");
        }

        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        mv.addObject("vaga", vaga);
        return mv;
    }


    //update da vaga

    @GetMapping("/update-vaga/{codigo}")
    public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", "Erro ao atualizar a vaga!");
            return "redirect:/editar-vaga/" + vaga.getCodigo();
        }

        vr.save(vaga);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

        return "redirect:/vaga/detalhes/" + vaga.getCodigo();  // Redireciona para os detalhes
    }

}


