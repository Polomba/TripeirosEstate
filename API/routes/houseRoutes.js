'use strict'
const express = require('express');
const houseController = require('../controllers/houseController');
const router = express.Router();

const {listHouses,addHouse,updateHouse,deleteHouse} = houseController;


router.get('/House', listHouses);

router.post('/House',addHouse);

router.put('/House/:HouseId',updateHouse)

router.delete('/House/:HouseId', deleteHouse);

module.exports = {
    routes: router
}