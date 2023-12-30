'use strict'

const houseData = require('../data/houseService');


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
        const houseId = req.params.HouseId;
        const data = req.body;
        const updated = await houseData.updateHouse(houseId, data);
        res.send(updated);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

const deleteHouse = async (req, res) => {
    try {
        const houseId = req.params.HouseId;
        const deleted = await houseData.deleteHouse(houseId);
        res.send(deleted);

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