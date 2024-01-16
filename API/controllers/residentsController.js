'use strict'

const residentsData = require('../data/residentsService');
const utils = require('../utils/utils');

const getResidentsByHouseId = async (req, res) => {
    try {
        const houseId = req.params.HouseId;
        const residents = await residentsData.getResidentsByHouseId(houseId);
        res.json(residents);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

const getHouseByUserId = async (req, res) => {
    try {
        const userId = req.params.UserId;
        const residents = await residentsData.getHomeByUserId(userId);
        res.json(residents);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

const addResident = async (req, res) => {
    try {
        const { HouseId, UserId } = req.body;
        const result = await residentsData.addResident(HouseId, UserId);

        res.send(result);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

module.exports = {
    getResidentsByHouseId,
    addResident,
    getHouseByUserId
};
