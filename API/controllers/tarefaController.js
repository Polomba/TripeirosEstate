'use strict'

const produtoData = require('../data/produtoService');

const getTarefas = async (req, res) => {
    try {
        const tarefas = await produtoData.listTarefas();
        res.send(tarefas);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}
const getTarefa = async (req, res)=> {
    try {
        const tarefaId = req.params.Id;
        const oneTarefa = await tarefaData.listTarefaById(tarefaId);
        res.send(oneTarefa);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const addTarefa = async (req, res)=> {
    try {
        const data = req.body;
        const created = await tarefaData.createTarefa(data);
        res.send(created);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const updateTarefa = async (req, res)=> {
    try {
        const tarefaId = req.params.Id;
        const data = req.body;
        const updated = await tarefaData.updateTarefa(tarefaId, data);
        res.send(updated);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

const deleteTarefa = async (req, res)=> {
    try {
        const tarefaId = req.params.Id;
        const deleted = await tarefaData.deleteTarefa(tarefaId);
        res.send(deleted);
    }
    catch (error) {
        res.status(400).send(error.message);
    }
}

module.exports = {
    getTarefas,
    getTarefa,
    addTarefa,
    updateTarefa,
    deleteTarefa
}