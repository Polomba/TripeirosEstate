'use strict'

const houseData = require('../data/houseService');
const utils = require('../utils/utils');
const tarefaData = require("../data/tarefaService");


const listHouses = async (req, res) => {
    try {
        const houses = await houseData.listHouses();
        res.send(houses);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

const addHouse = async (req, res) => {
    try {
        const house = req.body;
        const createdHouse = await houseData.createHouse(house);
        res.send(createdHouse);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

const updateHouse = async (req, res) => {
    try {
        const houseId = req.params.Id;
        const data = req.body;
        const updated = await houseData.updateHouse(houseId, data);
        res.send(updated);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

const deleteHouse = async (req, res) => {
    try {

    } catch (error) {
        res.status(400).send(error.message);
    }
};

module.exports = {
    listHouses,
    addHouse,
    updateHouse,
    deleteHouse,
};