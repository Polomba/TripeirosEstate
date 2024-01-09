'use strict'
const express = require('express');
const residentsController = require('../controllers/residentsController');
const router = express.Router();

const {getResidentsByHouseId, getHouseByUserId, addResident,deleteResident} = residentsController;


router.get('/Resident/:HouseId', getResidentsByHouseId);
router.get('/Residents/:UserId', getHouseByUserId);


router.post('/Resident',addResident);


module.exports = {
    routes: router
}