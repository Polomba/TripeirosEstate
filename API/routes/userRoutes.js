'use strict'
const express = require('express');
const userController = require('../controllers/userController');
const router = express.Router();

const {getUtilizadores, getUtilizador,deleteUtilizador, getUtilizadorByEmail, updateUtilizador} = userController;

router.get('/Utilizadores', getUtilizadores);
router.get('/Utilizadores/:Id', getUtilizador);
router.get('/Utilizador/:Email', getUtilizadorByEmail);

router.put('/Utilizador/:Id', updateUtilizador)

router.delete('/Utilizador/:Id', deleteUtilizador);

module.exports = {
    routes: router
}
