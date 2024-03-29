'use strict'
const express = require('express');
const tarefaController = require('../controllers/tarefaController');
const checkTaskRoles = require("../middleware/rolesTaskLimite")
const router = express.Router();

const {getTarefaTaskId, getTarefas, getTarefa, addTarefa, updateTarefa, deleteTarefa} = tarefaController;

router.get('/Tarefa', getTarefas);
router.get('/Tarefa/:Id', getTarefa);
router.get('/Tarefas/:Id', getTarefaTaskId);

router.post('/Tarefa', checkTaskRoles.checkRoleTaskLimite, addTarefa);

router.put('/Tarefa/:Id', updateTarefa);

router.delete('/Tarefa/:Id', deleteTarefa);

module.exports = {
    routes: router
}