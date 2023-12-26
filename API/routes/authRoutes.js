'use strict'
const express = require('express');
const authController = require('../controllers/authController');
const router = express.Router();

const { authUtilizador, registerUtilizador} = authController;

router.post('/authLogin/', authUtilizador);
router.post('/authRegister', registerUtilizador);

module.exports = {
    routes: router
}