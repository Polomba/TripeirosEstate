'use strict'
const express = require('express');
const tarefaController = require('../controllers/tarefaController');
const authCookie = require("../middleware/authCookieVerify");
const checkRoles = require("../middleware/rolesAuthorization");
const checkTaskRoles = require("../middleware/rolesTaskLimite")
const router = express.Router();

const {getTarefa, getTarefa, addTarefa, updateTarefa, deleteTarefa} = tarefaController;

router.get('/Tarefa', authCookie.authCookieVerify, getTarefa);
router.get('/Tarefa/:Id', authCookie.authCookieVerify, getTarefa);

router.post('/Tarefa', authCookie.authCookieVerify, checkTaskRoles.checkRoleTaskLimite, addTarefa);

router.put('/Tarefa/:Id', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, updateTarefa);

router.delete('/Tarefa/:Id', authCookie.authCookieVerify, checkRoles.checkRoleTarefa, deleteTarefa);

module.exports = {
    routes: router
}