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

const addResident = async (req, res) => {
    try {
        const { houseId, userId } = req.body;
        const result = await residentsData.addResident(houseId, userId);
        res.send(result);
    } catch (error) {
        res.status(400).send(error.message);
    }
};

module.exports = {
    getResidentsByHouseId,
    addResident
};
