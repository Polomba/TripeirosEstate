'use strict'
const express = require('express');
const authController = require('../controllers/authController');
const authCookie = require("../middleware/authCookieVerify");
const checkRoles = require("../middleware/rolesAuthorization");
const router = express.Router();

const { authUtilizador, registerUtilizador} = authController;

router.post('/authLogin', alteracoesCountVerifyOnLogin, authUtilizador);
router.post('/authRegister', registerUtilizador);

module.exports = {
    routes: router
}