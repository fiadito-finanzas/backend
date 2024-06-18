package com.eventos.Fiadito.controllers;

import com.eventos.Fiadito.services.DeudaMensualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/deuda-mensual")
public class DeudaMensualController {
    @Autowired
    private DeudaMensualService deudaMensualService;


}
