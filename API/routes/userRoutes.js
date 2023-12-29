'use strict'
const express = require('express');
const userController = require('../controllers/userController');
const router = express.Router();

const {getUtilizadores, getUtilizador, addUtlizador, updateUtilizador, deleteUtilizador} = userController;

router.get('/Utilizadores', getUtilizadores);
router.get('/Utilizador/:Id', getUtilizador);

router.post('/Utilizador', addUtlizador);

router.put('/Utilizador/:Id', updateUtilizador);

router.delete('/Utilizador/:Id', deleteUtilizador);

module.exports = {
    routes: router
}